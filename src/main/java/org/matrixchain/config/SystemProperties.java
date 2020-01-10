package org.matrixchain.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import org.matrixchain.crypto.ECKey;
import org.matrixchain.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;

public class SystemProperties {
    private static Logger logger = LoggerFactory.getLogger("general");

    public static SystemProperties CONFIG;

    private final ClassLoader classLoader;

    private Config config;

    private String externalIp;
    private String bindIp;

    public static SystemProperties getDefault() {
        if (CONFIG == null){
            CONFIG = new SystemProperties();
        }
        return CONFIG;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ValidateMe {
    }

    public SystemProperties() {
        this(ConfigFactory.empty());
    }

    public SystemProperties(File configFile) {
        this(ConfigFactory.parseFile(configFile));
    }

    public SystemProperties(String configResource) {
        this(ConfigFactory.load(configResource));
    }

    public SystemProperties(Config apiConfig) {
        this(apiConfig, SystemProperties.class.getClassLoader());
    }

    public SystemProperties(Config apiConfig, ClassLoader classLoader) {
        this.classLoader = ClassLoader.getSystemClassLoader();

        Config javaSystemProperties = ConfigFactory.load();
        Config defaultConfig = ConfigFactory.parseResources("matrixchain.conf");
        logger.info("Config (" + (defaultConfig.entrySet().size() > 0 ? " yes " : " no  ") + "): default properties from resource 'ethereumj.conf'");
        Config userConfig = ConfigFactory.parseResources("user.conf");
        logger.info("Config (" + (defaultConfig.entrySet().size() > 0 ? " yes " : " no  ") + "): default properties from resource 'ethereumj.conf'");

        config = apiConfig
                .withFallback(defaultConfig)
                .withFallback(userConfig);
        logger.debug("Config trace: " + config.root().render(ConfigRenderOptions.defaults().
                setComments(false).setJson(false)));

        config = javaSystemProperties.withFallback(config)
                .resolve();     // substitute variables in config if any
        validateConfig();
    }

    private void validateConfig() {
        for (Method method : getClass().getMethods()) {
            try {
                if (method.isAnnotationPresent(ValidateMe.class)) {
                    method.invoke(this);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error validating config method: " + method, e);
            }
        }
    }

    public String privateKey() {
        if (config.hasPath("peer.privateKey")) {
            String key = config.getString("peer.privateKey");
            if (key.length() != 64 || !Utils.isHexEncoded(key)) {
                throw new RuntimeException("The peer.privateKey needs to be Hex encoded and 32 byte length");
            }
            return key;
        }
        return null;
    }

    public ECKey getMyKey() {
        return ECKey.fromPrivate(Hex.decode(privateKey()));
    }

    /**
     *  Home NodeID calculated from 'peer.privateKey' property
     */
    public String nodeId() {
        return Hex.toHexString(getMyKey().getNodeId());
    }

    public String bindIp() {
        if (!config.hasPath("peer.discover.bind.ip") || config.getString("peer.discover.bind.ip").trim().isEmpty()) {
            if (bindIp == null) {
                try (Socket socket = new Socket("www.google.com", 80)) {
                    bindIp = socket.getLocalAddress().getHostAddress();
                    logger.info("");
                } catch (IOException e) {
                    bindIp = "0.0.0.0";
                }
            }
            return bindIp;
        } else {
            bindIp = config.getString("peer.discover.bind.ip").trim();
            return bindIp;
        }
    }

    public String externalIp() {
        if (!config.hasPath("peer.discover.external.ip") || config.getString("peer.discover.external.ip").trim().isEmpty()) {
            if (externalIp == null) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream()));
                    externalIp = in.readLine();
                    if (externalIp == null || externalIp.trim().isEmpty()) {
                        throw new IOException("");
                    }
                    try {
                        InetAddress.getByName(externalIp);
                    } catch (Exception e) {
                        throw new IOException("");
                    }
                } catch (IOException e) {
                    logger.info("");
                }
            }
            return externalIp;
        } else {
            externalIp = config.getString("peer.discover.external.ip").trim();
            return externalIp;
        }

    }

    public boolean peerDiscover() {
        return config.hasPath("peer.discover.enable") || config.getBoolean("peer.discover.enable");
    }

    public List<String> bootIpList() {
        return config.getStringList("peer.discover.ip.list");
    }

    public int getPeerListenPort() {
        return config.getInt("peer.listen.port");
    }

    public String getNetworkVersion() {
        return config.getString("network.version");
    }

}

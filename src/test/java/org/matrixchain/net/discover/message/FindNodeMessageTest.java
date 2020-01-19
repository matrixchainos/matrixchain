package org.matrixchain.net.discover.message;

import org.junit.Test;
import org.matrixchain.crypto.ECKey;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class FindNodeMessageTest {
    private final static ECKey ecKey = ECKey.fromPrivate(new BigInteger("a284c5935e33ec2c363913b6cf628da5c81defc2f96afb64690ae7a2f5535620", 16));
    private final static String url = "enode://6332792c4a00e3e4ee0926ed89e0d27ef985424d97b6a45bf0f23e51f0dcb5e66b875777506458aea7af6f9e4ffb69f43f3778ee73c81ed9d34c51c4b16b0b0f@52.232.243.152:30303";

    @Test
    public void create() {

    }
}
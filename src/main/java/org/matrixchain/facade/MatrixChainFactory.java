package org.matrixchain.facade;

import org.matrixchain.config.CommonConfig;
import org.matrixchain.config.SystemProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MatrixChainFactory {

    public static ApplicationContext context = null;

    public static MatrixChain create() {
        return create((Class) null);
    }

    public static MatrixChain create(Class userSpringConfig) {
        return create(SystemProperties.CONFIG, userSpringConfig);
    }

    public static MatrixChain create(SystemProperties systemProperties, Class userSpringConfig) {

        if (systemProperties == null)
            return null;
        return userSpringConfig == null ? create(new Class[]{CommonConfig.class}) :
                create(CommonConfig.class, userSpringConfig);
    }

    public static MatrixChain create(Class... springConfigs) {

        context = new AnnotationConfigApplicationContext(springConfigs);
        return context.getBean(MatrixChain.class);
    }
}

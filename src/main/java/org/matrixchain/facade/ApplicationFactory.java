package org.matrixchain.facade;

import org.matrixchain.config.CommonConfig;
import org.matrixchain.config.SystemProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationFactory {

    public static ApplicationContext context = null;

    public static Application create() {
        return create((Class) null);
    }

    public static Application create(Class userSpringConfig) {
        return create(SystemProperties.CONFIG, userSpringConfig);
    }

    public static Application create(SystemProperties systemProperties, Class userSpringConfig) {

        if (systemProperties == null)
            return null;
        return userSpringConfig == null ? create(new Class[]{CommonConfig.class}) :
                create(CommonConfig.class, userSpringConfig);
    }

    public static Application create(Class... springConfigs) {

        context = new AnnotationConfigApplicationContext(springConfigs);
        return context.getBean(Application.class);
    }
}

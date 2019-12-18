package org.matrixchain;

import org.matrixchain.facade.Application;
import org.matrixchain.facade.ApplicationFactory;

public class Start {

    public static void main(String[] args) {
        Application application = ApplicationFactory.create();
        application.init();
        application.startup();

        shutdown(application);
    }

    public static void shutdown(final Application application){
        Runtime.getRuntime().addShutdownHook(new Thread(application::shutdown));
    }
}

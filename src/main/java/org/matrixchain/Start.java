package org.matrixchain;

import org.matrixchain.config.SystemProperties;
import org.matrixchain.facade.MatrixChain;
import org.matrixchain.facade.MatrixChainFactory;

public class Start {

    public static void main(String[] args) {
        final SystemProperties config = SystemProperties.getDefault();

        MatrixChain matrixChain = MatrixChainFactory.create();
        matrixChain.init(config);
        matrixChain.startup();

        shutdown(matrixChain);
    }

    public static void shutdown(final MatrixChain matrixChain){
        Runtime.getRuntime().addShutdownHook(new Thread(matrixChain::shutdown));
    }
}

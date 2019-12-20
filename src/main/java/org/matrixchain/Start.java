package org.matrixchain;

import org.matrixchain.facade.MatrixChain;
import org.matrixchain.facade.MatrixChainFactory;

public class Start {

    public static void main(String[] args) {
        MatrixChain matrixChain = MatrixChainFactory.create();
        matrixChain.init();
        matrixChain.startup();

        shutdown(matrixChain);
    }

    public static void shutdown(final MatrixChain matrixChain){
        Runtime.getRuntime().addShutdownHook(new Thread(matrixChain::shutdown));
    }
}

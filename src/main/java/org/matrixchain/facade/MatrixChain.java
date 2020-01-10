package org.matrixchain.facade;

import org.matrixchain.config.SystemProperties;

public interface MatrixChain {

    void init(final SystemProperties config);

    void startup();

    void shutdown();

}

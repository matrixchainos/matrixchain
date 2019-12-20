package org.matrixchain.db;

import java.util.Set;

public interface DBSource <V>{

    void setName(String name);

    String getName();

    void init();

    void close();

    Set<byte[]> keys() throws RuntimeException;

    void reset();

    void put(V key, V val);

    V get(V key);

    void delete(V key);

    boolean flush();
}

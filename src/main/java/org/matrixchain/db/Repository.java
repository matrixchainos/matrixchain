package org.matrixchain.db;

public interface Repository {

    void put(Object key, Object val);

    Object get(Object key, Class type);

    void delete(Object key);

    boolean flush();
}

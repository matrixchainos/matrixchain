package org.matrixchain.db;

import java.lang.reflect.Type;

public interface Repository {

    void put(Object key, Object val);

    Object get(Object key, Type type);

    void delete(Object key);

    boolean flush();
}

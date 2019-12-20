package org.matrixchain.db;

import java.lang.reflect.Type;

public interface Repository {

    void put(String key, Object val);

    Object get(String key, Type type);

    void delete(String key);

    boolean flush();
}

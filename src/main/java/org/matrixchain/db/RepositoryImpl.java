package org.matrixchain.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class RepositoryImpl implements Repository{

    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public DBSourceImpl dbSource;

    public RepositoryImpl(String name) {
        dbSource = new DBSourceImpl(name);
        dbSource.init();
    }

    private byte[] serialize(Object o) {
        try {
            return mapper.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object deserialize(byte[] o, Class type) {
        try {
            return mapper.readValue(o, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(Object key, Object val) {
        dbSource.put(serialize(key), serialize(val));
    }

    public Object get(Object key, Class type) {
        byte[] value = dbSource.get(serialize(key));
        return deserialize(value, type);
    }

    public void delete(Object key) {
        dbSource.delete(serialize(key));
    }

    public boolean flush() {
        return false;
    }

}

package org.matrixchain.db;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

public class RepositoryImpl implements Repository{

    public DBSourceImpl dbSource;

    public RepositoryImpl(String name) {
        dbSource = new DBSourceImpl(name);
        dbSource.init();
    }

    private byte[] serialize(Object o) {
        return JSONObject.toJSONBytes(o);
    }

    private Object deserialize(byte[] o, Type type) {
        return JSONObject.parseObject(o, type);
    }

    public void put(String key, Object val) {
        dbSource.put(serialize(key), serialize(val));
    }

    public Object get(String key, Type type) {
        byte[] value = dbSource.get(serialize(key));
        return deserialize(value, type);
    }

    public void delete(String key) {
        dbSource.delete(serialize(key));
    }

    public boolean flush() {
        return false;
    }

}

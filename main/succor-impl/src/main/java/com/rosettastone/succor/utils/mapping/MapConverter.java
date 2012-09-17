package com.rosettastone.succor.utils.mapping;

import java.util.HashMap;
import java.util.Map;

public abstract class MapConverter<T extends Enum<?>> {

    private Map<String, T> map = new HashMap<String, T>();

    protected void add(String key, T value) {
        map.put(key, value);
    }

    public T convert(String str) {
        return map.get(str);
    }
}

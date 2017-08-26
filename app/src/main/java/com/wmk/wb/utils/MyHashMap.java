package com.wmk.wb.utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wmk on 2017/7/29.
 */

public class MyHashMap<K,V> extends HashMap<K,V> {
    private ArrayList<String> KeyArray=new ArrayList<>();
    private ArrayList<Object> ValueArray=new ArrayList<>();

    public MyHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public MyHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MyHashMap() {
        super();
    }

    public HashMap add(K key,V value) {
        super.put(key,value);
        return this;
    }

    @Override
    public V put(K key, V value) {
        KeyArray.add((String) key);
        ValueArray.add(value);
        return super.put(key, value);
    }

    public ArrayList<String> getKeyArray() {
        return KeyArray;
    }

    public ArrayList<Object> getValueArray() {
        return ValueArray;
    }
}

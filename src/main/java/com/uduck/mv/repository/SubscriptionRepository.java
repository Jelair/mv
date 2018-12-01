package com.uduck.mv.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubscriptionRepository {

    private final Map<String, Set> map = new HashMap<>();

    public void addSubscription(String key, String value){
        if (map.containsKey(key)){
            map.get(key).add(value);
        } else {
            Set set = new HashSet();
            set.add(value);
            map.put(key, set);
        }
    }

    public void subSubscription(String key, String value){
        if (map.containsKey(key)){
            map.get(key).remove(value);
        }
    }

    public void subSubscriptions(String key){
        if (map.containsKey(key))
            map.get(key).clear();
    }

    public Set getSubscription(String key){
        return map.get(key);
    }
}

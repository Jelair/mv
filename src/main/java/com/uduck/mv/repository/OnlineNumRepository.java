package com.uduck.mv.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class OnlineNumRepository {

    private final Map<String, Set> map = new HashMap<>();

    public int getOnlineNumByKey(String key){
        if (map.containsKey(key)){
            return map.get(key).size();
        } else {
            return 0;
        }
    }

    public void addOnlineNum(String key, String value){
        if (map.containsKey(key)){
            map.get(key).add(value);
        } else {
            Set set = new HashSet();
            set.add(value);
            map.put(key, set);
        }
    }

    public void subOnlineNum(String key, String value){
        if (map.containsKey(key)){
            map.get(key).remove(value);
        }
    }
}

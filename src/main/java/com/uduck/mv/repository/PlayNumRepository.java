package com.uduck.mv.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class PlayNumRepository {
    private Map<String, Integer> playCountMap = new HashMap<>();

    public void addNum(String videoId, String key){
        if (playCountMap.containsKey(videoId)){
            // 此处可以继续添加条件，比如同一个key，每天只能加一次
            Integer num = playCountMap.get(videoId);
            playCountMap.put(videoId, ++num);
        } else {
            playCountMap.put(videoId, 1);
        }
    }

    public Integer getNum(String videoId){
        if (playCountMap.containsKey(videoId)){
            return playCountMap.get(videoId);
        } else {
            playCountMap.put(videoId, 1);
        }
        return 1;
    }
}

package com.uduck.mv.repository;

import com.uduck.mv.entity.dto.VideoDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoSearchRepository extends ElasticsearchRepository<VideoDTO, Integer> {
}

package com.uduck.mv.service;

import com.uduck.mv.entity.dto.VideoDTO;

import java.util.List;

public interface IVideoSearchService {
    public Integer addVideo(VideoDTO videoDTO);
    public List<VideoDTO> searchVideo(Integer pageNumber, Integer pageSize, String searchContent);
    public Integer delVideo(Integer id);
}

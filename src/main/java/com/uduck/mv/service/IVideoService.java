package com.uduck.mv.service;

import com.uduck.mv.entity.Video;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;

import java.util.List;

public interface IVideoService {

    @Deprecated
    List<Video> getVideos();

    List<VideoDTO> getVideoDtos(DataPage dataPage);

    List<VideoDTO> getVideoDtosByUserId(Integer userId, DataPage dataPage);

    @Deprecated
    Video getVideoById(Integer id);

    VideoDTO getVideoDtoById(Integer id);

    Integer addVideo(Video video);

    Integer updateVideo(Video video);

    Integer deleteVideoById(Integer id);
}

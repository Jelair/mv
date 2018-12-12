package com.uduck.mv.service;

import com.uduck.mv.entity.Video;
import com.uduck.mv.entity.VideoCheck;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;
import com.uduck.mv.util.ResponseResult;

import javax.xml.ws.Response;
import java.util.List;

public interface IVideoService {

    @Deprecated
    List<Video> getVideos();

    List<VideoDTO> getVideoDtos(DataPage dataPage);

    ResponseResult getResponseResult(DataPage dataPage);

    //List<VideoDTO> getVideoDtosByAdmin(DataPage dataPage);

    List<VideoDTO> getVideoDtosByUserId(Integer userId, DataPage dataPage);

    @Deprecated
    Video getVideoById(Integer id);

    VideoDTO getVideoDtoById(Integer id);

    Integer addVideo(Video video);

    Integer updateVideo(Video video);

    Integer deleteVideoById(Integer id);

    Integer getPlayNumByVideoId(Integer id);

    boolean checkVideo(VideoCheck videoCheck);
}

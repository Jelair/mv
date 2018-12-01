package com.uduck.mv.controller;

import com.uduck.mv.entity.Video;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;
import com.uduck.mv.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private IVideoService videoService;

    @GetMapping("/user/{id}")
    public String user(@PathVariable("id") Integer id, Map<String,Object> map){
        DataPage dp = new DataPage();
        List<VideoDTO> dtos = videoService.getVideoDtosByUserId(id, dp);
        map.put("videos", dtos);
        return "user/center";
    }

    @GetMapping("/user/video/{id}")
    public String editVideoPage(@PathVariable("id") Integer id, Map<String,Object> map){
        VideoDTO videoDto = videoService.getVideoDtoById(id);
        map.put("video", videoDto);
        return "edit_video";
    }

    @PutMapping("/user/video")
    public String updateVideo(Video video){
        videoService.addVideo(video);
        return "success";
    }

    @DeleteMapping("/user/video/{id}")
    @ResponseBody
    public String deleteVideo(@PathVariable("id") Integer id){
        videoService.deleteVideoById(id);
        return "success";
    }
}

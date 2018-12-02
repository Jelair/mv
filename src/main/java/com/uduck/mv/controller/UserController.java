package com.uduck.mv.controller;

import com.uduck.mv.entity.Video;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;
import com.uduck.mv.security.MvUserDetails;
import com.uduck.mv.service.IVideoService;
import com.uduck.mv.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @ResponseBody
    public ResponseResult updateVideo(Video video){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof MvUserDetails){
            int operatorId = ((MvUserDetails) principal).getId();
            VideoDTO videoDto = videoService.getVideoDtoById(video.getId());
            if (operatorId == videoDto.getUser().getId()){
                videoService.addVideo(video);
                return ResponseResult.success();
            } else {
                return ResponseResult.fail(ResponseResult.Status.FORBIDDEN);
            }
        } else {
            return ResponseResult.fail(ResponseResult.Status.FORBIDDEN);
        }
    }

    @DeleteMapping("/user/video/{id}")
    @ResponseBody
    public ResponseResult deleteVideo(@PathVariable("id") Integer id){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof MvUserDetails){
            int operatorId = ((MvUserDetails) principal).getId();
            VideoDTO videoDto = videoService.getVideoDtoById(id);
            if (operatorId == videoDto.getUser().getId()){
                videoService.deleteVideoById(id);
                return ResponseResult.success();
            } else {
                return ResponseResult.fail(ResponseResult.Status.FORBIDDEN);
            }
        } else {
            return ResponseResult.fail(ResponseResult.Status.FORBIDDEN);
        }
    }
}

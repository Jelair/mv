package com.uduck.mv.controller;

import com.uduck.mv.constant.VideoStatus;
import com.uduck.mv.entity.User;
import com.uduck.mv.entity.VideoCheck;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;
import com.uduck.mv.security.MvUserDetails;
import com.uduck.mv.service.IVideoService;
import com.uduck.mv.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private IVideoService videoService;

    @GetMapping("/admin/review_video/{id}")
    public String reviewVideo(@PathVariable("id") Integer id,
                              Map<String, Object> map){
        VideoDTO dto = videoService.getVideoDtoById(id);
        map.put("video", dto);
        return "admin/review_video";
    }

    @GetMapping("/admin/review_videos")
    public String reviewVideos(Map<String, Object> map){
        DataPage dataPage = new DataPage();
        dataPage.setStatus(VideoStatus.REVIEW);
        List<VideoDTO> videoDtos = videoService.getVideoDtos(dataPage);
        map.put("videos", videoDtos);
        return "admin/review_videos";
    }

    @PostMapping("/admin/checkVideo")
    public String checkVideo(VideoCheck videoCheck){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof MvUserDetails){
            videoCheck.setUserId(((MvUserDetails) principal).getId());
        }
        videoCheck.setCheckTime(new Date());
        videoService.checkVideo(videoCheck);
        return "redirect:/admin/review_videos";
    }

}

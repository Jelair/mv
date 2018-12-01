package com.uduck.mv.controller;

import com.uduck.mv.entity.User;
import com.uduck.mv.entity.Video;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;
import com.uduck.mv.security.MvUserDetails;
import com.uduck.mv.service.IVideoService;
import com.uduck.mv.util.ConvertVideo;
import com.uduck.mv.util.ThumbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @GetMapping("/video")
    public String addVideoPage(){
        return "add_video";
    }

    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addVideo(@RequestParam("file") MultipartFile file,
                           Video video){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof MvUserDetails){
            User user = new User();
            user.setId(((MvUserDetails) principal).getId());
            video.setUser(user);
        } else {
            return "redirect:/error";
        }
        if (file.isEmpty()){
            return "redirect:/error";
        }
        String fileName = file.getOriginalFilename();
        // 获取文件的扩展名
        String ext = fileName.substring(fileName.lastIndexOf("."));
        // 使用UUID给图片重命名，并去掉四个“-”
        String newFileName = UUID.randomUUID().toString().replaceAll("-","");
        String netFileName = newFileName+ext;
        String url = "E:/upload/videos";
        video.setPath("/videos/"+netFileName);
        File target = new File(url + "/" + netFileName);

        File targetPath = new File(url);
        if (!targetPath.exists()){
            targetPath.mkdirs();
        }
        try {
            file.transferTo(target);
        } catch (IOException e){
            e.printStackTrace();
            return "redirect:/error";
        }

        /******************封面*******************/
        String thumbUrl = "E:/upload/covers";
        String thumbName = newFileName+".jpg";

        File targetPathThumb = new File(thumbUrl);
        if (!targetPathThumb.exists()){
            targetPathThumb.mkdirs();
        }
        try {
            ConvertVideo.extractingImage(5,url + "/" + netFileName,thumbUrl+"/"+thumbName);
            String newThumbName = "r"+thumbName;
            ThumbUtil.compressImageByOriginalScale(thumbUrl+"/"+thumbName,thumbUrl+"/"+newThumbName,348,196);
            video.setCover("/covers/"+newThumbName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*if (cover.isEmpty()){
            // 为空，将由系统截视频图来作为封面

        } else {
            // 用户自定义封面
            File targetThumb = new File(thumbUrl+"/"+thumbName);
            File targetPathThumb = new File(thumbUrl);
            if (!targetPathThumb.exists()){
                targetPathThumb.mkdirs();
            }
            try {
                cover.transferTo(targetThumb);
                video.setCover("covers/"+thumbName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        video.setUploadTime(new Date());
        videoService.addVideo(video);
        return "redirect:/videos";
    }

    @PutMapping("/video")
    public String updateVideo(Video video){
        return "success";
    }

    @DeleteMapping("/video/{id}")
    public String deleteVideo(@PathVariable("id") Integer id){
        videoService.deleteVideoById(id);
        return "success";
    }

    @GetMapping(value = "/video/{id}")
    public String playVideoPage(@PathVariable("id") Integer id, Map<String,Object> map){
        VideoDTO video = videoService.getVideoDtoById(id);
        DataPage dataPage = new DataPage();
        List<VideoDTO> videos = videoService.getVideoDtos(dataPage);
        map.put("videos",videos);
        map.put("video", video);
        return "play_video";
    }

    @GetMapping(value = {"/","/index","/videos"})
    public String videoList(Map<String,Object> map){
        DataPage dataPage = new DataPage();
        List<VideoDTO> videos = videoService.getVideoDtos(dataPage);
        map.put("videos",videos);
        return "index";
    }

    @GetMapping("/api/videoDtos")
    @ResponseBody
    public List<VideoDTO> getVideos(){
        DataPage dataPage = new DataPage();
        List<VideoDTO> videos = videoService.getVideoDtos(dataPage);
        return videos;
    }

    @GetMapping("/api/playNum/{id}")
    @ResponseBody
    public Object getPlayNum(@PathVariable("id") Integer id){
        Integer playNum = videoService.getPlayNumByVideoId(id);
        Map<String, Object> map = new HashMap<>();
        map.put("videoId", id);
        map.put("playNum", playNum);
        return map;
    }
}

package com.uduck.mv.controller;

import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;
import com.uduck.mv.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.crypto.Data;
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
}

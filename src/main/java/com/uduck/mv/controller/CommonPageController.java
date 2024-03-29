package com.uduck.mv.controller;

import com.uduck.mv.entity.User;
import com.uduck.mv.entity.dto.UserDTO;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.service.IUserService;
import com.uduck.mv.service.IVideoSearchService;
import com.uduck.mv.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CommonPageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IVideoSearchService videoSearchService;
    @Autowired
    private IVideoService videoService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(User user){
        if (userService.insertUser(user))
            return "redirect:register?success";
        return "redirect:register?error";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/error")
    public String error(){
        return "404";
    }

    @GetMapping("/test")
    @ResponseBody
    public Object test(HttpServletRequest request){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        String remoteUser = request.getRemoteUser();
        if (remoteUser != null){
            map.put("session", session.getAttribute("SPRING_SECURITY_CONTEXT"));
        }
        map.put("user", principal);
        return map;
    }

    @GetMapping("/test/es/{searchStr}")
    @ResponseBody
    public Object testElasticSearch(@PathVariable("searchStr") String searchStr){
        //VideoDTO video = videoService.getVideoDtoById(72);
        //videoSearchService.addVideo(video);
        System.out.println(searchStr);
        List<VideoDTO> videoDTOS = videoSearchService.searchVideo(null, null, searchStr);
        System.out.println(videoDTOS);
        return videoDTOS;
    }
}

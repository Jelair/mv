package com.uduck.mv.config;

import com.uduck.mv.properties.FileLocationProperties;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileLocationProperties fileLocationProperties;

    @Value("${spring.profiles.active}")
    private String runMode;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (runMode.equals("dev")){
            registry.addResourceHandler("/videos/**").addResourceLocations("file:///"+fileLocationProperties.getVideoLocation());
            registry.addResourceHandler("/covers/**").addResourceLocations("file:///"+fileLocationProperties.getCoverLocation());
            registry.addResourceHandler("/avatars/**").addResourceLocations("file:///"+fileLocationProperties.getAvatarLocation());
        } else {
            registry.addResourceHandler("/videos/**").addResourceLocations("file:"+fileLocationProperties.getVideoLocation());
            registry.addResourceHandler("/covers/**").addResourceLocations("file:"+fileLocationProperties.getCoverLocation());
            registry.addResourceHandler("/avatars/**").addResourceLocations("file:"+fileLocationProperties.getAvatarLocation());
        }
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

package com.uduck.mv.config;

import org.modelmapper.ModelMapper;
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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/videos/**").addResourceLocations("file:///E:/upload/videos/");
        registry.addResourceHandler("/covers/**").addResourceLocations("file:///E:/upload/covers/");
        registry.addResourceHandler("/avatars/**").addResourceLocations("file:///E:/upload/avatars/");
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

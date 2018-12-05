package com.uduck.mv.service;

import com.uduck.mv.constant.VideoStatus;
import com.uduck.mv.entity.Video;
import com.uduck.mv.entity.VideoCheck;
import com.uduck.mv.entity.dto.VideoDTO;
import com.uduck.mv.entity.form.DataPage;
import com.uduck.mv.repository.PlayNumRepository;
import com.uduck.mv.repository.VideoCheckRepository;
import com.uduck.mv.repository.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "video")
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoCheckRepository videoCheckRepository;

    @Autowired
    private PlayNumRepository playNumRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Video> getVideos() {
        List<Video> videos = videoRepository.findAll();
        return videos;
    }

    @Override
    public List<VideoDTO> getVideoDtos(DataPage dataPage){
        List<VideoDTO> videoDTOS = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.fromString(dataPage.getDirection()),dataPage.getOrderBy());
        int startPage = dataPage.getStart() / dataPage.getLength();
        Pageable pageable = PageRequest.of(startPage, dataPage.getLength(), sort);
        Page<Video> videos = videoRepository.findByStatus(dataPage.getStatus(), pageable);
        videos.getContent().forEach(video -> {
            VideoDTO videoDTO = modelMapper.map(video, VideoDTO.class);
            videoDTOS.add(videoDTO);
        });
        return videoDTOS;
    }

    @Override
    public List<VideoDTO> getVideoDtosByUserId(Integer userId, DataPage dataPage) {
        List<VideoDTO> videoDTOS = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.fromString(dataPage.getDirection()),dataPage.getOrderBy());
        int startPage = dataPage.getStart() / dataPage.getLength();
        Pageable pageable = PageRequest.of(startPage, dataPage.getLength(), sort);
        Page<Video> videos = videoRepository.findByUserId(userId, pageable);
        videos.getContent().forEach(video -> {
            VideoDTO videoDTO = modelMapper.map(video, VideoDTO.class);
            videoDTOS.add(videoDTO);
        });
        return videoDTOS;
    }

    @Override
    @Deprecated
    public Video getVideoById(Integer id) {
        Video video = videoRepository.getOne(id);
        return video;
    }

    @Override
    @Cacheable
    public VideoDTO getVideoDtoById(Integer id) {
        Video video = videoRepository.getOne(id);
        VideoDTO videoDTO = modelMapper.map(video, VideoDTO.class);
        return videoDTO;
    }



    @Override
    public Integer addVideo(Video video) {
        if (video.getId() != null){
            Video one = videoRepository.getOne(video.getId());
            one.setTitle(video.getTitle());
            one.setDescription(video.getDescription());
            videoRepository.save(one);
            return null;
        }
        Video save = videoRepository.save(video);
        return null;
    }

    @Override
    @CachePut(key = "#video.id")
    public Integer updateVideo(Video video) {
        Video save = videoRepository.save(video);
        return null;
    }

    @Override
    @CacheEvict
    public Integer deleteVideoById(Integer id) {
        videoRepository.deleteById(id);
        return null;
    }

    @Override
    public Integer getPlayNumByVideoId(Integer id) {
        Integer num = playNumRepository.getNum("/topic/online/" + id);
        return num;
    }

    @Override
    @Transactional
    public boolean checkVideo(VideoCheck videoCheck) {
        Video one = videoRepository.getOne(videoCheck.getVideoId());
        one.setStatus(videoCheck.getResult());
        videoRepository.save(one);
        videoCheckRepository.save(videoCheck);
        return true;
    }


}

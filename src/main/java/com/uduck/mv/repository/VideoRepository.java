package com.uduck.mv.repository;

import com.uduck.mv.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    Page<Video> findByUserId(Integer id, Pageable pageable);

    Page<Video> findByUserIdAndStatus(Integer id, Integer status, Pageable pageable);

    Page<Video> findByStatus(Integer status, Pageable pageable);
}

package com.youtube.stage.repository;

import com.youtube.stage.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
    List<Video> findByUploaderId(Long uploaderId);
    Page<Video> findByUploader_Id(Long uploaderId, Pageable pageable);

    Page<Video> findByUploader_IdAndTitleContainingIgnoreCase(Long uploaderId, String title, Pageable pageable);

    List<Video> findTop10ByCategoryAndIdNotOrderByViewsDesc(String category, Long excludeVideoId);

    Page<Video> findByCategoryIgnoreCase(String category, Pageable pageable);

    @Query("SELECT DISTINCT v.category FROM Video v")
    List<String> findDistinctCategories();
}

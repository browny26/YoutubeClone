package com.youtube.stage.service;

import com.youtube.stage.dto.VideoResponseDTO;
import com.youtube.stage.model.User;
import com.youtube.stage.model.Video;
import com.youtube.stage.repository.UserRepository;
import com.youtube.stage.repository.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public VideoService(VideoRepository videoRepository, UserRepository userRepository) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video uploadVideo(VideoResponseDTO dto, Long uploaderId) {
        Video video = new Video();
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setCategory(dto.getCategory());
        video.setVideoUrl(dto.getVideoUrl());
        video.setThumbnailUrl(dto.getThumbnailUrl());

        User uploader = userRepository.findById(uploaderId)
                .orElseThrow(() -> new RuntimeException("Uploader non trovato con ID: " + uploaderId));
        video.setUploader(uploader);

        return videoRepository.save(video);
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video non trovato con id: " + id));
    }

    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new RuntimeException("Video non trovato");
        }
        videoRepository.deleteById(id);
    }

    public List<Video> searchVideos(String keyword) {
        return videoRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    public List<Video> getVideosByUploader(Long uploaderId) {
        return videoRepository.findByUploaderId(uploaderId);
    }

    public void incrementViews(Long videoId) {
        Video video = getVideoById(videoId);
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
    }

    public Page<Video> getVideosByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return videoRepository.findByCategoryIgnoreCase(category, pageable);
    }

    public List<Video> getRecommendedVideos(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        String category = video.getCategory();
        return videoRepository.findTop10ByCategoryAndIdNotOrderByViewsDesc(category, videoId);
    }

    public Page<Video> getVideosByUser(Long uploaderId, int page, int size, String title) {
        Pageable pageable = PageRequest.of(page, size);

        if (title != null && !title.isEmpty()) {
            return videoRepository.findByUploader_IdAndTitleContainingIgnoreCase(uploaderId, title, pageable);
        }

        return videoRepository.findByUploader_Id(uploaderId, pageable);
    }

    public List<String> getAllCategories() {
        return videoRepository.findDistinctCategories();
    }
}

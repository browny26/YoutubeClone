package com.youtube.stage.controller;

import com.youtube.stage.config.CustomUserDetails;
import com.youtube.stage.dto.VideoResponseDTO;
import com.youtube.stage.model.Video;
import com.youtube.stage.service.CloudinaryService;
import com.youtube.stage.service.UserService;
import com.youtube.stage.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/video")
public class VideoController {

    private final VideoService videoService;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public VideoController(VideoService videoService, UserService userService, CloudinaryService cloudinaryService) {
        this.videoService = videoService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    @PostMapping("/upload")
    public ResponseEntity<Video> uploadVideo(
            @RequestParam("video") MultipartFile video,
            @RequestParam("thumbnail") MultipartFile thumbnail,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            Authentication authentication
    ) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long uploaderId = userDetails.getUser().getId();

        System.out.println("Video: " + video.getOriginalFilename());
        System.out.println("Thumbnail: " + thumbnail.getOriginalFilename());


        String videoUrl = cloudinaryService.uploadVideo(video.getBytes(), video.getOriginalFilename());

        String thumbnailUrl = cloudinaryService.uploadImage(thumbnail.getBytes(), thumbnail.getOriginalFilename());

        if (thumbnailUrl == null) {
            throw new RuntimeException("Upload thumbnail fallito");
        }


        VideoResponseDTO dto = new VideoResponseDTO();
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setCategory(category);
        dto.setVideoUrl(videoUrl);
        dto.setThumbnailUrl(thumbnailUrl);

        Video savedVideo = videoService.uploadVideo(dto, uploaderId);

        return ResponseEntity.ok(savedVideo);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Video>> searchVideos(@RequestParam String query) {
        List<Video> results = videoService.searchVideos(query);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/uploader/{uploaderId}")
    public ResponseEntity<List<Video>> getByUploader(@PathVariable Long uploaderId) {
        return ResponseEntity.ok(videoService.getVideosByUploader(uploaderId));
    }

    @GetMapping("/{videoId}/recommendations")
    public ResponseEntity<List<Video>> getRecommendations(@PathVariable Long videoId) {
        List<Video> recommendations = videoService.getRecommendedVideos(videoId);
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<Video>> getVideosByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Video> videos = videoService.getVideosByCategory(category, page, size);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = videoService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<Video>> getUserVideos(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title) {

        Long userId = getUserIdFromUserDetails(userDetails);
        Page<Video> videos = videoService.getVideosByUser(userId, page, size, title);
        return ResponseEntity.ok(videos);
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        // Assumendo che lo username sia l'email e che tu possa risalire all'ID utente così
        return userService.findByEmail(userDetails.getUsername()).getId();
    }
}

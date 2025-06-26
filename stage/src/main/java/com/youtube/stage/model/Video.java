package com.youtube.stage.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    @Column(name = "upload_date")
    private LocalDate uploadDate;

    private int views = 0;
    private int likes = 0;
    private String category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploader;

    @ManyToMany
    private Set<User> likedByUsers = new HashSet<>();

    public Video(Long id, String title, String description, String videoUrl, String thumbnailUrl, LocalDate uploadDate, int views, int likes, String category, User uploader, Set<User> likedByUsers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.uploadDate = uploadDate;
        this.views = views;
        this.likes = likes;
        this.category = category;
        this.uploader = uploader;
        this.likedByUsers = likedByUsers;
    }

    public Video() {

    }

    @PrePersist
    public void onCreate() {
        this.uploadDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(id, video.id) && Objects.equals(title, video.title) && Objects.equals(description, video.description) && Objects.equals(videoUrl, video.videoUrl) && Objects.equals(thumbnailUrl, video.thumbnailUrl) && Objects.equals(uploadDate, video.uploadDate) && Objects.equals(views, video.views) && Objects.equals(likes, video.likes) && Objects.equals(category, video.category) && Objects.equals(uploader, video.uploader) && Objects.equals(likedByUsers, video.likedByUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, videoUrl, thumbnailUrl, uploadDate, views, likes, category, uploader, likedByUsers);
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", uploadDate=" + uploadDate +
                ", views=" + views +
                ", likes=" + likes +
                ", category='" + category + '\'' +
                ", uploader=" + uploader +
                ", likedByUsers=" + likedByUsers +
                '}';
    }
}

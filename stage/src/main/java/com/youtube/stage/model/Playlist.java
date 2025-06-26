package com.youtube.stage.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private boolean isPublic = true;
    private LocalDate createdAt;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "playlist_videos",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id")
    )
    private List<Video> videos = new ArrayList<>();

    public Playlist() {

    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    public Playlist(Long id, String name, String description, boolean isPublic, LocalDate createdAt, String thumbnailUrl, User owner, List<Video> videos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.thumbnailUrl = thumbnailUrl;
        this.owner = owner;
        this.videos = videos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return isPublic == playlist.isPublic && Objects.equals(id, playlist.id) && Objects.equals(name, playlist.name) && Objects.equals(description, playlist.description) && Objects.equals(createdAt, playlist.createdAt) && Objects.equals(thumbnailUrl, playlist.thumbnailUrl) && Objects.equals(owner, playlist.owner) && Objects.equals(videos, playlist.videos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, isPublic, createdAt, thumbnailUrl, owner, videos);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", createdAt=" + createdAt +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", owner=" + owner +
                ", videos=" + videos +
                '}';
    }
}

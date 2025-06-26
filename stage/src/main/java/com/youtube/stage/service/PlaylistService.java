package com.youtube.stage.service;

import com.youtube.stage.model.Playlist;
import com.youtube.stage.model.User;
import com.youtube.stage.model.Video;
import com.youtube.stage.repository.PlaylistRepository;
import com.youtube.stage.repository.UserRepository;
import com.youtube.stage.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;


import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           VideoRepository videoRepository,
                           UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    public Playlist createPlaylist(Long userId, String title, String description) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Playlist playlist = new Playlist();
        playlist.setOwner(owner);
        playlist.setName(title);
        playlist.setDescription(description);

        return playlistRepository.save(playlist);
    }

    public Playlist addVideoToPlaylist(Long playlistId, Long videoId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist non trovata"));

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video non trovato"));

        playlist.getVideos().add(video);
        return playlistRepository.save(playlist);
    }

    public Playlist removeVideoFromPlaylist(Long playlistId, Long videoId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist non trovata"));

        playlist.getVideos().removeIf(v -> v.getId().equals(videoId));
        return playlistRepository.save(playlist);
    }

    public Page<Playlist> getPlaylistsByUser(Long userId, int page, int size, String titleFilter) {
        Pageable pageable = PageRequest.of(page, size);
        if (titleFilter == null || titleFilter.isEmpty()) {
            return playlistRepository.findByOwnerId(userId, pageable);
        } else {
            return playlistRepository.findByOwnerIdAndNameContainingIgnoreCase(userId, titleFilter, pageable);
        }
    }

    public List<Video> getVideosFromPlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist non trovata"));

        return playlist.getVideos();
    }

    public void deletePlaylist(Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public Playlist updatePlaylist(Long playlistId, String newTitle, String newDescription) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist non trovata"));

        playlist.setName(newTitle);
        playlist.setDescription(newDescription);

        return playlistRepository.save(playlist);
    }

    public Playlist getPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + playlistId));
    }

}


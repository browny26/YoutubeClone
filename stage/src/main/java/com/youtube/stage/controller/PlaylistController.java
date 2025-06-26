package com.youtube.stage.controller;

import com.youtube.stage.model.Playlist;
import com.youtube.stage.model.User;
import com.youtube.stage.model.Video;
import com.youtube.stage.service.PlaylistService;
import com.youtube.stage.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    public PlaylistController(PlaylistService playlistService, UserService userService) {
        this.playlistService = playlistService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Playlist> createPlaylist(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody Map<String, String> body) {
        String title = body.get("title");
        String description = body.get("description");
        Playlist playlist = playlistService.createPlaylist(/*userId*/ getUserIdFromUserDetails(userDetails), title, description);
        return ResponseEntity.ok(playlist);
    }

    @PostMapping("/{playlistId}/videos/{videoId}")
    public ResponseEntity<Playlist> addVideo(@PathVariable Long playlistId, @PathVariable Long videoId) {
        Playlist playlist = playlistService.addVideoToPlaylist(playlistId, videoId);
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("/{playlistId}/videos/{videoId}")
    public ResponseEntity<Playlist> removeVideo(@PathVariable Long playlistId, @PathVariable Long videoId) {
        Playlist playlist = playlistService.removeVideoFromPlaylist(playlistId, videoId);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<Playlist>> getUserPlaylists(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title) {

        Long userId = getUserIdFromUserDetails(userDetails);
        Page<Playlist> playlists = playlistService.getPlaylistsByUser(userId, page, size, title);
        return ResponseEntity.ok(playlists);
    }


    @GetMapping("/{playlistId}/videos")
    public ResponseEntity<List<Video>> getVideos(@PathVariable Long playlistId) {
        List<Video> videos = playlistService.getVideosFromPlaylist(playlistId);
        return ResponseEntity.ok(videos);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long playlistId,
                                                   @RequestBody Map<String, String> body) {
        String newTitle = body.get("title");
        String newDescription = body.get("description");
        Playlist updated = playlistService.updatePlaylist(playlistId, newTitle, newDescription);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long playlistId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return ResponseEntity.ok(playlist);
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        // Implementa qui la logica per ottenere l'id utente dal UserDetails (ad es. tramite UserService)
        // Es:
        User user = userService.getUserByEmail(userDetails.getUsername());
        return user.getId();
    }
}


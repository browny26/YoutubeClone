package com.youtube.stage.repository;

import com.youtube.stage.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByOwnerId(Long ownerId);

    Page<Playlist> findByOwnerId(Long ownerId, Pageable pageable);

    List<Playlist> findByName(String name);

    Page<Playlist> findByOwnerIdAndNameContainingIgnoreCase(Long ownerId, String name, Pageable pageable);

}

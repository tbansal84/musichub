package com.lexnx.exercises.music.db.repo;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlbumRepositoryImpl extends JpaRepository<AlbumEntity, UUID> {
    Page<AlbumEntity> findByArtistAndGenreContaining(ArtistEntity artist, String genre, Pageable pageable);

    Optional<AlbumEntity> findByArtistAndAlbumId(ArtistEntity artist, UUID albumId);
}

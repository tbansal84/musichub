package com.lexnx.exercises.music.db.repo;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.db.model.Genre;
import com.lexnx.exercises.music.rest.model.Album;
import com.lexnx.exercises.music.rest.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepositoryImpl extends JpaRepository<AlbumEntity, UUID> {
    Page<AlbumEntity> findByArtistAndGenreContaining(ArtistEntity artist, String genre, Pageable pageable);

    Optional<AlbumEntity> findByArtistAndAlbumId(ArtistEntity artist, UUID albumId);
}

package com.lexnx.exercises.music.db.repo;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.ArtistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ArtistRepositoryImpl extends JpaRepository<ArtistEntity, UUID> {


    Page<ArtistEntity> findAllByArtistNameContaining(String artistName, Pageable pageable);
}

package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.db.repo.ArtistRepositoryImpl;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ArtistService {
    private final ArtistRepositoryImpl artistRepository;
    private static final String EMPTY = "";

    public Page<ArtistEntity> getArtist(String artistName, Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("{pageable} cannot be null");
        }
        return artistRepository.findAllByArtistNameContaining(artistName == null ? EMPTY : artistName, pageable);
    }

    @Transactional
    public ArtistEntity createArtist(ArtistEntity artistEntity) {
        if (artistEntity == null) {
            throw new IllegalArgumentException("{artistEntity} cannot be null");
        }
        return artistRepository.save(artistEntity);

    }

    @Transactional
    public ArtistEntity editArtist(UUID artistId, ArtistEntity artistEntity) {
        if (artistId == null) {
            throw new IllegalArgumentException("{artistId} cannot be null");
        }
        if (artistEntity == null) {
            throw new IllegalArgumentException("{artistEntity} cannot be null");
        }
        final ArtistEntity artist = artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new);
        artist.setArtistName(artistEntity.getArtistName());
        return artistRepository.save(artist);
    }
}

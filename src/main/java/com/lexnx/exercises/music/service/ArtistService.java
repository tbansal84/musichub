package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.db.repo.ArtistRepository;
import com.lexnx.exercises.music.service.exceptions.ArtistServiceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public Flux<ArtistEntity> getArtist(String artistName, Sort sort) {
        return artistRepository.findAllByArtistNameContaining(artistName == null ? "" : artistName, sort).doOnError(e -> new ArtistServiceException("Error while getting  the artist"));
    }

    public Mono<ArtistEntity> createArtist(ArtistEntity artistEntity) {
        artistEntity.setArtistId(UUID.randomUUID());
        return artistRepository.save(artistEntity).doOnError(e -> new ArtistServiceException("Error while creating the artist"));

    }

    public Mono<ArtistEntity> editArtist(UUID artistId, ArtistEntity artistEntity) {

        return artistRepository.findById(artistId).flatMap(artist -> {
            artist.setArtistName(artistEntity.getArtistName());
            return artistRepository.save(artist).doOnError(e -> new ArtistServiceException("Error while editing the artist"));
        }).doOnError(e -> new ArtistServiceException("Error while editing the artist"));

    }
}

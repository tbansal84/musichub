package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.repo.AlbumRepository;
import com.lexnx.exercises.music.db.repo.ArtistRepository;
import com.lexnx.exercises.music.service.exceptions.AlbumServiceException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AlbumService {
    private static final String ERROR_WHILE_EDITING_THE_ALBUMS = "Error while editing the albums";
    private static final String ERROR_WHILE_SAVING_THE_ALBUMS = "Error while saving the albums";
    private static final String ERROR_WHILE_GETTING_THE_ALBUMS = "Error while getting the albums";
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public Flux<AlbumEntity> getAlbum(UUID artistId, String genre, Sort sort) {
        return albumRepository.findByArtistAndGenreContaining(artistRepository.findById(artistId).doOnError(e ->
                new AlbumServiceException(ERROR_WHILE_GETTING_THE_ALBUMS)), genre == null ? "" : genre, sort)
                .doOnError(e -> new AlbumServiceException(ERROR_WHILE_GETTING_THE_ALBUMS));
    }

    public Mono<AlbumEntity> createAlbum(UUID artistId, AlbumEntity album) {

        return artistRepository.findById(artistId).flatMap(artist -> {
            album.setAlbumId(UUID.randomUUID());
            album.setArtist(artist);
            return albumRepository.save(album).doOnError(e -> new AlbumServiceException(ERROR_WHILE_SAVING_THE_ALBUMS));
        }).doOnError(e -> new AlbumServiceException(ERROR_WHILE_SAVING_THE_ALBUMS));

    }

    public Mono<AlbumEntity> editAlbum(UUID artistId, UUID albumId, AlbumEntity album) {
        return artistRepository.findById(artistId).flatMap(artist -> {
            album.setArtist(artist);
            return albumRepository.findById(albumId).flatMap(s -> {

                s.setTitle(album.getTitle());
                s.setGenre(album.getGenre());
                s.setReleaseYear(album.getReleaseYear());
                return albumRepository.save(s).doOnError(e -> new AlbumServiceException(ERROR_WHILE_EDITING_THE_ALBUMS));
            }).doOnError(e -> new AlbumServiceException(ERROR_WHILE_EDITING_THE_ALBUMS));
        }).doOnError(e -> new AlbumServiceException(ERROR_WHILE_EDITING_THE_ALBUMS));


    }

}

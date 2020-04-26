package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.Genre;
import com.lexnx.exercises.music.db.repo.AlbumRepository;
import com.lexnx.exercises.music.db.repo.ArtistRepository;
import com.lexnx.exercises.music.service.exceptions.AlbumNotFoundException;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public Page<AlbumEntity> getAlbum(UUID artistId, Genre genre, Pageable pageable) {
        return albumRepository.findByArtistAndGenreLike(artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException()),
                genre, pageable);
    }

    public AlbumEntity createAlbum(UUID artistId, AlbumEntity album) {
        album.setArtist(
                artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException()));
        return albumRepository.save(album);

    }

    public AlbumEntity editAlbum(UUID artistId, Long albumId, AlbumEntity album) {

        AlbumEntity s = albumRepository.findByArtistAndAlbumId(artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException()), albumId)
                .orElseThrow(() -> new AlbumNotFoundException());
        s.setTitle(album.getTitle());
        s.setGenre(album.getGenre());
        s.setReleaseYear(album.getReleaseYear());
        return albumRepository.save(s);
    }

}

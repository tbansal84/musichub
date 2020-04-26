package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.Genre;
import com.lexnx.exercises.music.db.repo.AlbumRepositoryImpl;
import com.lexnx.exercises.music.db.repo.ArtistRepositoryImpl;
import com.lexnx.exercises.music.service.exceptions.AlbumNotFoundException;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AlbumService {
    private final AlbumRepositoryImpl albumRepository;
    private final ArtistRepositoryImpl artistRepository;

    public Page<AlbumEntity> getAlbum(@NotNull UUID artistId, String genre, @NotNull Pageable pageable) {
        return albumRepository.findByArtistAndGenreContaining(artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException()),
                genre == null ? "" : genre, pageable);
    }

    @Transactional
    public AlbumEntity createAlbum(@NotNull UUID artistId, @NotNull AlbumEntity album) {
        album.setArtist(
                artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException()));
        return albumRepository.save(album);

    }

    @Transactional
    public AlbumEntity editAlbum(@NotNull UUID artistId, @NotNull UUID albumId, @NotNull AlbumEntity album) {

        AlbumEntity s = albumRepository.findByArtistAndAlbumId(artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException()), albumId)
                .orElseThrow(() -> new AlbumNotFoundException());
        s.setTitle(album.getTitle());
        s.setGenre(album.getGenre());
        s.setReleaseYear(album.getReleaseYear());
        return albumRepository.save(s);
    }

}

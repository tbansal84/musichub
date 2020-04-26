package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.repo.AlbumRepositoryImpl;
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
public class AlbumService {
    private static final String ARTIST_ID_CANNOT_BE_NULL = "{artistId} cannot be null";
    private static final String PAGEABLE_CANNOT_BE_NULL = "{pageable} cannot be null";
    private static final String ALBUM_CANNOT_BE_NULL = "{album} cannot be null";
    private static final String ALBUM_ID_CANNOT_BE_NULL = "{albumId} cannot be null";
    private final AlbumRepositoryImpl albumRepository;
    private final ArtistRepositoryImpl artistRepository;

    private static final String EMPTY = "";

    public Page<AlbumEntity> getAlbum(UUID artistId, String genre, Pageable pageable) {
        if (artistId == null) {
            throw new IllegalArgumentException(ARTIST_ID_CANNOT_BE_NULL);
        }

        if (pageable == null) {
            throw new IllegalArgumentException(PAGEABLE_CANNOT_BE_NULL);
        }
        return albumRepository.findByArtistAndGenreContaining(artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new),
                genre == null ? EMPTY : genre, pageable);
    }

    @Transactional
    public AlbumEntity createAlbum(UUID artistId, AlbumEntity album) {
        if (artistId == null) {
            throw new IllegalArgumentException(ARTIST_ID_CANNOT_BE_NULL);
        }
        if (album == null) {
            throw new IllegalArgumentException(ALBUM_CANNOT_BE_NULL);
        }
        album.setArtist(
                artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new));
        return albumRepository.save(album);

    }

    @Transactional
    public AlbumEntity editAlbum(UUID artistId, UUID albumId, AlbumEntity album) {
        if (artistId == null) {
            throw new IllegalArgumentException(ARTIST_ID_CANNOT_BE_NULL);
        }
        if (albumId == null) {
            throw new IllegalArgumentException(ALBUM_ID_CANNOT_BE_NULL);
        }
        if (album == null) {
            throw new IllegalArgumentException(ALBUM_CANNOT_BE_NULL);
        }
        final AlbumEntity s = albumRepository.findByArtistAndAlbumId(artistRepository.findById(artistId).orElseThrow(ArtistNotFoundException::new), albumId)
                .orElseThrow(ArtistNotFoundException::new);
        s.setTitle(album.getTitle());
        s.setGenre(album.getGenre());
        s.setReleaseYear(album.getReleaseYear());
        return albumRepository.save(s);
    }

}

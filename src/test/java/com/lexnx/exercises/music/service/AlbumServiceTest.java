package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.db.repo.AlbumRepositoryImpl;
import com.lexnx.exercises.music.db.repo.ArtistRepositoryImpl;
import com.lexnx.exercises.music.service.exceptions.AlbumNotFoundException;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    private static final String ARTIST_NAME = "John wick";
    private static final String ARTIST_ID_CANNOT_BE_NULL = "{artistId} cannot be null";
    @Mock
    private AlbumRepositoryImpl albumRepository;
    @Mock
    private ArtistRepositoryImpl artistRepository;
    private static final String EMPTY = "";


    @Test
    public void getAlbumSuccessful() {
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final UUID artistId = UUID.randomUUID();
        final Pageable pageable = mock(Pageable.class);
        final Page<AlbumEntity> page = mock(Page.class);
        final ArtistEntity artist = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        when(albumRepository.findByArtistAndGenreContaining(artist, EMPTY, pageable)).thenReturn(page);
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        final Page<AlbumEntity> album = albumService.getAlbum(artistId, null, pageable);


        assertEquals(page.getTotalElements(), album.getTotalElements());
        verify(albumRepository, times(1)).findByArtistAndGenreContaining(artist, EMPTY, pageable);
        verify(artistRepository, times(1)).findById(artistId);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    public void getAlbumSuccessfulBlankGenre() {
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final UUID artistId = UUID.randomUUID();
        final String genre = "";
        final Pageable pageable = mock(Pageable.class);
        final Page<AlbumEntity> page = mock(Page.class);
        final ArtistEntity artist = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());

        when(albumRepository.findByArtistAndGenreContaining(artist, genre, pageable)).thenReturn(page);
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        final Page<AlbumEntity> album = albumService.getAlbum(artistId, genre, pageable);


        assertEquals(page.getTotalElements(), album.getTotalElements());
        verify(albumRepository, times(1)).findByArtistAndGenreContaining(artist, genre, pageable);
        verify(artistRepository, times(1)).findById(artistId);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    public void getAlbumArtistNotFoundException() {
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final UUID artistId = UUID.randomUUID();
        final String genre = "pop";
        final Pageable pageable = mock(Pageable.class);

        when(artistRepository.findById(artistId)).thenThrow(new ArtistNotFoundException());

        Assertions.assertThrows(ArtistNotFoundException.class, () ->
                albumService.getAlbum(artistId, genre, pageable)
        );
        verify(artistRepository, times(1)).findById(artistId);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    public void getAlbumArtistIllegalArtistId() {
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);


        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                albumService.getAlbum(null, null, null)
        );
        assertEquals(exception.getMessage(), ARTIST_ID_CANNOT_BE_NULL);
        verifyNoInteractions(albumRepository);
        verifyNoInteractions(artistRepository);
    }

    @Test
    public void getAlbumArtistIllegalPageaable() {
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final UUID artistId = UUID.randomUUID();
        final String genre = "pop";
        final Pageable pageable = null;

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                albumService.getAlbum(artistId, genre, pageable)
        );
        assertEquals(exception.getMessage(), "{pageable} cannot be null");
        verifyNoInteractions(albumRepository);
        verifyNoInteractions(artistRepository);
    }


    @Test
    public void createAlbumSuccess() {
        final UUID artistId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        when(albumRepository.save(album)).thenReturn(album);
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(new ArtistEntity()));
        assertEquals(album, albumService.createAlbum(artistId, album));
        verify(artistRepository, times(1)).findById(artistId);
        verify(albumRepository, times(1)).save(album);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    public void createAlbumArtistNotFoundException() {
        final UUID artistId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        when(artistRepository.findById(artistId)).thenThrow(new ArtistNotFoundException());
        Assertions.assertThrows(ArtistNotFoundException.class, () ->
                albumService.createAlbum(artistId, album)
        );
        verify(artistRepository, times(1)).findById(artistId);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    public void createAlbumIllegalArtistId() {
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                albumService.createAlbum(null, album)
        );
        assertEquals(exception.getMessage(), ARTIST_ID_CANNOT_BE_NULL);
        verifyNoInteractions(artistRepository);
        verifyNoInteractions(albumRepository);
    }

    @Test
    public void createAlbumIllegalAlbum() {
        final UUID artistId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                albumService.createAlbum(artistId, null)
        );
        assertEquals(exception.getMessage(), "{album} cannot be null");
        verifyNoInteractions(artistRepository);
        verifyNoInteractions(albumRepository);
    }

    @Test
    public void editAlbumSuccess() {
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        final ArtistEntity artist = Mockito.mock(ArtistEntity.class);
        when(albumRepository.findByArtistAndAlbumId(artist, albumId)).thenReturn(Optional.of(album));
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        when(albumRepository.save(album)).thenReturn(album);
        assertEquals(album, albumService.editAlbum(artistId, albumId, album));
        verify(artistRepository, times(1)).findById(artistId);
        verify(albumRepository, times(1)).findByArtistAndAlbumId(artist, albumId);
        verify(albumRepository, times(1)).save(album);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }


    @Test
    public void editAlbumAlbumNotFoundException() {
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        final ArtistEntity artist = Mockito.mock(ArtistEntity.class);
        when(albumRepository.findByArtistAndAlbumId(artist, albumId)).thenThrow(new AlbumNotFoundException());
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        lenient().when(albumRepository.save(album)).thenReturn(album);


        Assertions.assertThrows(AlbumNotFoundException.class, () ->
                albumService.editAlbum(artistId, albumId, album)
        );

        verify(artistRepository, times(1)).findById(artistId);
        verify(albumRepository, times(1)).findByArtistAndAlbumId(artist, albumId);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    public void editAlbumArtistNotFoundException() {
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        when(artistRepository.findById(artistId)).thenThrow(new ArtistNotFoundException());
        Assertions.assertThrows(ArtistNotFoundException.class, () ->
                albumService.editAlbum(artistId, albumId, album)
        );
        verify(artistRepository, times(1)).findById(artistId);
        verifyNoMoreInteractions(artistRepository);
        verifyNoMoreInteractions(albumRepository);
    }

    @Test
    public void editAlbumIllegalArtistId() {
        final UUID albumId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                albumService.editAlbum(null, albumId, album)
        );
        assertEquals(exception.getMessage(), ARTIST_ID_CANNOT_BE_NULL);
        verifyNoInteractions(artistRepository);
        verifyNoInteractions(albumRepository);
    }

    @Test
    public void editAlbumIllegalAlbumId() {
        final UUID artistId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        final AlbumEntity album = Mockito.mock(AlbumEntity.class);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                albumService.editAlbum(artistId, null, album)
        );
        assertEquals(exception.getMessage(), "{albumId} cannot be null");
        verifyNoInteractions(artistRepository);
        verifyNoInteractions(albumRepository);
    }

    @Test
    public void editAlbumIllegalAlbum() {
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        final AlbumService albumService = new AlbumService(albumRepository, artistRepository);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                albumService.editAlbum(artistId, albumId, null)
        );
        assertEquals(exception.getMessage(), "{album} cannot be null");
        verifyNoInteractions(artistRepository);
        verifyNoInteractions(albumRepository);
    }

}
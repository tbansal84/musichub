package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.db.repo.ArtistRepositoryImpl;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    private static final String ARTIST_NAME = "John wick";
    @Mock
    private ArtistRepositoryImpl artistRepository;


    @Test
    public void getArtistSuccessful() {
        final ArtistService artistService = new ArtistService(artistRepository);
        final Pageable pageable = mock(Pageable.class);
        final Page<ArtistEntity> page = mock(Page.class);

        when(artistRepository.findAllByArtistNameContaining(ARTIST_NAME, pageable)).thenReturn(page);
        final Page<ArtistEntity> album = artistService.getArtist(ARTIST_NAME, pageable);

        assertEquals(page.getTotalElements(), album.getTotalElements());
        verify(artistRepository, times(1)).findAllByArtistNameContaining(ARTIST_NAME, pageable);
        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void getArtistSuccessfulNameNull() {
        final ArtistService artistService = new ArtistService(artistRepository);
        final Pageable pageable = mock(Pageable.class);
        final Page<ArtistEntity> page = mock(Page.class);

        when(artistRepository.findAllByArtistNameContaining(anyString(), any())).thenReturn(page);
        final Page<ArtistEntity> album = artistService.getArtist(null, pageable);

        assertEquals(page.getTotalElements(), album.getTotalElements());
        verify(artistRepository, times(1)).findAllByArtistNameContaining(anyString(), any());
        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void getArtistNullPagable() {

        final ArtistService artistService = new ArtistService(artistRepository);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                artistService.getArtist(ARTIST_NAME, null)
        );
        assertEquals("{pageable} cannot be null", exception.getMessage());

        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void createArtist() {
        final ArtistService artistService = new ArtistService(artistRepository);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artist = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        when(artistRepository.save(artist)).thenReturn(artist);
        assertEquals(artist, artistService.createArtist(artist));
        verify(artistRepository, times(1)).save(artist);
        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void createArtistNullArtist() {
        final ArtistService artistService = new ArtistService(artistRepository);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                artistService.createArtist(null)
        );
        assertEquals("{artistEntity} cannot be null", exception.getMessage());
        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void editArtist() {
        final ArtistService artistService = new ArtistService(artistRepository);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artist = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        when(artistRepository.save(artist)).thenReturn(artist);
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        assertEquals(artist, artistService.editArtist(artistId, artist));
        verify(artistRepository, times(1)).save(artist);
        verify(artistRepository, times(1)).findById(artistId);
        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void editArtistArtistNotFound() {
        final ArtistService artistService = new ArtistService(artistRepository);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artist = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        when(artistRepository.findById(artistId)).thenThrow(new ArtistNotFoundException());
        Assertions.assertThrows(ArtistNotFoundException.class, () ->
                artistService.editArtist(artistId, artist)
        );

        verify(artistRepository, times(1)).findById(artistId);
        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void editArtistNullArtistId() {
        final ArtistService artistService = new ArtistService(artistRepository);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artist = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                artistService.editArtist(null, artist)
        );
        assertEquals("{artistId} cannot be null", exception.getMessage());

        verifyNoMoreInteractions(artistRepository);
    }

    @Test
    public void editArtistNullArtist() {
        final ArtistService artistService = new ArtistService(artistRepository);
        final UUID artistId = UUID.randomUUID();
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                artistService.editArtist(artistId, null)
        );
        assertEquals("{artistEntity} cannot be null", exception.getMessage());

        verifyNoMoreInteractions(artistRepository);
    }
}
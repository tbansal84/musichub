package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.rest.model.Artist;
import com.lexnx.exercises.music.rest.model.mapper.ArtistMapper;
import com.lexnx.exercises.music.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SuppressWarnings("ALL")
@ExtendWith(MockitoExtension.class)
public class ArtistControllerTest {

    private static final String ARTIST_NAME = "John Wick";
    @Mock
    private ArtistService artistService;
    @Mock
    private ArtistMapper artistMapper;


    @Test
    public void getAlbum() {
        final ArtistController artistController = new ArtistController(artistService, artistMapper);
        final Page<ArtistEntity> page = Mockito.mock(Page.class);
        when(artistService.getArtist(any(), any())).thenReturn(page);
        assertEquals(page, artistController.getArtists(anyString(), any()));
    }

    @Test
    public void createAlbum() {
        final ArtistController artistController = new ArtistController(artistService, artistMapper);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artistEntity = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        when(artistService.createArtist(artistEntity)).thenReturn(artistEntity);
        final Artist artist = new Artist();
        when(artistMapper.modelToEntity(artist)).thenReturn(artistEntity);
        assertEquals(artistId, artistController.createArtists(artist).getBody());
    }

    @Test
    public void createAlbumFailure() {
        final ArtistController artistController = new ArtistController(artistService, artistMapper);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artistEntity = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        final Artist artist = new Artist();
        when(artistService.createArtist(artistEntity)).thenReturn(artistEntity);
        when(artistMapper.modelToEntity(artist)).thenReturn(artistEntity);

        assertEquals(artistId, artistController.createArtists(artist).getBody());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, artistController.createArtists(artist).getStatusCode());
    }

    @Test
    public void editAlbum() {
        final ArtistController artistController = new ArtistController(artistService, artistMapper);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artistEntity = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        when(artistService.editArtist(artistId, artistEntity)).thenReturn(artistEntity);
        final Artist artist = new Artist();
        when(artistMapper.modelToEntity(artist)).thenReturn(artistEntity);
        assertEquals(artistId, artistController.editArtists(artistId, artist).getBody());
    }

    @Test
    public void editAlbumFailure() {
        final ArtistController artistController = new ArtistController(artistService, artistMapper);
        final UUID artistId = UUID.randomUUID();
        final ArtistEntity artistEntity = new ArtistEntity(artistId, ARTIST_NAME, Collections.emptySet());
        when(artistService.editArtist(artistId, artistEntity)).thenReturn(artistEntity);
        final Artist artist = new Artist();
        when(artistMapper.modelToEntity(artist)).thenReturn(artistEntity);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, artistController.editArtists(artistId, artist).getStatusCode());
    }
}
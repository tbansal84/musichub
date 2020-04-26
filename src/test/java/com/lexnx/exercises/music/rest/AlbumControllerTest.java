package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.rest.model.Album;
import com.lexnx.exercises.music.rest.model.mapper.AlbumMapper;
import com.lexnx.exercises.music.service.AlbumService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumControllerTest {
    private static final String ALBUM = "my rock album";
    private static final String GENRE = "pop";
    private static final long RELEASE_YEAR = 1984L;
    @Mock
    private AlbumService albumService;
    @Mock
    private AlbumMapper albumMapper;


    @Test
    public void getAlbum() {
        final AlbumController albumController = new AlbumController(albumService, albumMapper);
        final Page<AlbumEntity> page = Mockito.mock(Page.class);
        when(albumService.getAlbum(any(), anyString(), any())).thenReturn(page);
        assertEquals(page, albumController.getAlbum(any(), anyString(), any()));
    }

    @Test
    public void createAlbum() {
        final AlbumController albumController = new AlbumController(albumService, albumMapper);
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        final ArtistEntity artist = new ArtistEntity();
        final AlbumEntity albumEntity = new AlbumEntity(albumId, ALBUM, artist, GENRE, RELEASE_YEAR);
        when(albumService.createAlbum(any(), any())).thenReturn(albumEntity);

        Album album = iniitalizeAlbum(albumId);
        assertEquals(albumId, albumController.createAlbum(artistId, album).getBody());
    }

    @Test
    public void createAlbumFailure() {
        final AlbumController albumController = new AlbumController(albumService, albumMapper);
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        when(albumService.createAlbum(any(), any())).thenReturn(null);
        Album album = iniitalizeAlbum(albumId);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, albumController.createAlbum(artistId, album).getStatusCode());
    }

    @Test
    public void editAlbum() {
        final AlbumController albumController = new AlbumController(albumService, albumMapper);
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        final ArtistEntity artist = new ArtistEntity();
        final AlbumEntity albumEntity = new AlbumEntity(albumId, ALBUM, artist, GENRE, RELEASE_YEAR);
        when(albumService.editAlbum(any(), any(), any())).thenReturn(albumEntity);

        Album album = iniitalizeAlbum(albumId);
        assertEquals(HttpStatus.OK
                , albumController.editAlbum(artistId, albumId, album).getStatusCode());
    }

    @Test
    public void editAlbumFailure() {
        final AlbumController albumController = new AlbumController(albumService, albumMapper);
        final UUID artistId = UUID.randomUUID();
        final UUID albumId = UUID.randomUUID();
        when(albumService.editAlbum(any(), any(), any())).thenReturn(null);

        Album album = iniitalizeAlbum(albumId);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, albumController.editAlbum(artistId, albumId, album).getStatusCode());
    }

    private Album iniitalizeAlbum(UUID albumId) {
        return new Album(albumId, GENRE, ALBUM, 1984L, Collections.emptyList());
    }

}
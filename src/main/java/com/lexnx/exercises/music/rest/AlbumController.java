package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.discogs.vo.tracks.Tracklist;
import com.lexnx.exercises.music.service.AlbumService;
import com.lexnx.exercises.music.service.DiscogsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/artists/{artistId}/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final DiscogsService discogsService;


    @GetMapping
    public Flux<AlbumEntity> getAlbum(@PathVariable UUID artistId, @RequestParam(required = false) String genre,
    @SortDefault.SortDefaults({
            @SortDefault(sort = "title", direction = Sort.Direction.DESC),
            @SortDefault(sort = "albumId", direction = Sort.Direction.ASC)
    }) Sort sort) {
        return albumService.getAlbum(artistId, genre, sort);

    }

    @PostMapping
    //TODO: convert Entities to VOs/DTOs
    public Mono<AlbumEntity> createAlbum(@PathVariable UUID artistId, @RequestBody AlbumEntity album) {
        return albumService.createAlbum(artistId, album);
    }

    @PutMapping("{albumId}")
    //TODO: convert Entities to VOs/DTOs
    public Mono<AlbumEntity> editAlbum(@PathVariable UUID artistId, @PathVariable UUID albumId, @RequestBody AlbumEntity album) {
        return albumService.editAlbum(artistId, albumId, album);
    }

    @GetMapping(value = "{albumId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Tracklist>> getAlbumWithTracks(@PathVariable UUID artistId, @PathVariable UUID albumId) {
        return discogsService.findTracks(artistId, albumId);
    }


}

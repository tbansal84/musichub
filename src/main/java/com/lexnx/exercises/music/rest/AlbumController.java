package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.rest.model.Album;
import com.lexnx.exercises.music.rest.model.mapper.AlbumMapper;
import com.lexnx.exercises.music.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/artists/{artistId}/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper albumMapper;


    @GetMapping
    public Page<AlbumEntity> getAlbum(@PathVariable UUID artistId, @RequestParam(required = false) String genre, @PageableDefault
    @SortDefault.SortDefaults({
            @SortDefault(sort = "releaseYear", direction = Sort.Direction.DESC)
    })
            Pageable pageable) {
        return albumService.getAlbum(artistId, genre, pageable);

    }

    @PostMapping
    public ResponseEntity<UUID> createAlbum(@PathVariable UUID artistId, @Valid @RequestBody Album album) {
        final AlbumEntity entity = albumService.createAlbum(artistId, albumMapper.modelToEntity(album));
        if (entity != null) {
            return ResponseEntity.ok(entity.getAlbumId());
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("{albumId}")
    public ResponseEntity<Void> editAlbum(@PathVariable UUID artistId, @PathVariable UUID albumId, @Valid @RequestBody Album album) {
        if (albumService.editAlbum(artistId, albumId, albumMapper.modelToEntity(album)) != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }

    }

}

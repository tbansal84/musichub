package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.rest.model.Artist;
import com.lexnx.exercises.music.rest.model.mapper.ArtistMapper;
import com.lexnx.exercises.music.service.ArtistService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("artists")
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    @GetMapping
    public Page<ArtistEntity> getArtists(@RequestParam(required = false) String artistName,
                                         @PageableDefault(page = 0, size = 10)
                                         @SortDefault.SortDefaults({
                                                 @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                                                 @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                                         })
                                                 Pageable pageable) {
        return artistService.getArtist(artistName, pageable);

    }

    @PostMapping
    public ResponseEntity<UUID> createArtists(@RequestBody Artist artist) {
        return ResponseEntity.ok(artistService.createArtist(artistMapper.modelToEntity(artist)).getArtistId());


    }

    @PutMapping("{artistId}")
    public ResponseEntity<Void> editArtists(@PathVariable UUID artistId, @RequestBody Artist artist) {
        artistService.editArtist(artistId, artistMapper.modelToEntity(artist));
        return ResponseEntity.ok().build();

    }
}

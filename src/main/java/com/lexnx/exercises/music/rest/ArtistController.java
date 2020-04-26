package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.rest.model.Artist;
import com.lexnx.exercises.music.rest.model.mapper.ArtistMapper;
import com.lexnx.exercises.music.service.ArtistService;
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
@RequestMapping("artists")
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    @GetMapping
    public Page<ArtistEntity> getArtists(@RequestParam(required = false) String artistName,
                                         @PageableDefault
                                         @SortDefault.SortDefaults({
                                                 @SortDefault(sort = "artistName", direction = Sort.Direction.DESC)
                                         })
                                                 Pageable pageable) {
        return artistService.getArtist(artistName, pageable);

    }

    @PostMapping
    public ResponseEntity<UUID> createArtists(@Valid @RequestBody Artist artist) {
        final ArtistEntity artistEntity = artistService.createArtist(artistMapper.modelToEntity(artist));
        if (artistEntity != null) {
            return ResponseEntity.ok(artistEntity.getArtistId());
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("{artistId}")
    public ResponseEntity<Void> editArtists(@PathVariable UUID artistId, @Valid @RequestBody Artist artist) {
        if (artistService.editArtist(artistId, artistMapper.modelToEntity(artist)) != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }


    }
}

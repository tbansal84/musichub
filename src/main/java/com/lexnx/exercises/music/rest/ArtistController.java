package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping()
    public Flux<ArtistEntity> getArtists(@RequestParam(required = false) String artistName,
                                         @SortDefault.SortDefaults({
                                                 @SortDefault(sort = "artistName", direction = Sort.Direction.DESC),
                                                 @SortDefault(sort = "artistNameId", direction = Sort.Direction.ASC)
                                         }) Sort sort
    ) {
        return artistService.getArtist(artistName, sort);

    }

    @PostMapping
    //TODO: convert Entities to VOs/DTOs
    public Mono<ArtistEntity> createArtists(@RequestBody ArtistEntity artist) {
        return artistService.createArtist(artist);


    }

    @PutMapping("/{artistId}")
    //TODO: convert Entities to VOs/DTOs
    public void editArtists(@PathVariable UUID artistId, @RequestBody ArtistEntity artist) {
        artistService.editArtist(artistId, artist);

    }
}

package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.db.repo.ArtistRepository;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public Page<ArtistEntity> getArtist(String artistName, Pageable pageable) {
        return artistRepository.findAllByArtistnameLike(artistName, pageable);
    }

    public ArtistEntity createArtist(ArtistEntity album) {
        return artistRepository.save(album);

    }

    public ArtistEntity editArtist(UUID artistId, ArtistEntity artistEntity) {
        ArtistEntity artist = artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException());
        artist.setArtistname(artistEntity.getArtistname());
        return artistRepository.save(artist);

    }
}

package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.db.repo.ArtistRepositoryImpl;
import com.lexnx.exercises.music.service.exceptions.ArtistNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ArtistService {
    private final ArtistRepositoryImpl artistRepository;

    public Page<ArtistEntity> getArtist(String artistName, @NotNull Pageable pageable) {
        return artistRepository.findAllByArtistNameContaining(artistName == null ? "" : artistName, pageable);
    }
    @Transactional
    public ArtistEntity createArtist(@NotNull ArtistEntity album) {
        return artistRepository.save(album);

    }
    @Transactional
    public ArtistEntity editArtist(@NotNull UUID artistId, @NotNull ArtistEntity artistEntity) {
        ArtistEntity artist = artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException());
        artist.setArtistName(artistEntity.getArtistName());
        return artistRepository.save(artist);

    }
}

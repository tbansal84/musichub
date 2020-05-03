package com.lexnx.exercises.music.db.repo;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ArtistRepository extends ReactiveMongoRepository<ArtistEntity, UUID> {


    Flux<ArtistEntity> findAllByArtistNameContaining(String artistName, Sort sort);
}

package com.lexnx.exercises.music.db.repo;

import com.lexnx.exercises.music.db.model.AlbumEntity;
import com.lexnx.exercises.music.db.model.ArtistEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface AlbumRepository extends ReactiveMongoRepository<AlbumEntity, UUID> {
    Flux<AlbumEntity> findByArtistAndGenreContaining(Mono<ArtistEntity> artist, String genre, Sort sort);

}

package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.db.repo.AlbumRepository;
import com.lexnx.exercises.music.db.repo.ArtistRepository;
import com.lexnx.exercises.music.discogs.DiscogsRestServiceCommunicator;
import com.lexnx.exercises.music.discogs.vo.albums.Response;
import com.lexnx.exercises.music.discogs.vo.tracks.AlbumDetails;
import com.lexnx.exercises.music.discogs.vo.tracks.Tracklist;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class DiscogsService {

    private final DiscogsRestServiceCommunicator discogsRestServiceCommunicator;
    private String searchEndpoint;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public DiscogsService(DiscogsRestServiceCommunicator discogsRestServiceCommunicator,
                          @Value("${discogs.rest.service.communication.searchEndpoint}") String searchEndpoint,
                          ArtistRepository artistRepository,
                          AlbumRepository albumRepository) {
        this.discogsRestServiceCommunicator = discogsRestServiceCommunicator;
        this.searchEndpoint = searchEndpoint;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }


    public Mono<List<Tracklist>> findTracks(UUID artistId, UUID albumId) {

        return artistRepository.findById(artistId).zipWith(albumRepository.findById(albumId), (m1, m2) ->
        {
            final MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
            final List<String> pathParams = Collections.emptyList();
            requestParams.put("artist", List.of(m1.getArtistName()));
            requestParams.put("master", List.of(m2.getTitle()));
            return getResultFromDiscogs(requestParams, pathParams);
        }).flatMap(m -> m);

    }


    private Mono<List<Tracklist>> getResultFromDiscogs(MultiValueMap<String, String> requestParams, List<String> pathParams) {
        return discogsRestServiceCommunicator.get(searchEndpoint, pathParams, requestParams, Response.class).flatMap(responseFromSearch ->
                responseFromSearch.getResults().stream().findFirst().map(album ->
                {
                    MultiValueMap<String, String> requestParamsForTracks = new LinkedMultiValueMap<>();
                    List<String> pathParamsForTracks = List.of(String.valueOf(album.getId()));
                    return discogsRestServiceCommunicator.get("/masters/{masterId}",
                            pathParamsForTracks,
                            requestParamsForTracks,
                            AlbumDetails.class);
                }).get()
        ).flatMap(mono -> Mono.just(mono.getTracklist()));
    }


}

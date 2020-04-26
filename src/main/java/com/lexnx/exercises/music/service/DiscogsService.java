package com.lexnx.exercises.music.service;

import com.lexnx.exercises.music.client.discogs.DiscogsRestServiceCommunicator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DiscogsService {

    private final DiscogsRestServiceCommunicator discogsRestServiceCommunicator;

    public DiscogsService(DiscogsRestServiceCommunicator discogsRestServiceCommunicator) {
        this.discogsRestServiceCommunicator = discogsRestServiceCommunicator;
    }

    public Mono<String> findTracks(String artistId, String albumId) {
        return discogsRestServiceCommunicator.get(String.format("search?artist=%1s&title=%2s", artistId, albumId), String.class);

    }
}

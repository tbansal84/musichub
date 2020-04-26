package com.lexnx.exercises.music.rest;

import com.lexnx.exercises.music.service.DiscogsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.validation.Validator;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@AllArgsConstructor
public class AlbumSearchRouter {

    private final DiscogsService discogsService;

    @Bean
    public RouterFunction<ServerResponse> records() {
        HandlerFunction<ServerResponse> getAlbum = (serverRequest) -> {
            String artistId = serverRequest.pathVariable("artistId");
            String albumId = serverRequest.pathVariable("albumId");
            return ok().body(discogsService.findTracks(artistId, albumId), String.class);
        };
        return route(GET("/artists/{artistId}/albums/{albumId}").and(accept(MediaType.APPLICATION_JSON)), getAlbum);
    }

}

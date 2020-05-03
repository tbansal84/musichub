/**
 * Copyright (C) 2020, Ingenico ePayments - https://www.ingenico.com/epayments
 * <p>
 * All rights reserved
 * <p>
 * This software is owned by Ingenico ePayments (hereinafter the Owner).
 * No material from this software owned, operated and controlled by the Owner
 * may be copied, reproduced, republished, uploaded, posted, transmitted, or
 * distributed in any way by any third party without the Owner's explicit
 * written consent. All intellectual and other property rights of this software
 * are held by the Owner. No rights of any kind are licensed or assigned or
 * shall otherwise pass to third parties making use of this software. Said use
 * by third parties shall only be in accordance with applicable license
 * agreements between such party and the Owner. Making, acquiring, or using
 * unauthorized copies of this software or other copyrighted materials may
 * result in disciplinary or legal action as the circumstances may warrant.
 */
package com.lexnx.exercises.music.discogs;

import com.lexnx.exercises.music.discogs.exceptions.DiscogsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class DiscogsRestServiceCommunicator {

    private final WebClient webClient;

    public DiscogsRestServiceCommunicator(@Qualifier("discogsWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public <R> Mono<R> get(String url, List<String> pathParameters, MultiValueMap<String, String> requestParameters, Class<R> responseClass) {
        return sendRequest(url, pathParameters, requestParameters)
                .onStatus(HttpStatus::isError, exceptionFunction())
                .bodyToMono(responseClass)
                .doOnError(DiscogsServiceException::new);
    }
    public <R> Mono<R> get(String url, List<String> pathParameters, MultiValueMap<String, String> requestParameters, ParameterizedTypeReference<R> responseClass) {
        return sendRequest(url, pathParameters, requestParameters)
                .onStatus(HttpStatus::isError, exceptionFunction())
                .bodyToMono(responseClass)
                .doOnError(DiscogsServiceException::new);
    }


    private WebClient.ResponseSpec sendRequest(String url, List<String> pathParameters, MultiValueMap<String, String> requestParameters) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(url)
                .queryParams(requestParameters)
                .build(pathParameters.toArray())
        ).accept(MediaType.APPLICATION_JSON).retrieve();
    }

    private Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction() {
        return response -> response.bodyToMono(String.class).flatMap(e ->
                Mono.error(new DiscogsServiceException(String.valueOf(response.rawStatusCode()))));
    }


}

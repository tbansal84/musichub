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
package com.lexnx.exercises.music.client.discogs;

import com.lexnx.exercises.music.client.discogs.exceptions.DiscogsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Slf4j
@Component
public class DiscogsRestServiceCommunicator {

    private final WebClient webClient;

    public DiscogsRestServiceCommunicator(@Qualifier("discogsWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public <T, R> Mono<R> get(String url, Class<R> responseClass) {
        return webClient.get().uri(url).retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class).flatMap(e ->
                        Mono.error(new DiscogsServiceException(String.valueOf(response.rawStatusCode())))
                ))
                .bodyToMono(responseClass)
                .doOnError(e -> {
                    throw new DiscogsServiceException(e);
                })
                .onErrorResume(e -> Mono.error(new DiscogsServiceException(e))).retry(2);
    }


}

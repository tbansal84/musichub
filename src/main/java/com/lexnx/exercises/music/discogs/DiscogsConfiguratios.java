package com.lexnx.exercises.music.discogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Configuration

public class DiscogsConfiguratios {

    @Bean
    public ExchangeStrategies discogsExchangeStartegies(ObjectMapper objectMapper) {
        return ExchangeStrategies
                .builder().codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs();
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));

                }).build();
    }

    @Bean
    public HttpClient discogsHttpClient(@Value("${discogs.rest.service.communication.timeout}") int timeout) {
        return HttpClient.create()
                .tcpConfiguration(tcpClient -> tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout));
    }


    @Bean
    public WebClient discogsWebClient(
            @Qualifier("discogsHttpClient") HttpClient httpClient,
            ExchangeStrategies discogsExchangeStartegies,
            @Value("${discogs.rest.service.communication.baseurl}") String baseUrl,
            @Value("${discogs.rest.service.authentication.keyenc}") String key,
            @Value("${discogs.rest.service.authentication.secretenc}") String secret) {
        return WebClient.builder()
                .exchangeStrategies(discogsExchangeStartegies)
                .defaultHeaders(header -> addAuthorizationHeader(header, key, secret))
                .baseUrl(baseUrl)
                .build();
    }

    private void addAuthorizationHeader(HttpHeaders header, String key, String secret) {
        header.set("Authorization", "Discogs key=" + key + ", secret=" + secret);
    }


}

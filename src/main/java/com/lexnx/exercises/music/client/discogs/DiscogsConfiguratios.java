package com.lexnx.exercises.music.client.discogs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration

public class DiscogsConfiguratios {

//    @Bean
//    public ExchangeStrategies discogsExchangeStartegies(Jackson2JsonEncoder disclosedJackson2JsonEncoder,
//                                                        Jackson2JsonDecoder jackson2JsonDecoder) {
//        return ExchangeStrategies
//                .builder().codecs(codecConfigurer -> {
//                    codecConfigurer.defaultCodecs().jackson2JsonEncoder(disclosedJackson2JsonEncoder);
//                    codecConfigurer.defaultCodecs().jackson2JsonDecoder(jackson2JsonDecoder);
//                }).build();
//    }

    @Bean
    public HttpClient discogsHttpClient(@Value("${discogs.rest.service.communication.timeout}") int timeout,
                                        @Qualifier("discogsSslContext") SslContext sslContext) {
        return HttpClient.create()
                .tcpConfiguration(tcpClient -> tcpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout))
                .secure(t -> t.sslContext(sslContext));
    }

    @Bean
    public SslContext discogsSslContext(@Value("${discogs.rest.service.communication.protocols}") String protocols) throws SSLException {
            return SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .protocols(protocols.split(","))
                .build();
    }

    @Bean
    public WebClient discogsWebClient(
            @Qualifier("discogsHttpClient") HttpClient httpClient,
            @Value("${discogs.rest.service.communication.baseurl}") String baseUrl,
            @Value("${discogs.rest.service.authentication.keyenc}") String key,
            @Value("${discogs.rest.service.authentication.secretenc}") String secret) throws
            SSLException {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeaders(header -> header.setBasicAuth(key, secret))
                .baseUrl(baseUrl)
                .build();
    }


}

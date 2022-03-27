package com.myproject.transactionservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class SpringConfiguration {

    @Value(value = "${rest.client.connect.timeout:30}")
    private int restClientConnectTimeout;
    @Value(value = "${rest.client.read.timeout:30}")
    private int restClientReadTimeout;

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectionRequestTimeout((restClientConnectTimeout * 1000));
        clientHttpRequestFactory.setConnectTimeout((restClientConnectTimeout * 1000));
        clientHttpRequestFactory.setReadTimeout((restClientReadTimeout * 1000));
        clientHttpRequestFactory.setBufferRequestBody(true);
        return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .requestFactory(this::clientHttpRequestFactory)
                .setConnectTimeout(Duration.ofSeconds(restClientConnectTimeout))
                .setReadTimeout(Duration.ofSeconds(restClientReadTimeout))
                .build();
    }
}

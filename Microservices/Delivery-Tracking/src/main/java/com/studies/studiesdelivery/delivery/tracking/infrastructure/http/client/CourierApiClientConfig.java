package com.studies.studiesdelivery.delivery.tracking.infrastructure.http.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class CourierApiClientConfig {

    @Bean
    @LoadBalanced
    public  RestClient.Builder loadBalancedRestClientBuilder(){
        return RestClient.builder();
    }

    @Bean
    public CourierApiClient courierApiClient(RestClient.Builder builder){
        RestClient restClient = builder.baseUrl("http://courier-management")
                .requestFactory(generateClientRequestFactory())
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(CourierApiClient.class);
    }

    private ClientHttpRequestFactory generateClientRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(10));//quanto tempo esperar para estabelecer uma conexão
        factory.setReadTimeout(Duration.ofMillis(200));//quanto tempo esperar uma resposta após conexão estabelecida
        return  factory;
    }
}

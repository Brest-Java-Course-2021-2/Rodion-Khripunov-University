package com.epam.brest.web_app.config;

import com.epam.brest.service.UniversityDtoService;
import com.epam.brest.service.UniversityService;
import com.epam.brest.service.rest.UniversityDtoServiceRest;
import com.epam.brest.service.rest.UniversityServiceRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    UniversityDtoService universityDtoService() {
        String url = String.format("%s://%s:%d/university_dto", protocol, host, port);
        return new UniversityDtoServiceRest(url, restTemplate());
    }

    ;

    @Bean
    UniversityService universityService() {
        String url = String.format("%s://%s:%d/universities", protocol, host, port);
        return new UniversityServiceRest(url, restTemplate());
    }

}

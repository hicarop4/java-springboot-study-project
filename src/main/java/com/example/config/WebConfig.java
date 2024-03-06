package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // private static final MediaType MEDIA_TYPE_APLLICATION_YML =
    // MediaType.valueOf("application/x-yaml");
    // @Override
    // public void extendMessageConverters(List<HttpMessageConverter<?>> converters)

    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**").allowedMethods("*").allowedOrigins(allowedOrigins).allowCredentials(true);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // VIA QUERY PARAM:
        // configurer.favorParameter(true)
        // .ignoreAcceptHeader(false)
        // .defaultContentType(MediaType.APPLICATION_JSON)
        // .mediaType("json", MediaType.APPLICATION_JSON)
        // .mediaType("xml", MediaType.APPLICATION_XML);

        // VIA HEADER PARAM:
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);

    }

}

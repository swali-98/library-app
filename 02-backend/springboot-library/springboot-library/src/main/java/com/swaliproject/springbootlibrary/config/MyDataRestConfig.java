package com.swaliproject.springbootlibrary.config;


import com.swaliproject.springbootlibrary.entity.Book;
import com.swaliproject.springbootlibrary.entity.Message;
import com.swaliproject.springbootlibrary.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrigins="http://localhost:3000";

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors){
        HttpMethod[] unsupportedActions ={HttpMethod.POST,HttpMethod.DELETE,HttpMethod.PATCH,HttpMethod.PUT}  ;

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Message.class);
        disableHttpMethods(Book.class,config,unsupportedActions);
        disableHttpMethods(Review.class,config,unsupportedActions);
        cors.addMapping(config.getBasePath()+"/**")
                .allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] unsupportedActions) {

        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable(unsupportedActions));
    }
}

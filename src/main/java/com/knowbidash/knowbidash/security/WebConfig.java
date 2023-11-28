package com.knowbidash.knowbidash.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
<<<<<<< HEAD:src/main/java/com/knowbidash/knowbidash/security/WebConfig.java
@EnableWebSecurity
public class WebConfig {
    @Value("${cors.origins}")
    private String corsOrigins;

=======
public class JWTconfig {
>>>>>>> db812e1b789f64420aad1824a8b61780bef3b311:src/main/java/com/knowbidash/knowbidash/security/JWTconfig.java
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**").allowedOrigins(corsOrigins).allowedMethods("*");
            }
        };
    }
}

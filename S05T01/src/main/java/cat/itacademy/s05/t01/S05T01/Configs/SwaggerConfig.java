package cat.itacademy.s05.t01.S05T01.Configs;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("webflux-api")
                .pathsToMatch("/game/**", "/player/**", "/ranking")
                .build();
    }
}
package co.onefi.bankstatement;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.PathSelectors;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    @ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(input -> PathSelectors.regex("/statement.*").apply(input)
                //   PathSelectors.regex("/wallet.*").apply(input)
                //  || PathSelectors.regex("/oauth.*").apply(input)
                //    || PathSelectors.regex("/investments.*").apply(input)
                )
                .build();
    }
}

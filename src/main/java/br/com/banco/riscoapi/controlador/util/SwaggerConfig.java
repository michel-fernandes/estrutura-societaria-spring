package br.com.banco.riscoapi.controlador.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket configuracao(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.banco.riscoapi"))
                .build()
                .apiInfo(informacaoAPI());
    }

    private ApiInfo informacaoAPI() {
        return new ApiInfoBuilder()
                .title("Comprometimento financeiro")
                .description("Etrutura de dados em árvore")
                .version("0.0.1")
                .build();
    }
}

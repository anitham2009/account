package com.app.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * Swagger configuration.
 * @author Anitha Manoharan
 *
 */
@Configuration 
public class SpringSwaggerConfig {
	 
	/**
	 * Set Swagger documentation details.
	 * @return Docket
	 */
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).useDefaultResponseMessages(false) 
          .select()
          .apis(RequestHandlerSelectors.basePackage("com.app.account"))              
          .paths(PathSelectors.any())                        
          .build();                                           
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Account Service API").version("1.0")
                .description("Account Service")
                .build();
    }
}
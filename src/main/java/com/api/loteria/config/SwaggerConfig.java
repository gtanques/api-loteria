package com.api.loteria.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.api.loteria.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("API REST LOTERIA", "Documentação API Gerador de Combinações para Loteria", "1.0", "urn:tos",
		          new Contact(
		        		  "Gustavo",
		        		  "https://github.com/gtanques",
		        		  "saintcalm1@gmail.com"),
		          "Apache 2.0",
		          "http://www.apache.org/licenses/LICENSE-2.0",
		          new ArrayList<>()
		          );
		
		return apiInfo;
	}
}

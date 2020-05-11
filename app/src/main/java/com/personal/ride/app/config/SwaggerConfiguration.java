package com.personal.ride.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
public class SwaggerConfiguration {

    @Value("${swagger.host}")
    private String host;
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.version}")
    private String version;
    @Value("${swagger.base-package}")
    private String basePackage;
    @Value("${swagger.license}")
    private String license;
    @Value("${swagger.licenseUrl}")
    private String licenseUrl;
    @Value("${swagger.contact.name}")
    private String name;
    @Value("${swagger.contact.url}")
    private String url;
    @Value("${swagger.contact.email}")
    private String email;

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .description(description)
                .license(license)
                .licenseUrl(licenseUrl)
                .contact(new Contact(name,url,email))
                .build();
    }

	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .host(host)
                .select()
				.apis(RequestHandlerSelectors.basePackage(basePackage))
				.paths(PathSelectors.any())
                .build();
	}
}

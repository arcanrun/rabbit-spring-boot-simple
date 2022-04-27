package com.arcanrun.rabbitmq.config;

import com.arcanrun.rabbitmq.RabbitmqPlaygroundApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableSwagger2
@PropertySource("classpath:git.properties")
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String GIT_INFO_MESSAGE_TMPL = "" +
            "<h3>------------- GIT INFO -------------</h3>" +
            "<p><b>Git branch:</b> %s</p> " +
            "<p><b>Commit SHA:</b> %s</p>" +
            "<p><b>Commit Date:</b> %s</p>" +
            "<h3>------------ BUILD INFO -----------</h3>" +
            "<p><b>App Version:</b> %s</p>" +
            "<p><b>Build Date:</b> %s</p>";
    private static final String SECURITY_REFERENCE = "Bearer";
    private static final String AUTHORIZATION = "Authorization";
    private static final String VERSION = "1.0";
    private static final String TITLE = "RabbitMQ Simple example";


    private BuildProperties buildProperties;

    @Value("${git.commit.time}")
    private String commitDate;

    @Value("${git.branch}")
    private String gitBranch;

    @Value("${git.commit.id.abbrev}")
    private String commitSha;

    private static List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference(SECURITY_REFERENCE, authorizationScopes));
    }

    private static SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(operationContext ->
                        PathSelectors.regex("/.*").test(operationContext.requestMappingPattern()))
                .build();
    }

    private static ApiKey apiKey() {
        return new ApiKey(SECURITY_REFERENCE, AUTHORIZATION, "header");
    }

    @Bean
    public Docket docket() {
        List<Response> responseMessages = new ArrayList<>();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(RabbitmqPlaygroundApplication.class.getPackage().getName()))
                .paths(PathSelectors.any()).build()
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                // responses
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.DELETE, responseMessages)
                .globalResponses(HttpMethod.POST, responseMessages)
                .globalResponses(HttpMethod.GET, responseMessages)
                .globalResponses(HttpMethod.PUT, responseMessages);


    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .description(getDescription())
                .title(TITLE)
                .version(VERSION)
                .build();
    }

    private String getDescription() {
        var time = buildProperties.getTime();
        var version = buildProperties.getVersion();

        return String.format(GIT_INFO_MESSAGE_TMPL, gitBranch, commitSha, getIsoDateStr(commitDate), version, time);
    }

    private String getIsoDateStr(String date) {
        var zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));

        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());

        return zonedDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Autowired
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }
}

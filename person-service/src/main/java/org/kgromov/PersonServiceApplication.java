package org.kgromov;


import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kgromov.model.CreditCard;
import org.kgromov.model.Person;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PersonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate loadbalancedRestTemplate() {
        return new RestTemplate();
    }
}

@Slf4j
@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
class PersonController {
    private final RestTemplate loadbalancedRestTemplate;
    private final DiscoveryClient discoveryClient;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable long id) {
        return new Person(100, "John", "Doe");
    }

    @GetMapping("/{id}/credit-card")
    public CreditCard getPersonCreditCard(
            @PathVariable long id,
            @Value("${account:app:service-id}") String accountServiceId
    ) {
        discoveryClient.getInstances("account-service").forEach(i -> {
            log.debug("SERVICE ID = {}", i.getServiceId());
            log.debug("SCHEME = {}", i.getScheme());
            log.debug("URI = {}", i.getUri().toString());
            log.debug("PORT = {}", i.getPort());
            log.debug("HOST = {}", i.getHost());
            log.debug("Metadata = {}", i.getMetadata());
        });
        return RestClient.create(loadbalancedRestTemplate)
                .get()
                .uri("http://{accountServiceId}/api/accounts/{id}/credit-card", accountServiceId, id)
                .retrieve()
                .body(CreditCard.class);
    }
}

@Component
class InstallOpenTelemetryAppender implements InitializingBean {

    private final OpenTelemetry openTelemetry;

    InstallOpenTelemetryAppender(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @Override
    public void afterPropertiesSet() {
        OpenTelemetryAppender.install(this.openTelemetry);
    }

}

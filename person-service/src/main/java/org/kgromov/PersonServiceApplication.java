package org.kgromov;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.kgromov.model.CreditCard;
import org.kgromov.model.Person;
import org.kgromov.model.PersonInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
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
    public CreditCard getPersonCreditCard(@PathVariable long id) {
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
                .uri("http://account-service/api/accounts/{id}/credit-card", id)
                .retrieve()
                .body(CreditCard.class);
    }
}

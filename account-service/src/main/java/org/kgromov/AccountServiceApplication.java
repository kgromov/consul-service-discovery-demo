package org.kgromov;


import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.kgromov.model.CreditCard;
import org.kgromov.model.CreditCardPrivateInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
class AccountController {

    @GetMapping("/{accountNumber}")
    public CreditCard getCreditCard(@PathVariable String accountNumber) {
        return new CreditCard(new Faker().business());
    }

    @GetMapping("/{personId}/credit-card")
    public CreditCardPrivateInfo getPersonCreditCard(@PathVariable long personId) {
        var creditCard = new CreditCard(new Faker().business());
        return CreditCardPrivateInfo.builder()
                .ownerId(personId)
                .number(creditCard.creditCardNumber())
                .type(creditCard.creditCardType())
                .expirationDate(creditCard.creditCardExpiry())
                .cvv(StringUtils.leftPad(new Faker().number().digits(3), 3, '0'))
                .build();
    }
}

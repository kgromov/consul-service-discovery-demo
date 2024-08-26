package org.kgromov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.javafaker.Business;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {
    @JsonIgnore
    @Delegate
    private Business creditCard;

   /* public String getNumber() {
        return creditCard.creditCardNumber();
    }

    public String getExpiration() {
        return creditCard.creditCardExpiry();
    }

    public String getType() {
        return creditCard.creditCardType();
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number=" + creditCard.creditCardNumber() +
                ", type=" + creditCard.creditCardType() +
                ", expiry=" + creditCard.creditCardExpiry() +
                '}';
    }*/
}

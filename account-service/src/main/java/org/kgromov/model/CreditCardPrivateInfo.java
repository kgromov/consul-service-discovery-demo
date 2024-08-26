package org.kgromov.model;

import lombok.*;


@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreditCardPrivateInfo {
    private long ownerId;
    private String number;
    private String type;
    private String expirationDate;
    private String cvv;
}

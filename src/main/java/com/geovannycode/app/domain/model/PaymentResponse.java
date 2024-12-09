package com.geovannycode.app.domain.model;

public record PaymentResponse(
        String id,
        Long amount,
        Currency currency,
        String status
) {
}

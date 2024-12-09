package com.geovannycode.app.domain.model;

import java.math.BigDecimal;

public record PaymentRequest(
        String description,
        BigDecimal amount,
        Currency currency,
        String stripeEmail,
        String stripeToken
) {
}

package com.geovannycode.app.domain;

import com.geovannycode.app.domain.model.Currency;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;
    private BigDecimal amount;
    private String description;
    private Currency currency;
    private String status;
    private String stripeEmail;
    private String stripeToken;
    private String paymentIntentId;
    private LocalDateTime createdAt  = LocalDateTime.now();

    public PaymentEntity() {
    }

    public PaymentEntity(Long id, BigDecimal amount, String description, Currency currency, String status, String stripeEmail, String stripeToken, String paymentIntentId, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.currency = currency;
        this.status = status;
        this.stripeEmail = stripeEmail;
        this.stripeToken = stripeToken;
        this.paymentIntentId = paymentIntentId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    public void setPaymentIntentId(String paymentIntentId) {
        this.paymentIntentId = paymentIntentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

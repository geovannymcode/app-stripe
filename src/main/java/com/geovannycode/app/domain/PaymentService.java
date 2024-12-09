package com.geovannycode.app.domain;

import com.geovannycode.app.domain.model.PaymentRequest;
import com.geovannycode.app.domain.model.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.secret.key}")
    String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponse processPayment(PaymentRequest request) throws StripeException {
        // Crear PaymentIntent con PaymentMethod
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(request.amount().multiply(BigDecimal.valueOf(100)).longValue()) // Convertir a centavos
                .setCurrency(request.currency().name().toLowerCase())
                .setPaymentMethod("pm_card_visa") // Usar PaymentMethod de prueba (por ejemplo, pm_card_visa)
                .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL) // Confirmar manualmente
                .setConfirm(true) // Confirmar automáticamente el pago
                .setReturnUrl("http://localhost:8080/result") // URL donde se redirige después del pago
                .build();

        // Crear el PaymentIntent
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Guardar la transacción en la base de datos
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(request.amount());
        paymentEntity.setCurrency(request.currency());
        paymentEntity.setDescription(request.description());
        paymentEntity.setStatus(paymentIntent.getStatus());
        paymentEntity.setStripeEmail(request.stripeEmail());
        paymentEntity.setStripeToken("pm_card_visa");
        paymentEntity.setPaymentIntentId(paymentIntent.getId());
        paymentEntity.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(paymentEntity);

        // Retornar la respuesta
        return new PaymentResponse(
                paymentIntent.getId(),
                paymentIntent.getAmount(),
                request.currency(),
                paymentIntent.getStatus()
        );
    }

}

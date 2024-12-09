package com.geovannycode.app.web.view;

import com.geovannycode.app.domain.PaymentEntity;
import com.geovannycode.app.domain.PaymentRepository;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Comparator;

@PageTitle("Result Form")
@Route("result")
@Menu(order = 1, icon = LineAwesomeIconUrl.CREDIT_CARD)
public class ResultView extends VerticalLayout {

    @Autowired
    public ResultView(PaymentRepository paymentRepository) {
        setSpacing(false);
        setPadding(true);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        NativeLabel resultLabel = new  NativeLabel("Transaction Result:");
        resultLabel.getStyle()
                .set("font-size", "24px")
                .set("font-weight", "bold")
                .set("margin-bottom", "20px");
        add(resultLabel);

        // Obtener la transacción más reciente por fecha
        PaymentEntity lastPayment = paymentRepository.findAll()
                .stream()
                .max(Comparator.comparing(PaymentEntity::getCreatedAt))
                .orElse(null);

        if (lastPayment != null) {
            Div card = new Div();
            card.getStyle()
                    .set("border", "1px solid #e0e0e0")
                    .set("padding", "20px")
                    .set("border-radius", "8px")
                    .set("background-color", "#ffffff")
                    .set("box-shadow", "0 2px 4px rgba(0, 0, 0, 0.1)")
                    .set("width", "80%");

            // Cambiar el color del estado del pago
            NativeLabel statusTextLabel = new NativeLabel("Status:");
            statusTextLabel.getStyle().set("font-weight", "bold");

            NativeLabel statusLabel = new NativeLabel(lastPayment.getStatus());
            if ("succeeded".equalsIgnoreCase(lastPayment.getStatus())) {
                statusLabel.getStyle().set("color", "green"); // Color verde para éxito
            } else {
                statusLabel.getStyle().set("color", "red"); // Color rojo para fallido
            }

            // Crear un contenedor para el texto de Status
            HorizontalLayout statusContainer = new HorizontalLayout();
            statusContainer.setSpacing(true);

            statusContainer.add(statusTextLabel, statusLabel);
            statusContainer.getStyle().set("align-items", "center"); // Centrar alineación

            // Añadir los detalles a la tarjeta
            card.add(
                    createDetail("ID", String.valueOf(lastPayment.getId())),
                    createDetail("Amount", "$" + lastPayment.getAmount()),
                    createDetail("Currency", lastPayment.getCurrency().toString()),
                    statusContainer, // Usar el estado con color
                    createDetail("Email", lastPayment.getStripeEmail()),
                    createDetail("Description", lastPayment.getDescription()),
                    createDetail("Created At", lastPayment.getCreatedAt().toString())
            );

            add(card);
        } else {
            NativeLabel noPaymentLabel = new NativeLabel("No payment found!");
            noPaymentLabel.getStyle().set("color", "red").set("font-weight", "bold");
            add(noPaymentLabel);
        }
    }

    private Div createDetail(String label, String value) {
        Div detail = new Div();
        detail.getStyle().set("margin-bottom", "10px");

        NativeLabel labelComponent = new NativeLabel(label + ": ");
        labelComponent.getStyle()
                .set("font-weight", "bold")
                .set("margin-right", "10px");

        NativeLabel valueComponent = new NativeLabel(value);

        detail.add(labelComponent, valueComponent);
        return detail;
    }
}

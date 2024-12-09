package com.geovannycode.app.web.view;

import com.geovannycode.app.domain.PaymentService;
import com.geovannycode.app.domain.model.Currency;
import com.geovannycode.app.domain.model.PaymentRequest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.math.BigDecimal;


@PageTitle("Checkout Form")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.CREDIT_CARD)
public class CheckoutView extends VerticalLayout {

    private final PaymentService paymentService;
    private String CARD_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35d{3})d{11})$";

    public CheckoutView(PaymentService paymentService) {
        this.paymentService = paymentService;

        TextField descriptionField = new TextField("Description");
        descriptionField.setPlaceholder("Enter a description");

        TextField amountField = new TextField("Amount");
        amountField.setPlaceholder("Enter amount in USD (e.g., 100)");

        ComboBox<Currency> currencyComboBox = new ComboBox<>("Currency");
        currencyComboBox.setItems(Currency.values());
        currencyComboBox.setValue(Currency.USD);

        TextField emailField = new TextField("Email");
        emailField.setPlaceholder("Enter your email");

        TextField cardNumberField = new TextField("Card Number");
        cardNumberField.setPlaceholder("Enter card number (e.g., 4242424242424242)");
        cardNumberField.setPattern(CARD_REGEX);
        cardNumberField.setAllowedCharPattern("[\\d ]");
        cardNumberField.setRequired(true);
        cardNumberField.setErrorMessage("Please enter a valid credit card number");

        Select month = new Select<>();
        month.setPlaceholder("Month");
        month.setItems(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        Select year = new Select<>();
        year.setPlaceholder("Year");
        year.setItems(20, 21, 22, 23, 24, 25);

        ExpirationDateField expiration = new ExpirationDateField("Expiration date", month, year);
        PasswordField cvcField = new PasswordField("CSC");
        cvcField.setPlaceholder("Enter CVC (e.g., 123)");

        // Botón de pago
        Button payButton = new Button("Pay", new Icon(VaadinIcon.LOCK), event -> {
            try {
                // Validar los datos ingresados
                if (amountField.isEmpty() || !amountField.getValue().matches("\\d+(\\.\\d+)?")) {
                    Notification.show("Please enter a valid amount.", 5000, Notification.Position.MIDDLE);
                    return;
                }

                // Crear el objeto PaymentRequest
                PaymentRequest request = new PaymentRequest(
                        descriptionField.getValue(),
                        new BigDecimal(amountField.getValue()),
                        currencyComboBox.getValue(),
                        emailField.getValue(),
                        cardNumberField.getValue() + "|" + expiration.getValue() + "|" + cvcField.getValue()
                );

                // Procesar el pago
                paymentService.processPayment(request);

                Notification.show("Payment successful!", 5000, Notification.Position.MIDDLE);
            } catch (Exception e) {
                Notification.show("Payment failed: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        // Usar FormLayout para organizar los campos
        FormLayout formLayout = new FormLayout();
        formLayout.add(descriptionField, amountField, currencyComboBox, emailField, cardNumberField, expiration, cvcField, payButton);

        add(formLayout);
    }

    private class ExpirationDateField extends CustomField<String> {
        public ExpirationDateField(String label, Select<Integer> month, Select<Integer> year) {
            setLabel(label);
            HorizontalLayout layout = new HorizontalLayout(month, year);
            layout.setFlexGrow(1.0, month, year);
            month.setWidth("100px");
            year.setWidth("100px");
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            // Unused as month and year fields part are of the outer class
            return "";
        }

        @Override
        protected void setPresentationValue(String newPresentationValue) {
            // Unused as month and year fields part are of the outer class
        }

    }
}

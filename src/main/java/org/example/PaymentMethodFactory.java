package org.example;

import org.example.paymentMethods.BoletoPayment;
import org.example.paymentMethods.CreditCardPayment;
import org.example.paymentMethods.PaymentMethod;
import org.example.paymentMethods.PaymentType;
import org.example.paymentMethods.PixPayment;

public class PaymentMethodFactory {
    public static PaymentMethod create(PaymentType type) {
        return switch (type) {
            case PIX -> new PixPayment();
            case CARTAO -> new CreditCardPayment();
            case BOLETO -> new BoletoPayment();
        };
    }
}
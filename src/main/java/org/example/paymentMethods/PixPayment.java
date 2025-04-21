package org.example.paymentMethods;

import java.util.UUID;

public class PixPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Pedido no valor de R$" + amount + " pago com PIX"
        + "\nChave de autenticação: PIX-" + gerarChave());
    }

    @Override
    public String gerarChave() {

        UUID uuid = UUID.randomUUID();

        String uuidStr = uuid.toString().replaceAll("-", "");
        return uuidStr.substring(0, 8) + "-" +
                uuidStr.substring(8, 12) + "-" +
                uuidStr.substring(12, 16) + "-" +
                uuidStr.substring(16, 24);
    }
}
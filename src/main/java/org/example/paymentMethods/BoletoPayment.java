package org.example.paymentMethods;

import java.util.UUID;

public class BoletoPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Pedido no valor de R$" + amount + " pago com boleto\n" +
                "Chave de autenticação: BOLETO-" + gerarChave());
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
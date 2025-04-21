package org.example.paymentMethods;

public interface PaymentMethod {
    void pay(double amount);
    String gerarChave();
}
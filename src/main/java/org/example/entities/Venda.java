package org.example.entities;

import org.example.paymentMethods.PaymentType;

import java.util.List;
import java.util.UUID;

public class Venda extends Entity {

    private String cliente;
    private List<String> produtos;
    private double total;
    private PaymentType paymentType;


    public Venda(String cliente, List<String> produtos, double total, PaymentType paymentType) {
        super();
        this.cliente = cliente;
        this.produtos = produtos;
        this.total = total;
        this.paymentType = paymentType;
    }

    public Venda(UUID uuid, String cliente, List<String> produtos, double total, PaymentType paymentType) {
        super(uuid);
        this.cliente = cliente;
        this.produtos = produtos;
        this.total = total;
        this.paymentType = paymentType;
    }


    public String getCliente() {
        return cliente;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public double getTotal() {
        return total;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void resumoVenda(){
        System.out.println("Cliente:   " + cliente);
        System.out.println("Produtos:");

        for (String produto : produtos) {
            System.out.println("  - " + produto);
        }

        System.out.println("\n             Total: R$" + total);
        System.out.println("Forma de pagamento: " + paymentType);
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente + "   |   Produtos: " + produtos + "   |   Total: " + total + "   |   Tipo de pagamento: " + paymentType + "   |   ID: " + getUuid();
    }

}

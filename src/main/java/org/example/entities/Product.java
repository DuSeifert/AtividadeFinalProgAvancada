package org.example.entities;

import java.util.UUID;

public class Product extends Entity {

    private final String name;
    private final double price;
    private final int id;

    public Product(UUID uuid, String name, double price, int id) {
        super(uuid);
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public Product(String name, double price, int id) {
        super();
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "\tProduto: " + name + "   |   Preço: R$" + price + "   |   ID: " + id + "   |   UUID: " + getUuid();
    }



}

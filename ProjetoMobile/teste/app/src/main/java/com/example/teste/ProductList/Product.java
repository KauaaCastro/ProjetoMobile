package com.example.teste.ProductList;

public class Product {
    private String name;
    private String size;
    private int quantity;

    public Product() {
    }

    public Product(String name, String size, int quantity) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increment() {
        this.quantity++;
    }

    public void decrement() {
        if(this.quantity > 0) {
            this.quantity--;
        }
    }
}

package com.example.teste.ProductList;

public class Product {
    private String name;
    private String size;
    private int quantity;

    public Product(String name, String size, int quantity) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void Increment() {
        this.quantity++;
    }

    public void Decrement() {
        if(this.quantity > 0) {
            this.quantity--;
        }
    }
}

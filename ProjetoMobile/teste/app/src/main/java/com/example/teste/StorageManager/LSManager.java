package com.example.teste.StorageManager;

import com.example.teste.ProductList.Product;

import java.util.ArrayList;
import java.util.List;

public class LSManager {
    private static List<Product> savedItems = new ArrayList<>();

    public static void addItems(List<Product> items) {
        LSManager.clear();
        savedItems.addAll(items);

    }

    public static List<Product> getSavedItems() {
        return savedItems;
    }

    public static void clear() {
        savedItems.clear();
    }
}

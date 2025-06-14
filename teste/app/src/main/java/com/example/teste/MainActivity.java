package com.example.teste;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.teste.ProductList.Product;
import com.example.teste.ProductList.ProductAdapter;
import com.example.teste.StorageManager.LSManager;
import com.example.teste.StorageManager.NoteScreen;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> allProducts;

    private List<Product> filteredProducts;
    private Spinner spinner;
    private boolean confirmation = false;

    // Carrega a tela
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.ProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spinner = findViewById(R.id.SelectSize);
        Button saveButton = findViewById(R.id.SaveList);
        Button finishButton = findViewById(R.id.FinallyList);

        // Criar uma lista inicial de produtos
        allProducts = new ArrayList<>();
        allProducts.add(new Product("Nossa Senhora Aparecida", "7cm", 0));
        allProducts.add(new Product("São Jorge", "7cm", 0));
        allProducts.add(new Product("São José", "12cm", 0));
        allProducts.add(new Product("Santa Rita", "12cm", 0));
        allProducts.add(new Product("Santo Antônio", "15cm", 0));
        allProducts.add(new Product("Nossa Senhora Aparecida", "20cm", 0));
        allProducts.add(new Product("Santa Rita", "30cm", 0));

        // Cria a lista filtrada
        filteredProducts = new ArrayList<>();

        // Abrindo a aplicação com a lista já filtrada
        adapter = new ProductAdapter(filteredProducts);
        recyclerView.setAdapter(adapter);

        // Colocando os tamanhos dentro da box
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new String[]{
                        "7cm", "12cm", "15cm", "20cm", "30cm"
                }
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSize = (String) parent.getItemAtPosition(position);
                FilteredListBySize(selectedSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Funcionamento dos botões
        saveButton.setOnClickListener(v -> {
            List<Product> itemsToSave = new ArrayList<>();

            for (Product p : allProducts) {
                if (p.getQuantity() > 0) {
                    itemsToSave.add(new Product(p.getName(), p.getSize(), p.getQuantity()));
                }
            }

            LSManager.addItems(itemsToSave);
            confirmation = true;

            if (!itemsToSave.isEmpty()) {
                LSManager.addItems(itemsToSave);
                confirmation = true;
                Toast.makeText(this, "Itens salvos com sucesso!", Toast.LENGTH_SHORT).show();

            } else {
                    Toast.makeText(this, "Nenhum item com quantidade selecionada.", Toast.LENGTH_SHORT).show();
                    confirmation = true;

            }
        });

        finishButton.setOnClickListener(v -> {
            if (!LSManager.getSavedItems().isEmpty()) {
                Intent intent = new Intent(MainActivity.this, NoteScreen.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Salve a lista antes de exibir a nota!", Toast.LENGTH_SHORT).show();
                confirmation = false;

            }
        });
    }

    private void FilteredListBySize(String size) {
        filteredProducts.clear();

        for (Product p : allProducts) {
            if (p.getSize().equals(size)) {
                filteredProducts.add(p);
            }
        }
        adapter.notifyDataSetChanged();
    }
}

//2 ns - 1 j - 12-- 3j - 5rt - 15-- 2ant - 20 -- 1 ns - 30 -- 12
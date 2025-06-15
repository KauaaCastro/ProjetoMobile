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

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.ProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        spinner = findViewById(R.id.SelectSize);
        Button saveButton = findViewById(R.id.SaveList);
        Button finishButton = findViewById(R.id.FinallyList);

        // Criar lista inicial de produtos
        allProducts = new ArrayList<>();
        allProducts.add(new Product("Nossa Senhora Aparecida", "7cm", 0));
        allProducts.add(new Product("São Jorge", "7cm", 0));
        allProducts.add(new Product("São José", "12cm", 0));
        allProducts.add(new Product("Santa Rita", "12cm", 0));
        allProducts.add(new Product("Santo Antônio", "15cm", 0));
        allProducts.add(new Product("Nossa Senhora Aparecida", "20cm", 0));
        allProducts.add(new Product("Santa Rita", "30cm", 0));

        filteredProducts = new ArrayList<>();
        adapter = new ProductAdapter(filteredProducts);
        recyclerView.setAdapter(adapter);

        // Spinner de tamanhos
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                new String[]{"7cm", "12cm", "15cm", "20cm", "30cm"});
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSize = (String) parent.getItemAtPosition(position);
                FilteredListBySize(selectedSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Botão salvar lista — salva no LSManager
        saveButton.setOnClickListener(v -> {
            this.count++;

            List<Product> itemsToSave = new ArrayList<>();
            for (Product p : allProducts) {
                if (p.getQuantity() > 0) {
                    itemsToSave.add(new Product(p.getName(), p.getSize(), p.getQuantity()));
                }
            }

            // Atualiza a lista salva no LSManager
            LSManager.addItems(itemsToSave);

            confirmation = true;

            ShowUpdate(itemsToSave);
        });

        // Botão finalizar — atualiza LSManager e abre NoteScreen se houver itens
        finishButton.setOnClickListener(v -> {
            // Atualiza os itens salvos antes de verificar
            List<Product> itemsToSave = new ArrayList<>();
            for (Product p : allProducts) {
                if (p.getQuantity() > 0) {
                    itemsToSave.add(new Product(p.getName(), p.getSize(), p.getQuantity()));
                }
            }
            LSManager.addItems(itemsToSave);

            if (!LSManager.getSavedItems().isEmpty()) {
                Intent intent = new Intent(MainActivity.this, NoteScreen.class);
                startActivity(intent);

            } else {
                confirmation = false;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Atenção!")
                        .setMessage("Não há nenhum item na lista de resina ainda, insira-os e finalize o pedido")
                        .setPositiveButton("Ok", null)
                        .show();
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

    private void ShowUpdate(List<Product> itemsToSave) {
        if (!itemsToSave.isEmpty()) {
            if(count == 0) {
                Toast.makeText(this, "Lista atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                this.count = -1;
            } else {
                this.count = -1;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Lista atualizada!")
                        .setMessage("A lista foi atualizada com sucesso!")
                        .setPositiveButton("Continuar", (dialog, which) -> {
                            Toast.makeText(this, "A próxima confirmação aparecerá aqui", Toast.LENGTH_SHORT).show();
                        }).show();
            }

        } else {
            if (count >= 5) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("OOOOO MUMU")
                        .setMessage("Mumu tem que adicionar produtos a lista, para ela abrir, né Mumu")
                        .setPositiveButton("KKKK Mumu é complicado", null)
                        .show();

            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Lista não atualizada!")
                        .setMessage("Não possui itens a serem atualizados, adicione-os e salve novamente")
                        .setPositiveButton("Ok", null)
                        .show();
            }
        }
    }
}

//2 ns - 1 j - 12-- 3j - 5rt - 15-- 2ant - 20 -- 1 ns - 30 -- 12
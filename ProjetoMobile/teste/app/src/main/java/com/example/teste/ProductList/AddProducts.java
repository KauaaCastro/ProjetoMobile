package com.example.teste.ProductList;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.teste.R;
import com.example.teste.StorageManager.LSManager;

public class AddProducts extends AppCompatActivity {

    private EditText inputName, inputSize;
    private Button addButton, ExButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_products);

        inputName = findViewById(R.id.inputName);
        inputSize = findViewById(R.id.inputSize);
        addButton = findViewById(R.id.btnAddProduct);
        ExButton = findViewById(R.id.btnRemoveButton);

        addButton.setOnClickListener(v -> {
            String name = inputName.getText().toString().trim();
            String size = inputSize.getText().toString().trim();

            if (!name.isEmpty() && !size.isEmpty()) {
                Product newProduct = new Product(name, size, 0);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String docId = name + "_" + size;

                db.collection("products")
                        .document(docId)
                        .set(newProduct)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Produto adicionado ao banco!", Toast.LENGTH_SHORT).show();
                            inputName.setText("");
                            inputSize.setText("");
                        })
                        .addOnFailureListener(e -> {
                            new AlertDialog.Builder(AddProducts.this)
                                    .setTitle("Erro ao salvar")
                                    .setMessage("Falha ao adicionar produto: " + e.getMessage())
                                    .setPositiveButton("Ok", null)
                                    .show();
                        });

            } else {
                new AlertDialog.Builder(AddProducts.this)
                        .setTitle("Erro!")
                        .setMessage("Preencha todos os campos!")
                        .setPositiveButton("Ok", null)
                        .show();
            }
        });
    }
}


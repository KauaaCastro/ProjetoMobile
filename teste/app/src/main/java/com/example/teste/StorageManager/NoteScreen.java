package com.example.teste.StorageManager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.teste.ProductList.Product;
import com.example.teste.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NoteScreen extends AppCompatActivity {

    private TextView noteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_view);

        noteTextView = findViewById(R.id.textNota);

        Map<String, List<Product>> grouped = new TreeMap<>();

        for (Product p : LSManager.getSavedItems()) {
            if (!grouped.containsKey(p.getSize())) {
                grouped.put(p.getSize(), new ArrayList<>());
            }
            grouped.get(p.getSize()).add(p);
        }

        StringBuilder note = new StringBuilder();

        for (String size : grouped.keySet()) {
            note.append(size).append(":\n");
            for (Product p : grouped.get(size)) {
                note.append("- ").append(p.getName())
                        .append(" (").append(p.getQuantity()).append(")\n");
            }
            note.append("\n");
        }

        noteTextView.setText(note.toString());
    }
}
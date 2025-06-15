package com.example.teste.ProductList;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.teste.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> productList;
    private FirebaseFirestore db;


    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
        this.db = FirebaseFirestore.getInstance();
    }

    // Cria um item da lista com base na tela de layout dos produtos
    @NonNull
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_list, parent, false);
        return new ProductViewHolder(itemView);
    }

    // preenche os dados e configura os botões de + e -
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txName.setText(product.getName());
        holder.txtQuantity.setText(String.valueOf(product.getQuantity()));

        holder.btIncrement.setOnClickListener(v -> {
            product.increment();
            holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
            updateQuantityInFirestore(product);
        });

        // Botão decrementar
        holder.btDecrement.setOnClickListener(v -> {
            product.decrement();
            holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
            updateQuantityInFirestore(product);
        });
    }

    public int getItemCount() {
        return productList.size();
    }

    // Localiza e adquire os botões disponiveis na tela de xml
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txName, txtQuantity;
        Button btIncrement, btDecrement;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.txtName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btIncrement = itemView.findViewById(R.id.btnIncrement);
            btDecrement = itemView.findViewById(R.id.btnDecrement);
        }
    }

    private void updateQuantityInFirestore(Product product) {
        db.collection("products")
                .document(product.getId())
                .update("quantity", product.getQuantity());

    }
}

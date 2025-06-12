package com.example.teste.ProductList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.teste.R;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
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

        //Botão de mais
        holder.btIncrement.setOnClickListener(v -> {
            product.Increment();
            holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
        });

        //Botão de menos
        holder.btDecrement.setOnClickListener(v -> {
            product.Decrement();
            holder.txtQuantity.setText(String.valueOf(product.getQuantity()));
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
}

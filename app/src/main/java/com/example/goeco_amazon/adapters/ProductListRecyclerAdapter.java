package com.example.goeco_amazon.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.interfaces.PickuppointOnClick;
import com.example.goeco_amazon.interfaces.ProductBuyOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.models.ProductModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductListRecyclerAdapter extends RecyclerView.Adapter<ProductListRecyclerAdapter.MyViewHolder>{
    Context context;
    ArrayList<ProductModel> responses;
    ProductBuyOnClick productBuyOnClick;

    public ProductListRecyclerAdapter(Context context,ArrayList<ProductModel> responses,ProductBuyOnClick productBuyOnClick) {
        this.context = context;
        this.responses = responses;
        this.productBuyOnClick = productBuyOnClick;
    }
    @NonNull
    @Override
    public ProductListRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new ProductListRecyclerAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductListRecyclerAdapter.MyViewHolder holder, int position) {


        ProductModel pointModel = responses.get(position);
        holder.name.setText(responses.get(position).getName());
        holder.price.setText("Rs."+ responses.get(position).getPrice());
        Picasso.get().load(responses.get(position).getImage()).into(holder.imageView);
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.quantity.getText().toString());
                productBuyOnClick.onclick(pointModel,quantity);
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(holder.quantity.getText().toString());
                num = num + 1;
                holder.quantity.setText(num+"");
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = Integer.parseInt(holder.quantity.getText().toString());
                if(num > 0){
                    num = num - 1;
                    holder.quantity.setText(num+"");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return responses.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,quantity;
        Button buy;
        MaterialButton add,minus;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.product_quantity);
            buy = itemView.findViewById(R.id.button_buy);
            add = itemView.findViewById(R.id.button_increase);
            minus = itemView.findViewById(R.id.button_decrease);
            imageView = itemView.findViewById(R.id.product_image);



        }
    }
}

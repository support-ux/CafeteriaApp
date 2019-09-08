package com.example.cafeteriaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapp.Entidades.Producto;
import com.example.cafeteriaapp.R;

import java.util.List;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.MyViewHolder>{

    private Context mCtx;
    private List<Producto> productoList;

    public AdapterProductos(List<Producto> productoList, Context context) {
        this.productoList = productoList;
        this.mCtx = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardproducto, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.producto.setText(productoList.get(position).getProducto());
        holder.descripcion.setText(productoList.get(position).getDescripcion());
        holder.precio.setText("$"+ productoList.get(position).getPrecio());


    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView producto,descripcion,precio;

        public MyViewHolder(View itemView) {
            super(itemView);
            producto = itemView.findViewById(R.id.txtNomProducto);
            descripcion = itemView.findViewById(R.id.txtDescripcion);
            precio = itemView.findViewById(R.id.txtPrecio);


        }

    }
}

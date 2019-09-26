package com.example.cafeteriaapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapp.Entidades.Producto;
import com.example.cafeteriaapp.R;

import java.util.List;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.MyViewHolder>
implements View.OnClickListener{

    private Context mCtx;
    private List<Producto> productoList;
    private View.OnClickListener listener;

    public AdapterProductos(List<Producto> productoList, Context context) {
        this.productoList = productoList;
        this.mCtx = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardproducto, parent, false);

        view.setOnClickListener(this);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.producto.setText(productoList.get(position).getProducto());
        holder.descripcion.setText(productoList.get(position).getDescripcion());
        holder.precio.setText("$"+ productoList.get(position).getPrecio());
        String img;
        img = productoList.get(position).getFoto();
        byte [] encodeByte= Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        holder.imgProducto.setImageBitmap(bitmap);

        // Change Her!!


    }
    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {

        if(listener!=null){
            listener.onClick(view);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView producto,descripcion,precio;
        ImageView imgProducto;


        public MyViewHolder(View itemView) {
            super(itemView);
            producto = itemView.findViewById(R.id.txtNomProducto);
            descripcion = itemView.findViewById(R.id.txtDescripcion);
            precio = itemView.findViewById(R.id.txtPrecio);
            imgProducto = itemView.findViewById(R.id.imgProducto);



        }

    }
}

package com.example.cafeteriaapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapp.Entidades.ProductoVo;
import com.example.cafeteriaapp.R;

import java.util.List;

public class AdapterListPedidos extends RecyclerView.Adapter<AdapterListPedidos.ViewHolderListPedidos>
implements View.OnClickListener{
    @NonNull

    public List<ProductoVo> listaProductos;
    private View.OnClickListener listener;

    public AdapterListPedidos (List<ProductoVo>listaProductos){
        this.listaProductos = listaProductos;
    }
    @Override
    public ViewHolderListPedidos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlistpedido,null,false);
        view.setOnClickListener(this);
        return new ViewHolderListPedidos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListPedidos holder, int position) {
        holder.txtProductoLista.setText(listaProductos.get(position).getNomProducto());
        holder.txtPreProductoLista.setText(listaProductos.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
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

    public class ViewHolderListPedidos extends RecyclerView.ViewHolder {

        TextView txtProductoLista,txtPreProductoLista;
        public ViewHolderListPedidos(@NonNull View itemView) {
            super(itemView);
            txtProductoLista = itemView.findViewById(R.id.txtProductoList);
            txtPreProductoLista = itemView.findViewById(R.id.txtPrecioList);
        }
    }
}

package com.example.cafeteriaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapp.Entidades.VentaDetalle;
import com.example.cafeteriaapp.R;

import java.util.List;

public class AdapterListDetallePedidoUsuario extends RecyclerView.Adapter<AdapterListDetallePedidoUsuario.ViewHolderListDetallePedidoUsuario> {
    @NonNull

    public List<VentaDetalle> listaDetallePedidoUsuario;
    private Context context;

    public AdapterListDetallePedidoUsuario (List<VentaDetalle>listaDetallePedidoUsuario, Context context){
        this.listaDetallePedidoUsuario = listaDetallePedidoUsuario;
        this.context = context;
    }
    @Override
    public AdapterListDetallePedidoUsuario.ViewHolderListDetallePedidoUsuario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carddetallventausuario,parent, false);
        return new ViewHolderListDetallePedidoUsuario(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListDetallePedidoUsuario.ViewHolderListDetallePedidoUsuario holder, int position) {
        holder.txtNombreProductoDetalle.setText(""+listaDetallePedidoUsuario.get(position).getProducto());
        holder.txtCantidadProductoDetalle.setText(listaDetallePedidoUsuario.get(position).getCantidad());
        holder.txtPrecioProductoDetalle.setText("$"+ listaDetallePedidoUsuario.get(position).getPrecio());
        holder.txtTotalProductoDetalle.setText("$"+ listaDetallePedidoUsuario.get(position).getTotal());
    }

    @Override
    public int getItemCount() {

        return listaDetallePedidoUsuario.size();
    }

    public class ViewHolderListDetallePedidoUsuario extends RecyclerView.ViewHolder {

        TextView txtNombreProductoDetalle,txtCantidadProductoDetalle,txtPrecioProductoDetalle,txtTotalProductoDetalle;
        public ViewHolderListDetallePedidoUsuario(@NonNull View itemView) {
            super(itemView);
            txtNombreProductoDetalle = itemView.findViewById(R.id.txtNombreProductoDetalle);
            txtCantidadProductoDetalle = itemView.findViewById(R.id.txtCantidadProductoDetalle);
            txtPrecioProductoDetalle = itemView.findViewById(R.id.txtPrecioProductoDetalle);
            txtTotalProductoDetalle = itemView.findViewById(R.id.txtTotalProductoDetalle);
        }
    }
}

package com.example.cafeteriaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapp.Entidades.Venta;
import com.example.cafeteriaapp.R;

import java.util.List;

public class AdapterVentaAdmin extends RecyclerView.Adapter<AdapterVentaAdmin.MyViewHolder>
        implements View.OnClickListener{

    private Context mCtx;
    private List<Venta> ventaList;
    private View.OnClickListener listener;

    public AdapterVentaAdmin(List<Venta> ventaList, Context context) {
        this.ventaList = ventaList;
        this.mCtx = context;
    }


    @NonNull
    @Override
    public AdapterVentaAdmin.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carddetallepedidoadmin, parent, false);

        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVentaAdmin.MyViewHolder holder, int position) {
        holder.txtFecha.setText(""+ventaList.get(position).getFecha());
        holder.txtNombre.setText(ventaList.get(position).getNombres());
        holder.txtApellido.setText(ventaList.get(position).getApellidos());
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return ventaList.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtFecha,txtNombre,txtApellido;



        public MyViewHolder(View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtNombre = itemView.findViewById(R.id.txtNombreUsuario);
            txtApellido = itemView.findViewById(R.id.txtApellidoUsuario);
        }
    }
}

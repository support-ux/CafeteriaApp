package com.example.cafeteriaapp.Activitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapp.Adapter.AdapterProductos;
import com.example.cafeteriaapp.ApiClient.ApiGetDataProductos;
import com.example.cafeteriaapp.ApiInterface.ApiInterfaceProductos;
import com.example.cafeteriaapp.Entidades.Producto;
import com.example.cafeteriaapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Producto> listProducto;
    private AdapterProductos adapterProductos;
    private ApiInterfaceProductos apiInterfaceProductos;

    int id,idUsuario,idVenta;
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        recyclerView = findViewById(R.id.listProductos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                status = extras.getString("status");
                id = extras.getInt("idC");
                idVenta = extras.getInt("idVenta");
                idUsuario= extras.getInt("ID");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("Id");
        }
        fetchProductos("producto", id);


    }

    private void fetchProductos(String type, int id) {

        apiInterfaceProductos = ApiGetDataProductos.getApiListaProductos().create(ApiInterfaceProductos.class);

        Call<List<Producto>> call = apiInterfaceProductos.getProductos(type,id);
        call.enqueue(new Callback<List<Producto>>(){

            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                listProducto = response.body();
                adapterProductos = new AdapterProductos(listProducto, ProductosActivity.this);

                adapterProductos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProductosActivity.this,AddActivity.class);
                        String nombre,precio,descripcion,stock,img;
                        int Id;
                        Id=listProducto.get(recyclerView.getChildAdapterPosition(view)).getId();
                        nombre=listProducto.get(recyclerView.getChildAdapterPosition(view)).getProducto();
                        precio=listProducto.get(recyclerView.getChildAdapterPosition(view)).getPrecio();
                        descripcion=listProducto.get(recyclerView.getChildAdapterPosition(view)).getDescripcion();
                        stock= listProducto.get(recyclerView.getChildAdapterPosition(view)).getStock();
                        img= listProducto.get(recyclerView.getChildAdapterPosition(view)).getFoto();

                        intent.putExtra("status",status);
                        intent.putExtra("idVenta",idVenta);
                        intent.putExtra("ID",idUsuario);
                        intent.putExtra("id",Id);
                        intent.putExtra("nombre",nombre);
                        intent.putExtra("precio",precio);
                        intent.putExtra("descripcion",descripcion);
                        intent.putExtra("stock",stock);
                        intent.putExtra("img",img);

                        startActivity(intent);

                    }
                });
                recyclerView.setAdapter(adapterProductos);
                adapterProductos.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(ProductosActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

}





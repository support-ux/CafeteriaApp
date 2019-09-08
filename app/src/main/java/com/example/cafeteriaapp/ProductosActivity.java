package com.example.cafeteriaapp;


import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeteriaapp.Adapter.AdapterProductos;
import com.example.cafeteriaapp.ApiClient.ApiGetDataProductos;
import com.example.cafeteriaapp.ApiInterface.ApiInterfaceProductos;
import com.example.cafeteriaapp.Entidades.Producto;

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

    int id;


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
                id = extras.getInt("idC");
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





package com.example.cafeteriaapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cafeteriaapp.Adapter.AdapterListDetallePedidoUsuario;
import com.example.cafeteriaapp.ApiClient.ApiGetDataDetallePedidoUsuario;
import com.example.cafeteriaapp.ApiInterface.ApiInterfaceDetallePedidoUsuario;
import com.example.cafeteriaapp.Entidades.VentaDetalle;
import com.example.cafeteriaapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleVentaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<VentaDetalle> ventaDetalleList;
    private AdapterListDetallePedidoUsuario adapterListDetallePedidoUsuario;
    private ApiInterfaceDetallePedidoUsuario apiInterfaceDetallePedidoUsuario;

    int idAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                idAlumno = extras.getInt("ID");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("Id");
        }



        recyclerView = findViewById(R.id.listPedidosUsuario);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetchDetallePedidoUsuario(idAlumno);
    }

    private void fetchDetallePedidoUsuario(int idAlumno) {

        apiInterfaceDetallePedidoUsuario = ApiGetDataDetallePedidoUsuario.getApiListaDetallePedidoUsuario().create(ApiInterfaceDetallePedidoUsuario.class);

        Call<List<VentaDetalle>> call = apiInterfaceDetallePedidoUsuario.getDetallePedidoUsuario(idAlumno);
        call.enqueue(new Callback<List<VentaDetalle>>(){

            @Override
            public void onResponse(Call<List<VentaDetalle>> call, Response<List<VentaDetalle>> response) {
                ventaDetalleList = response.body();
                adapterListDetallePedidoUsuario = new AdapterListDetallePedidoUsuario(ventaDetalleList,DetalleVentaActivity.this);

                recyclerView.setAdapter(adapterListDetallePedidoUsuario);
                adapterListDetallePedidoUsuario.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VentaDetalle>> call, Throwable t) {
                Toast.makeText(DetalleVentaActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

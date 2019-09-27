package com.example.cafeteriaapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cafeteriaapp.Adapter.AdapterVentaAdmin;
import com.example.cafeteriaapp.ApiClient.ApiGetDataVentaAdmin;
import com.example.cafeteriaapp.ApiInterface.ApiInterfaceVentaAdmin;
import com.example.cafeteriaapp.Entidades.Venta;
import com.example.cafeteriaapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanelAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Venta> ventaList;
    private AdapterVentaAdmin adapterVentaAdmin;
    private ApiInterfaceVentaAdmin apiInterfaceVentaAdmin;

    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admin);

        recyclerView = findViewById(R.id.listPedidosAdmin);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetchVentas(0);
    }

    private void fetchVentas(int id) {

        apiInterfaceVentaAdmin = ApiGetDataVentaAdmin.getApiListaDetalleVentaAdmin().create(ApiInterfaceVentaAdmin.class);

        Call<List<Venta>> call = apiInterfaceVentaAdmin.getDetallePedidoAdmin(id);
        call.enqueue(new Callback<List<Venta>>() {

            @Override
            public void onResponse(Call<List<Venta>> call, Response<List<Venta>> response) {
                ventaList = response.body();
                adapterVentaAdmin = new AdapterVentaAdmin(ventaList, PanelAdminActivity.this);

                adapterVentaAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PanelAdminActivity.this, GestionPedidosAdminActivity.class);
                        String idAlumno, nombres, apellidos, fecha,estado;
                        int Id;
                        Id = ventaList.get(recyclerView.getChildAdapterPosition(view)).getId();
                        idAlumno = ventaList.get(recyclerView.getChildAdapterPosition(view)).getIdAl();
                        nombres = ventaList.get(recyclerView.getChildAdapterPosition(view)).getNombres();
                        apellidos = ventaList.get(recyclerView.getChildAdapterPosition(view)).getApellidos();
                        fecha = ventaList.get(recyclerView.getChildAdapterPosition(view)).getFecha();
                        estado = ventaList.get(recyclerView.getChildAdapterPosition(view)).getEstado();

                        intent.putExtra("idVenta",Id);
                        intent.putExtra("idAlumno",idAlumno);
                        intent.putExtra("nombres",nombres);
                        intent.putExtra("apellidos",apellidos);
                        intent.putExtra("fecha",fecha);
                        intent.putExtra("estado",estado);

                        Toast.makeText(PanelAdminActivity.this, "Id" +Id+" idAlumno"+ idAlumno+" nombres"+nombres
                                + " apellidos" +apellidos+" fecha"+fecha + " estado"+estado, Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(adapterVentaAdmin);
                        adapterVentaAdmin.notifyDataSetChanged();
                        startActivity(intent);


                    }
                });
                recyclerView.setAdapter(adapterVentaAdmin);

                adapterVentaAdmin.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Venta>> call, Throwable t) {
                Toast.makeText(PanelAdminActivity.this, "Error\n" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }
}

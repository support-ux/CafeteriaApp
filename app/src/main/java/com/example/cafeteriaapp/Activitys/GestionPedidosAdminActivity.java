package com.example.cafeteriaapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafeteriaapp.Adapter.AdapterListDetallePedidoUsuario;
import com.example.cafeteriaapp.ApiClient.ApiGetDataDetallePedidoUsuario;
import com.example.cafeteriaapp.ApiInterface.ApiInterfaceDetallePedidoUsuario;
import com.example.cafeteriaapp.Entidades.VentaDetalle;
import com.example.cafeteriaapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class GestionPedidosAdminActivity extends AppCompatActivity {
    String idAlumno, nombres, apellidos, fecha,estado;
    int Id,idAlumno2;

    Button btnAceptarPedido,btnFinalizarPedido;
    TextView txtFecha,txtNombre,txtApellido;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<VentaDetalle> ventaDetalleList;
    private AdapterListDetallePedidoUsuario adapterListDetallePedidoUsuario;
    private ApiInterfaceDetallePedidoUsuario apiInterfaceDetallePedidoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedidos_admin);

        btnAceptarPedido=findViewById(R.id.btnAceptarPedido);
        btnFinalizarPedido=findViewById(R.id.btnFinalizarPedido);

        txtFecha=findViewById(R.id.txtFechaPedido);
        txtNombre=findViewById(R.id.txNomDetalleAlumno);
        txtApellido=findViewById(R.id.txtApeDetalleAlumno);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                Id = extras.getInt("idVenta");
                idAlumno = extras.getString("idAlumno");
                nombres = extras.getString("nombres");
                apellidos = extras.getString("apellidos");
                fecha = extras.getString("fecha");
                estado = extras.getString("estado");


            }
        } else {
            newString= (String) savedInstanceState.getSerializable("Id");
        }

        if(estado.equals("1")){

        }else{
            if(estado.equals("2")){
                btnAceptarPedido.setVisibility(GONE);
                btnFinalizarPedido.setEnabled(true);
            }
        }

        txtFecha.setText(fecha);
        txtNombre.setText(nombres);
        txtApellido.setText(apellidos);
        recyclerView = findViewById(R.id.recyclerDetalleListProductosAdmin);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        idAlumno2 = Integer.parseInt(idAlumno);

        fetchDetallePedidoUsuario(idAlumno2);

        btnAceptarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus1("https://support-ux.000webhostapp.com/wscafeteria/Datos/updateStatusPedidoAdmin.php?idVenta="+Id);
            }
        });

        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus2("https://support-ux.000webhostapp.com/wscafeteria/Datos/updateStatusPedidoAdmin2.php?idVenta="+Id);
            }
        });
    }

    private void fetchDetallePedidoUsuario(int idAlumno) {
        apiInterfaceDetallePedidoUsuario = ApiGetDataDetallePedidoUsuario.getApiListaDetallePedidoUsuario().create(ApiInterfaceDetallePedidoUsuario.class);

        Call<List<VentaDetalle>> call = apiInterfaceDetallePedidoUsuario.getDetallePedidoUsuario(idAlumno);
        call.enqueue(new Callback<List<VentaDetalle>>(){

            @Override
            public void onResponse(Call<List<VentaDetalle>> call, Response<List<VentaDetalle>> response) {
                ventaDetalleList = response.body();
                adapterListDetallePedidoUsuario = new AdapterListDetallePedidoUsuario(ventaDetalleList,GestionPedidosAdminActivity.this);

                recyclerView.setAdapter(adapterListDetallePedidoUsuario);
                adapterListDetallePedidoUsuario.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VentaDetalle>> call, Throwable t) {
                Toast.makeText(GestionPedidosAdminActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateStatus1(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(mensaje.equals("ACTUALIZADO CON EXITO..!!")){
                        Intent intent = new Intent(GestionPedidosAdminActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        Toast.makeText(GestionPedidosAdminActivity.this, "PEDIDO ACEPTADO..!!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(GestionPedidosAdminActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GestionPedidosAdminActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idVenta2="0";

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_IDALUMNO", idVenta2);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateStatus2(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(mensaje.equals("ACTUALIZADO CON EXITO..!!")){
                        Intent intent = new Intent(GestionPedidosAdminActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        Toast.makeText(GestionPedidosAdminActivity.this, "PEDIDO FINALIZADO", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(GestionPedidosAdminActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GestionPedidosAdminActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idVenta2="0";

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_IDALUMNO", idVenta2);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

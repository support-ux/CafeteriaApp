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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafeteriaapp.Adapter.AdapterListPedidos;
import com.example.cafeteriaapp.Entidades.ProductoVo;
import com.example.cafeteriaapp.R;
import com.example.cafeteriaapp.Sqlite.ConexionSQLHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetalleActivity extends AppCompatActivity {

    TextView txtTotalPagar,txtTiempoEstimado;
    RecyclerView recyclerView;
    AdapterListPedidos adapterListPedidos;
    private List<ProductoVo> listProductoVo;
    Button btnFinalizarPedido,btnVerPedido;

    ConexionSQLHelper conn;

    String status="1";
    int statuspedido;
    int idVenta;
    int idUsuario;

    public List<ProductoVo> listaProductos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        statuspedido=0;


        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                status = extras.getString("status");
                idVenta = extras.getInt("idVenta");
                idUsuario = extras.getInt("ID");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("Id");
        }


        final ConexionSQLHelper conexionSQLHelper = new ConexionSQLHelper(this);

        txtTotalPagar = findViewById(R.id.txtTotalPagar);
        txtTiempoEstimado = findViewById(R.id.txtTiempoEstimado);

        recyclerView = findViewById(R.id.recyclerDetalleListProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);
        btnVerPedido = findViewById(R.id.btnVerPedido);

        if(idVenta==0){
            btnFinalizarPedido.setEnabled(false);

        }else{
            btnFinalizarPedido.setEnabled(true);
        }

        if(status==null){
            status="0";
        }


        adapterListPedidos = new AdapterListPedidos(conexionSQLHelper.mostrarLista());
        int total = conexionSQLHelper.TotalServings();


        recyclerView.setAdapter(adapterListPedidos);
        txtTotalPagar.setText("$. "+total);
        Toast.makeText(this, "idUsuario "+idUsuario, Toast.LENGTH_SHORT).show();


        adapterListPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destroytable();

                UpdateStatusProductosVentas("https://support-ux.000webhostapp.com/wscafeteria/Datos/updateStatusPedido.php?idVenta="+idVenta);


            }
        });

        btnVerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleActivity.this,DetalleVentaActivity.class);
                intent.putExtra("ID",idUsuario);
                startActivity(intent);
            }
        });


    }

    private void destroytable() {
        ConexionSQLHelper conexionSQLHelper = new ConexionSQLHelper(DetalleActivity.this);
        conexionSQLHelper.deleteAll();
    }

    private void UpdateStatusProductosVentas(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(mensaje.equals("ACTUALIZADO CON EXITO..!!")){
                        Intent intent = new Intent(DetalleActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        Toast.makeText(DetalleActivity.this, "SE REALIZÓ SU PEDIDO CON ÉXITO!!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(DetalleActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetalleActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();
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

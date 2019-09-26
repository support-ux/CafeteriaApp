package com.example.cafeteriaapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafeteriaapp.Entidades.Producto;
import com.example.cafeteriaapp.Entidades.ProductoVo;
import com.example.cafeteriaapp.R;
import com.example.cafeteriaapp.Sqlite.ConexionSQLHelper;
import com.example.cafeteriaapp.Utilidades.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    public String nombre,precio,descripcion,stock,img;

    ImageButton btnMenos,btnMas;
    Button btnAgregarLista;
    EditText txtCantidad;
    TextView txtDescripcion,txtNombreProducto,txtPrecioDetalle;
    ImageView imgProductos;

    int cantfinal=0;
    int cantfinal2=0;

    public int idVenta;
    public int idVenta2;

    int Id,idUsuario;
    public String status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnMenos = findViewById(R.id.btnMenos);
        btnMas = findViewById(R.id.btnMas);
        btnAgregarLista = findViewById(R.id.btnAgregarLista);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtNombreProducto = findViewById(R.id.txtNomProductoDetalle);
        txtPrecioDetalle = findViewById(R.id.txtPrecioDetalle);
        imgProductos = findViewById(R.id.imgProductos);



        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                status = extras.getString("status");
                idVenta = extras.getInt("idVenta");
                idUsuario = extras.getInt("ID");
                Id = extras.getInt("id");
                nombre = extras.getString("nombre");
                precio = extras.getString("precio");
                descripcion = extras.getString("descripcion");
                stock = extras.getString("stock");
                img = extras.getString("img");

            }
        } else {
            newString= (String) savedInstanceState.getSerializable("Id");
        }
        byte [] encodeByte= Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        imgProductos.setImageBitmap(bitmap);
        txtDescripcion.setText(descripcion);
        txtNombreProducto.setText(nombre);
        txtPrecioDetalle.setText("$ "+precio);






        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantfinal = Integer.parseInt(txtCantidad.getText().toString());
                if(cantfinal==0){
                    txtCantidad.setText(""+ 0);
                }else{
                    cantfinal2=cantfinal-1;
                    txtCantidad.setText(""+ cantfinal2);
                    btnAgregarLista.setEnabled(true);
                }
            }
        });

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantfinal = Integer.parseInt(txtCantidad.getText().toString());
                if(cantfinal== Integer.parseInt(stock)){
                    Toast.makeText(AddActivity.this, "La cantidad del pedido supera el Stock del día", Toast.LENGTH_SHORT).show();
                    txtCantidad.setText(""+ stock);
                }else{
                    cantfinal2=cantfinal+1;
                    txtCantidad.setText(""+ cantfinal2);
                    btnAgregarLista.setEnabled(true);
                }
            }
        });

        btnAgregarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idVenta==0 & status.equals("0")){
                    insertCabeceraVenta("https://support-ux.000webhostapp.com/wscafeteria/Datos/insertCabeceraVenta.php?idAl="+idUsuario);
                }else{

                    idVenta2=idVenta;
                    int prefinal;
                    int cant;
                    int subTotal;

                    cant = Integer.parseInt(txtCantidad.getText().toString());
                    prefinal = Integer.parseInt(precio);
                    subTotal = prefinal*cant;
                    insertDetalleVenta("https://support-ux.000webhostapp.com/wscafeteria/Datos/insertDetalleVenta.php?idVenta="+idVenta2+"&idProducto="+Id+"&cantidad="+cant+"&preUnitario="+prefinal+"&subTotal="+subTotal+"&total="+subTotal+"&estado=0");

                }
            }
        });


    }

    private void getiDNuevaCabeceraVenta(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int idNuevo = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(mensaje.equals("")){
                        idNuevo = idNuevo+1;
                        Toast.makeText(AddActivity.this,"Nuevo ID: "+idNuevo,Toast.LENGTH_LONG).show();


                    }else{
                        Toast.makeText(AddActivity.this,"Error al Registrar Cabecera",Toast.LENGTH_LONG).show();
                        //getIdCabecera("https://support-ux.000webhostapp.com/wscafeteria/Datos/getiDCabeceraVenta.php?idAlumno="+idUsuario);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idUsuario2;
                idUsuario2 = Integer.toString(idUsuario);

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_IDALUMNO", idUsuario2);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void insertCabeceraVenta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(mensaje.equals("")){
                        Toast.makeText(AddActivity.this,"Error al Registrar Cabecera",Toast.LENGTH_LONG).show();

                    }else{
                        //Toast.makeText(AddActivity.this,"Cabecera Registrada Extiosamente..!!",Toast.LENGTH_LONG).show();
                        getIdCabecera("https://support-ux.000webhostapp.com/wscafeteria/Datos/getiDCabeceraVenta.php?idAlumno="+idUsuario);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idUsuario2;
                idUsuario2 = Integer.toString(idUsuario);

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_IDALUMNO", idUsuario2);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getIdCabecera(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    idVenta = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(mensaje.equals("")){
                        //Toast.makeText(AddActivity.this,"IdCabecera: "+id,Toast.LENGTH_LONG).show();

                       int prefinal;
                       int cant;
                       int subTotal;

                       cant = Integer.parseInt(txtCantidad.getText().toString());
                       prefinal = Integer.parseInt(precio);
                       subTotal = prefinal*cant;
                        insertDetalleVenta("https://support-ux.000webhostapp.com/wscafeteria/Datos/insertDetalleVenta.php?idVenta="+idVenta+"&idProducto="+Id+"&cantidad="+cant+"&preUnitario="+prefinal+"&subTotal="+subTotal+"&total="+subTotal+"&estado=0");


                    }else{

                        Toast.makeText(AddActivity.this,"Error al Registrar Cabecera",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idUsuario2;
                idUsuario2 = Integer.toString(idUsuario);

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_IDALUMNO", idUsuario2);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void insertDetalleVenta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(mensaje.equals("")){
                        Toast.makeText(AddActivity.this,"Error al Registrar Pedido",Toast.LENGTH_LONG).show();

                    }else{
                        final ConexionSQLHelper conexionSQLHelper = new ConexionSQLHelper(AddActivity.this);
                        int tot=Integer.parseInt(txtCantidad.getText().toString());
                        int pre = Integer.parseInt(precio);
                        String totProducto = Integer.toString(tot*pre);
                        conexionSQLHelper.agregarProductos(""+Id,nombre,totProducto);
                        Toast.makeText(AddActivity.this,"Pedido Agregado al Carrito",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddActivity.this,MenuActivity.class);
                        intent.putExtra("ID",idUsuario);
                        intent.putExtra("idVenta",idVenta);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddActivity.this,"Error al Registrar Pedido..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idUsuario2;
                idUsuario2 = Integer.toString(idUsuario);

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_IDALUMNO", idUsuario2);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

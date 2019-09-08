package com.example.cafeteriaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText txtNombres,txtApellidos,txtMatricula,txtClave;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtMatricula = findViewById(R.id.txtUsuarioRegister);
        txtClave = findViewById(R.id.txtClaveRegister);
        btnRegistrar = findViewById(R.id.btnRegistrar);


        txtNombres.requestFocus();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaform();
            }
        });
    }

    private void validaform() {
        String nombres,apellidos,matricula,clave;

        nombres = txtNombres.getText().toString().trim();
        apellidos = txtApellidos.getText().toString().trim();
        matricula = txtMatricula.getText().toString().trim();
        clave = txtClave.getText().toString().trim();
        if(TextUtils.isEmpty(nombres)) {
            txtNombres.setError("Campo Requerido...!!");
        }else{
            if(TextUtils.isEmpty(apellidos)){
                txtApellidos.setError("Campo Requerido...!!");
            }else{
                if(TextUtils.isEmpty(matricula)|| matricula.length()<10){
                    txtMatricula.setError("Ingrese N° de matrícula válida!!");
                }else{
                    if(TextUtils.isEmpty(clave)|| clave.length()<6){
                        txtClave.setError("Ingrese Contraseña válida!!");
                    }else{
                        buscarResultados("https://support-ux.000webhostapp.com//wscafeteria/Datos/createPersona.php?nom="+nombres+"&ape="+apellidos);
                        btnRegistrar.setEnabled(false);
                    }

                }

            }
        }

    }
    private void buscarResultados(String URL) {
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
                        Toast.makeText(RegisterActivity.this,"El Usuario ya fue registrado anteriormente!!",Toast.LENGTH_LONG).show();
                        btnRegistrar.setEnabled(true);
                    }else{
                        String nombres,apellidos;

                        nombres = txtNombres.getText().toString().trim();
                        apellidos = txtApellidos.getText().toString().trim();
                        obteneridpersona("https://support-ux.000webhostapp.com//wscafeteria/Datos/getiDClient.php?nom="+nombres+"&ape="+apellidos);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Error al Registrar Persona",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String nombres,apellidos;

                nombres = txtNombres.getText().toString().trim();
                apellidos = txtApellidos.getText().toString().trim();

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_NOMBRES",nombres);
                parametros.put("N_APELLIDOS", apellidos);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void obteneridpersona(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(id>0){
                        String matricula;
                        matricula = txtMatricula.getText().toString().trim();
                        insertAlumno("https://support-ux.000webhostapp.com//wscafeteria/Datos/createAlumno.php?idper="+id+"&matri="+matricula);
                    }else{
                        Toast.makeText(RegisterActivity.this,"Error al Registrar Alumno",Toast.LENGTH_LONG).show();
                        btnRegistrar.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Error al obtener datos..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String nombres,apellidos;

                nombres = txtNombres.getText().toString().trim();
                apellidos = txtApellidos.getText().toString().trim();

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("N_NOMBRES",nombres);
                parametros.put("N_APELLIDOS", apellidos);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void insertAlumno(String URL) {
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
                        Toast.makeText(RegisterActivity.this,"Error al Registrar Alumno",Toast.LENGTH_LONG).show();
                        btnRegistrar.setEnabled(true);
                    }else{
                        String codA;
                        codA = txtMatricula.getText().toString().trim();
                        obteneridalumno("https://support-ux.000webhostapp.com//wscafeteria/Datos/getiDAlumno.php?codA="+codA);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Error al Registrar Alumno..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String codAlumno;

                codAlumno = txtMatricula.getText().toString().trim();
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("S_CODIGOALUMNO", codAlumno);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void obteneridalumno(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("datos");

                    jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.optInt("ID");
                    String mensaje = jsonObject.optString("message");

                    if(id>0){
                        String pass;
                        pass = txtClave.getText().toString().trim();
                        insertLogin("https://support-ux.000webhostapp.com//wscafeteria/Datos/createLogin.php?idAl="+id+"&pass="+pass);
                    }else{
                        Toast.makeText(RegisterActivity.this,"Error al Registrar Alumno",Toast.LENGTH_LONG).show();
                        btnRegistrar.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Error al obtener datos..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id;
                id="0";


                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("ID",id);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void insertLogin(String URL) {
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
                        Toast.makeText(RegisterActivity.this,"Error al Registrar Alumno",Toast.LENGTH_LONG).show();
                        btnRegistrar.setEnabled(true);
                    }else{
                        Toast.makeText(RegisterActivity.this,"Alumno Registrado Extiosamente..!!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
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
                Toast.makeText(RegisterActivity.this,"Error al Registrar Alumno..!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String codAlumno;

                codAlumno = txtMatricula.getText().toString().trim();
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("S_CODIGOALUMNO", codAlumno);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

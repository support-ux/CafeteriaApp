package com.example.cafeteriaapp.Activitys;

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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cafeteriaapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
Button btnAcceder;
EditText txtUsuario,txtClave;

    RequestQueue rq;
    JsonRequest jrq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnAcceder = findViewById(R.id.btnAcceder);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave = findViewById(R.id.txtClave);

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validaForm();
                /**/
            }
        });
    }

    private void validaForm() {

        String matricula,password;

        matricula = txtUsuario.getText().toString().trim();
        password = txtClave.getText().toString().trim();

        if(TextUtils.isEmpty(matricula)||matricula.length()<10) {
            txtUsuario.setError("Ingrese Código Válido...!!");
        }else{
            if(TextUtils.isEmpty(password)||password.length()<6){
                txtClave.setError("Ingrese Contraseña Válida...!!");
            }else{
                if(matricula.equals("0000000002")&password.equals("123456789")){
                    Intent intent = new Intent(LoginActivity.this,PanelAdminActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    buscarResultados("https://support-ux.000webhostapp.com//wscafeteria/Datos/getDataClient.php?matri="+matricula+"&pass="+password);
                    btnAcceder.setEnabled(false);
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
                    String nombres = jsonObject.optString("NOMBRES");
                    String apellidos = jsonObject.optString("APELLIDOS");
                    String matricula = jsonObject.optString("MATRICULA");

                    if(nombres.equals("NO REGISTRA NOMBRES")){
                        Toast.makeText(LoginActivity.this,"Usuario NO Registrado!!",Toast.LENGTH_LONG).show();
                        btnAcceder.setEnabled(true);
                    }else{
                        Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                        intent.putExtra("ID",id);
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
                Toast.makeText(LoginActivity.this,"Error de Conexión!!",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String matricula,password;

                matricula = txtUsuario.getText().toString().trim();
                password = txtClave.getText().toString().trim();

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("MATRICULA",matricula);
                parametros.put("PASSWORD", password);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

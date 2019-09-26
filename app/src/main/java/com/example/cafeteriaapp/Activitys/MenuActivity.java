package com.example.cafeteriaapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cafeteriaapp.R;

public class MenuActivity extends AppCompatActivity {
ImageView opc1,opc2,opc3;
    int idUsuario;
    int idUsuario2;
    int idVenta=0;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                status = extras.getString("status");
                idUsuario = extras.getInt("ID");
                idVenta = extras.getInt("idVenta");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("ID");
        }


        opc1 =findViewById(R.id.imgBebidas);
        opc2 = findViewById(R.id.imgAlimentos);
        opc3 = findViewById(R.id.imgCarrito);

        if(status==null){
            status="0";
        }


        Toast.makeText(this, ""+idUsuario, Toast.LENGTH_SHORT).show();
        opc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(getApplicationContext(),
                        "Hiciste Click en Menú 1", Toast.LENGTH_SHORT).show();*/
                int idC = 1;

                Intent intent = new Intent(MenuActivity.this,ProductosActivity.class);
                intent.putExtra("status",status);
                intent.putExtra("idC",idC);
                intent.putExtra("idVenta",idVenta);
                intent.putExtra("ID",idUsuario);
                startActivity(intent);


            }
        });

        opc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(getApplicationContext(),
                        "Hiciste Click en Menú 2", Toast.LENGTH_SHORT).show();*/

                int idC = 2;

                Intent intent = new Intent(MenuActivity.this,ProductosActivity.class);
                intent.putExtra("status",status);
                intent.putExtra("idC",idC);
                intent.putExtra("idVenta",idVenta);
                intent.putExtra("ID",idUsuario);
                startActivity(intent);
            }
        });

        opc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(getApplicationContext(),
                        "Hiciste Click en Menú 1", Toast.LENGTH_SHORT).show();*/
                int idC = 1;

                Intent intent = new Intent(MenuActivity.this, DetalleActivity.class);
                intent.putExtra("idVenta",idVenta);
                intent.putExtra("idC",idC);
                intent.putExtra("ID",idUsuario);
                startActivity(intent);


            }
        });


    }
}

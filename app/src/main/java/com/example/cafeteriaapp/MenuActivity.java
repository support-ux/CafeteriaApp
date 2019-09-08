package com.example.cafeteriaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
ImageView opc1,opc2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        opc1 =findViewById(R.id.imgBebidas);
        opc2 = findViewById(R.id.imgAlimentos);

        opc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Toast.makeText(getApplicationContext(),
                        "Hiciste Click en Menú 1", Toast.LENGTH_SHORT).show();*/
                int idC = 1;

                Intent intent = new Intent(MenuActivity.this,ProductosActivity.class);
                intent.putExtra("idC",idC);
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
                intent.putExtra("idC",idC);
                startActivity(intent);
            }
        });
    }
}

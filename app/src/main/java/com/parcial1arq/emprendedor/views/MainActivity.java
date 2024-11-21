package com.parcial1arq.emprendedor.views;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import com.parcial1arq.emprendedor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincula el botón de categorías
        Button btnCategorias = findViewById(R.id.btnCategorias);

        // Configura el listener para ir a la pantalla de gestionar categorías
        btnCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de Categoría
                Intent intent = new Intent(MainActivity.this, CategoriaActivity.class);
                startActivity(intent);
            }
        });
    }
}
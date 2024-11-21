package com.parcial1arq.emprendedor.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.parcial1arq.emprendedor.R;
import com.parcial1arq.emprendedor.models.CategoriaModel;
import com.parcial1arq.emprendedor.database.DBConnection;
import com.parcial1arq.emprendedor.controllers.CategoriaController;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {

    private EditText editTextCategoria;
    private RecyclerView recyclerViewCategorias;
    private CategoriaAdapter adapter;
    private CategoriaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        editTextCategoria = findViewById(R.id.editTextCategoria);
        Button btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);
        recyclerViewCategorias = findViewById(R.id.recyclerViewCategorias);

        // Inicializar la conexión y el modelo
        DBConnection dbConnection = new DBConnection(this);
        CategoriaModel modelo = new CategoriaModel(dbConnection);

        // Inicializar el controlador
        controller = new CategoriaController(this, modelo);

        // Acción para agregar una nueva categoría
        btnAgregarCategoria.setOnClickListener(v -> {
            String descripcion = editTextCategoria.getText().toString();
            if (!descripcion.isEmpty()) {
                controller.agregarCategoria(descripcion);
                editTextCategoria.setText("");
            }
        });

        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(this));
    }

    // Actualizar la lista de categorías
    public void actualizarListaCategorias(List<String> categorias) {
        adapter = new CategoriaAdapter(categorias);
        recyclerViewCategorias.setAdapter(adapter);
    }

    // Mostrar el diálogo para editar una categoría
    public void mostrarDialogoEditarCategoria(String descripcionVieja) {
        editTextCategoria.setText(descripcionVieja);
        findViewById(R.id.btnAgregarCategoria).setOnClickListener(v -> {
            String nuevaDescripcion = editTextCategoria.getText().toString();
            if (!nuevaDescripcion.isEmpty()) {
                controller.editarCategoria(descripcionVieja, nuevaDescripcion);
                editTextCategoria.setText("");
            }
        });
    }

    // Eliminar una categoría seleccionada
    public void eliminarCategoriaSeleccionada(String descripcion) {
        controller.eliminarCategoria(descripcion);
    }
}

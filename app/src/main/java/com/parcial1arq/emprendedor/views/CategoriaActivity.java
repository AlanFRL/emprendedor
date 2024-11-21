package com.parcial1arq.emprendedor.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.parcial1arq.emprendedor.R;
import com.parcial1arq.emprendedor.models.Categoria;
import com.parcial1arq.emprendedor.controllers.CategoriaController;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {
    private EditText txtDescripcionCategoria;
    private Button btnGuardar, btnEditar, btnEliminar;
    private ListView listaCategorias;

    private CategoriaController controller;

    // Variable para mantener la categoría seleccionada
    private int categoriaSeleccionadaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Vincular vistas
        txtDescripcionCategoria = findViewById(R.id.txtDescripcionCategoria);
        btnGuardar = findViewById(R.id.btnGuardarCategoria);
        btnEditar = findViewById(R.id.btnEditarCategoria);
        btnEliminar = findViewById(R.id.btnEliminarCategoria);
        listaCategorias = findViewById(R.id.listaCategorias);

        // Inicializar controlador
        controller = new CategoriaController(this);

        // Configurar eventos
        btnGuardar.setOnClickListener(v -> controller.guardarCategoria(txtDescripcionCategoria.getText().toString()));
        btnEditar.setOnClickListener(v -> editarCategoria());
        btnEliminar.setOnClickListener(v -> eliminarCategoria());

        // Configurar listener para seleccionar una categoría en la lista
        listaCategorias.setOnItemClickListener((parent, view, position, id) -> {
            // Obtiene la categoría seleccionada
            Categoria categoria = (Categoria) parent.getItemAtPosition(position);
            categoriaSeleccionadaId = categoria.getId(); // Guarda el ID
            txtDescripcionCategoria.setText(categoria.getDescripcion()); // Muestra en el EditText
        });

        // Cargar categorías
        controller.cargarCategorias();
    }

    private void editarCategoria() {
        String nuevaDescripcion = txtDescripcionCategoria.getText().toString();
        if (categoriaSeleccionadaId == -1) {
            mostrarMensaje("Debe seleccionar una categoría para editar");
            return;
        }
        if (nuevaDescripcion.isEmpty()) {
            mostrarMensaje("La descripción no puede estar vacía");
            return;
        }
        controller.editarCategoria(categoriaSeleccionadaId, nuevaDescripcion);
    }

    private void eliminarCategoria() {
        if (categoriaSeleccionadaId == -1) {
            mostrarMensaje("Debe seleccionar una categoría para eliminar");
            return;
        }
        controller.eliminarCategoria(categoriaSeleccionadaId);
        categoriaSeleccionadaId = -1; // Reinicia la selección
        txtDescripcionCategoria.setText(""); // Limpia el campo de texto
    }

    public void mostrarCategorias(List<Categoria> categorias) {
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, categorias);
        listaCategorias.setAdapter(adapter);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}

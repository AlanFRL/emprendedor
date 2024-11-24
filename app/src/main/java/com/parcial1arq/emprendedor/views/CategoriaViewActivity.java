package com.parcial1arq.emprendedor.views;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.parcial1arq.emprendedor.R;
import com.parcial1arq.emprendedor.controllers.CategoriaController;
import java.util.List;

public class CategoriaViewActivity extends AppCompatActivity {
    private EditText txtDescripcion;
    private Button btnGuardar, btnEditar, btnEliminar;
    private ListView listaCategorias;



    // Almacenaremos la lista completa con IDs para evitar dependencia del modelo
    private List<String[]> listaCompletaCategorias;
    private int categoriaSeleccionadaId = -1;
    private CategoriaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Vincular vistas
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuardar = findViewById(R.id.btnGuardarCategoria);
        btnEditar = findViewById(R.id.btnEditarCategoria);
        btnEliminar = findViewById(R.id.btnEliminarCategoria);
        listaCategorias = findViewById(R.id.listaCategorias);

        // Inicializar controlador
        controller = new CategoriaController(this);

        // Configurar eventos
        btnGuardar.setOnClickListener(v -> guardarCategoria());
        btnEditar.setOnClickListener(v -> editarCategoria());
        btnEliminar.setOnClickListener(v -> eliminarCategoria());

        // Configurar listener para seleccionar una categoría en la lista
        listaCategorias.setOnItemClickListener((parent, view, position, id) -> {
            categoriaSeleccionadaId = Integer.parseInt(listaCompletaCategorias.get(position)[0]);
            txtDescripcion.setText(listaCompletaCategorias.get(position)[1]);
        });

        // Cargar categorías
        controller.cargarCategorias();
    }

    private void guardarCategoria() {
        String Descripcion = txtDescripcion.getText().toString();
        if (Descripcion.isEmpty()) {
            mostrarMensaje("Todos los campos son necesarios.");
            return;
        }
        controller.guardarCategoria(Descripcion);
        txtDescripcion.setText("");
    }

    private void editarCategoria() {
        String nuevaDescripcion = txtDescripcion.getText().toString();
        if (categoriaSeleccionadaId == -1) {
            mostrarMensaje("Debe seleccionar una categoría para editar.");
            return;
        }
        controller.actualizarCategoria(categoriaSeleccionadaId, nuevaDescripcion);
        txtDescripcion.setText("");
    }

    private void eliminarCategoria() {
        if (categoriaSeleccionadaId == -1) {
            mostrarMensaje("Debe seleccionar una categoría para eliminar.");
            return;
        }
        controller.eliminarCategoria(categoriaSeleccionadaId);
        categoriaSeleccionadaId = -1;
        txtDescripcion.setText("");
    }

    public void mostrarCategorias(String[] descripciones, List<String[]> listaCompleta) {
        this.listaCompletaCategorias = listaCompleta;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, descripciones);
        listaCategorias.setAdapter(adapter);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}

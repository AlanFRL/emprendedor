package com.parcial1arq.emprendedor.views;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.parcial1arq.emprendedor.R;
import com.parcial1arq.emprendedor.controllers.CatalogoController;
import java.util.List;

public class CatalogoViewActivity extends AppCompatActivity {
    private EditText txtDescripcion;
    private Button btnGuardar, btnEliminar, btnEditar;
    private ListView listaCatalogos;
    private ListView listaProductos;
    private CatalogoController controller;

    private int catalogoSeleccionadoId = -1;
    private List<String[]> listaCompletaCatalogos;
    private List<Integer> productosSeleccionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        txtDescripcion = findViewById(R.id.txtDescripcionCatalogo);
        btnGuardar = findViewById(R.id.btnGuardarCatalogo);
        btnEditar = findViewById(R.id.btnEditarCatalogo);
        btnEliminar = findViewById(R.id.btnEliminarCatalogo);
        listaCatalogos = findViewById(R.id.listaCatalogos);
        listaProductos = findViewById(R.id.listaProductos);

        controller = new CatalogoController(this);

        btnGuardar.setOnClickListener(v -> guardarCatalogo());
        btnEditar.setOnClickListener(v -> editarCatalogo());
        btnEliminar.setOnClickListener(v -> eliminarCatalogo());

        listaCatalogos.setOnItemClickListener((parent, view, position, id) -> {
            String[] catalogo = listaCompletaCatalogos.get(position);
            catalogoSeleccionadoId = Integer.parseInt(catalogo[0]);
            txtDescripcion.setText(catalogo[1]);
            productosSeleccionados = controller.obtenerProductosPorCatalogo(catalogoSeleccionadoId);
        });

        controller.cargarCatalogos();
    }

    private void guardarCatalogo() {
        String descripcion = txtDescripcion.getText().toString();
        controller.guardarCatalogo(descripcion, productosSeleccionados);
    }

    private void editarCatalogo() {
        String descripcion = txtDescripcion.getText().toString();
        controller.actualizarCatalogo(catalogoSeleccionadoId, descripcion, productosSeleccionados);
    }

    private void eliminarCatalogo() {
        controller.eliminarCatalogo(catalogoSeleccionadoId);
    }

    public void mostrarCatalogos(String[] nombresCatalogos, List<String[]> catalogos) {
        this.listaCompletaCatalogos = catalogos;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresCatalogos);
        listaCatalogos.setAdapter(adapter);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
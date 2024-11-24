package com.parcial1arq.emprendedor.views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.parcial1arq.emprendedor.R;
import com.parcial1arq.emprendedor.controllers.ProductoController;
import java.util.List;

public class ProductoViewActivity extends AppCompatActivity {
    private EditText txtCodigo, txtNombre, txtDescripcion, txtPrecio, txtStock;
    private Spinner spinnerCategoria;
    private Button btnGuardar, btnEliminar, btnEditar, btnSeleccionarFoto;
    private ListView listaProductos;
    private ImageView imgProducto;


    private int productoSeleccionadoId = -1;
    private String categoriaSeleccionadaId = null;
    private Uri imagenSeleccionadaUri;

    private List<String[]> listaCompletaProductos;
    private List<String[]> listaCompletaCategorias;
    private ProductoController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        // Vincular vistas
        txtCodigo = findViewById(R.id.txtCodigoProducto);
        txtNombre = findViewById(R.id.txtNombreProducto);
        txtDescripcion = findViewById(R.id.txtDescripcionProducto);
        txtPrecio = findViewById(R.id.txtPrecioProducto);
        txtStock = findViewById(R.id.txtStockProducto);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnGuardar = findViewById(R.id.btnGuardarProducto);
        btnEliminar = findViewById(R.id.btnEliminarProducto);
        btnEditar = findViewById(R.id.btnEditarProducto);
        btnSeleccionarFoto = findViewById(R.id.btnSeleccionarFoto);
        listaProductos = findViewById(R.id.listaProductos);
        imgProducto = findViewById(R.id.imgProducto);

        // Inicializar controlador
        controller = new ProductoController(this);

        // Configurar eventos
        btnGuardar.setOnClickListener(v -> guardarProducto());
        btnEliminar.setOnClickListener(v -> eliminarProducto());
        btnEditar.setOnClickListener(v -> editarProducto());
        btnSeleccionarFoto.setOnClickListener(v -> seleccionarFoto());

        // Configurar listener para selección en la lista
        listaProductos.setOnItemClickListener((parent, view, position, id) -> {
            String[] producto = listaCompletaProductos.get(position);
            productoSeleccionadoId = Integer.parseInt(producto[0]);
            categoriaSeleccionadaId = producto[1];
            txtCodigo.setText(producto[4]);
            txtNombre.setText(producto[2]);
            txtDescripcion.setText(producto[3]);
            txtPrecio.setText(producto[6]);
            txtStock.setText(producto[7]);
            imgProducto.setImageURI(producto[5] != null ? Uri.parse(producto[5]) : null);
            spinnerCategoria.setSelection(getCategoriaPosition(categoriaSeleccionadaId));
        });

        verificarPermisos();
        // Cargar categorías y productos
        controller.cargarCategorias();
        controller.cargarProductos();
    }

    private void guardarProducto() {
        String codigo = txtCodigo.getText().toString();
        String nombre = txtNombre.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String precioTexto = txtPrecio.getText().toString();
        String stockTexto = txtStock.getText().toString();
        String idCategoria = listaCompletaCategorias.get(spinnerCategoria.getSelectedItemPosition())[0];

        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.");
            return;
        }

        double precio = Double.parseDouble(precioTexto);
        int stock = Integer.parseInt(stockTexto);
        // Usar la imagen existente si no hay una nueva seleccionada
        String imagen = (imagenSeleccionadaUri != null) ? imagenSeleccionadaUri.toString() : null;

        controller.guardarProducto(codigo, nombre, descripcion, Integer.parseInt(idCategoria), imagen, precio, stock);
        limpiarCampos();
    }

    private void editarProducto() {
        if (productoSeleccionadoId == -1) {
            mostrarMensaje("Seleccione un producto para editar.");
            return;
        }

        String codigo = txtCodigo.getText().toString();
        String nombre = txtNombre.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String precioTexto = txtPrecio.getText().toString();
        String stockTexto = txtStock.getText().toString();
        String idCategoria = listaCompletaCategorias.get(spinnerCategoria.getSelectedItemPosition())[0];

        if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || precioTexto.isEmpty() || stockTexto.isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.");
            return;
        }

        double precio = Double.parseDouble(precioTexto);
        int stock = Integer.parseInt(stockTexto);
        String imagen = (imagenSeleccionadaUri != null)
                ? imagenSeleccionadaUri.toString()
                : obtenerImagenActual(productoSeleccionadoId);

        controller.actualizarProducto(productoSeleccionadoId, codigo, nombre, descripcion, Integer.parseInt(idCategoria),
                imagen, precio, stock);
        limpiarCampos();
    }

    private void eliminarProducto() {
        if (productoSeleccionadoId == -1) {
            mostrarMensaje("Seleccione un producto para eliminar.");
            return;
        }

        controller.eliminarProducto(productoSeleccionadoId);
        limpiarCampos();
    }


    private void seleccionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        seleccionarFotoLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> seleccionarFotoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imagenSeleccionadaUri = result.getData().getData();
                    imgProducto.setImageURI(imagenSeleccionadaUri);
                }
            });

    public void mostrarCategorias(String[] nombresCategorias, List<String[]> categorias) {
        this.listaCompletaCategorias = categorias;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }

    public void mostrarProductos(String[] nombresProductos, List<String[]> productos) {
        this.listaCompletaProductos = productos;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresProductos);
        listaProductos.setAdapter(adapter);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        imgProducto.setImageURI(null);
        imagenSeleccionadaUri = null;
        productoSeleccionadoId = -1;
    }

    private int getCategoriaPosition(String idCategoria) {
        for (int i = 0; i < listaCompletaCategorias.size(); i++) {
            if (listaCompletaCategorias.get(i)[0].equals(idCategoria)) {
                return i;
            }
        }
        return 0;
    }

    private void verificarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    private String obtenerImagenActual(int idProducto) {
        for (String[] producto : listaCompletaProductos) {
            if (Integer.parseInt(producto[0]) == idProducto) {
                return producto[5];
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

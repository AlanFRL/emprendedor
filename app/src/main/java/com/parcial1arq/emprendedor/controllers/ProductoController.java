package com.parcial1arq.emprendedor.controllers;

import com.parcial1arq.emprendedor.models.ProductoModel;
import com.parcial1arq.emprendedor.models.CategoriaModel;
import com.parcial1arq.emprendedor.views.ProductoViewActivity;
import java.util.List;

public class ProductoController {
    private ProductoModel productoModel;
    private CategoriaModel categoriaModel;
    private ProductoViewActivity productoView;

    public ProductoController(ProductoViewActivity view) {
        this.productoView = view;
        this.productoModel = new ProductoModel(view);
        this.categoriaModel = new CategoriaModel(view);
    }

    // Método para agregar producto
    public void guardarProducto(String codigo, String nombre, String descripcion, int idCategoria, String imagen, double precio, int stock) {
        try {
            productoModel.agregarProducto(codigo, nombre, descripcion, idCategoria, imagen, precio, stock);
            productoView.mostrarMensaje("Producto guardado con éxito.");
            cargarProductos();
        } catch (Exception e) {
            productoView.mostrarMensaje(e.getMessage());
        }
    }

    // Método para cargar productos
    public void cargarProductos() {
        List<String[]> productos = productoModel.obtenerProductos();
        //String[] nombres = productos.stream().map(p -> p[2]).toArray(String[]::new);
        // Concatenar nombre, precio y stock para mostrar en la lista
        String[] nombres = productos.stream()
                .map(p -> p[2] + " - Precio: $" + p[6] + " - Stock: " + p[7])
                .toArray(String[]::new);
        productoView.mostrarProductos(nombres, productos);
    }

    // Método para cargar categorías en el spinner
    public void cargarCategorias() {
        List<String[]> categorias = categoriaModel.obtenerCategorias();
        String[] nombresCategorias = categorias.stream().map(c -> c[1]).toArray(String[]::new);
        productoView.mostrarCategorias(nombresCategorias, categorias);
    }

    // Método para actualizar producto
    public void actualizarProducto(int id, String codigo, String nombre, String descripcion, int idCategoria, String imagen, double precio, int stock) {
        try {
            productoModel.actualizarProducto(id, codigo, nombre, descripcion, idCategoria, imagen, precio, stock);
            productoView.mostrarMensaje("Producto actualizado con éxito.");
            cargarProductos();
        } catch (Exception e) {
            productoView.mostrarMensaje(e.getMessage());
        }
    }

    // Método para eliminar producto
    public void eliminarProducto(int id) {
        productoModel.eliminarProducto(id);
        productoView.mostrarMensaje("Producto eliminado con éxito.");
        cargarProductos();
    }


}

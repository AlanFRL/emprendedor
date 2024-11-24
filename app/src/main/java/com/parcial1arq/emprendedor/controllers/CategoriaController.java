package com.parcial1arq.emprendedor.controllers;

import com.parcial1arq.emprendedor.models.CategoriaModel;
import com.parcial1arq.emprendedor.views.CategoriaViewActivity;
import java.util.List;

public class CategoriaController {
    private CategoriaModel model;
    private CategoriaViewActivity view;

    public CategoriaController(CategoriaViewActivity view) {
        this.view = view;
        this.model = new CategoriaModel(view);
    }

    // Método para agregar categoría
    public void guardarCategoria(String descripcion) {
        try {
            model.agregarCategoria(descripcion);
            view.mostrarMensaje("Categoría guardada con éxito.");
            cargarCategorias();
        } catch (Exception e) {
            view.mostrarMensaje(e.getMessage());
        }
    }

    // Método para cargar las categorías y actualizar la vista
    public void cargarCategorias() {
        List<String[]> categorias = model.obtenerCategorias();
        // Convertimos las categorías a un formato que la vista pueda manejar
        String[] descripciones = categorias.stream().map(c -> c[1]).toArray(String[]::new);
        view.mostrarCategorias(descripciones, categorias);
    }

    // Método para editar categoría
    public void actualizarCategoria(int id, String descripcion) {
        try {
            model.actualizarCategoria(id, descripcion);
            view.mostrarMensaje("Categoría actualizada con éxito.");
            cargarCategorias();
        } catch (Exception e) {
            view.mostrarMensaje(e.getMessage());
        }
    }

    // Método para eliminar categoría
    public void eliminarCategoria(int id) {
        model.eliminarCategoria(id);
        view.mostrarMensaje("Categoría eliminada con éxito.");
        cargarCategorias();
    }
}

package com.parcial1arq.emprendedor.controllers;

import android.content.Context;
import com.parcial1arq.emprendedor.database.DBConnection;
import com.parcial1arq.emprendedor.models.Categoria;
import com.parcial1arq.emprendedor.models.CategoriaModel;
import com.parcial1arq.emprendedor.views.CategoriaActivity;
import java.util.List;

public class CategoriaController {
    private CategoriaModel categoriaModel;
    private CategoriaActivity categoriaView;



    public CategoriaController(CategoriaActivity view) {
        this.categoriaView = view;
        //DBConnection dbConnection = new DBConnection((Context) view);
        this.categoriaModel = new CategoriaModel(view);
    }

    // Método para agregar categoría
    public void guardarCategoria(String descripcion) {
        if (descripcion.isEmpty()) {
            categoriaView.mostrarMensaje("La descripción no puede estar vacía");
            return;
        }
        categoriaModel.agregarCategoria(descripcion);
        categoriaView.mostrarMensaje("Categoría guardada");
        cargarCategorias();
    }

    // Método para cargar las categorías y actualizar la vista
    public void cargarCategorias() {
        List<Categoria> categorias = categoriaModel.obtenerCategorias();
        categoriaView.mostrarCategorias(categorias);
    }

    // Método para editar categoría
    public void editarCategoria(int id, String nuevaDescripcion) {
        categoriaModel.actualizarCategoria(id, nuevaDescripcion);
        categoriaView.mostrarMensaje("Categoría actualizada");
        cargarCategorias();
    }

    // Método para eliminar categoría
    public void eliminarCategoria(int id) {
        categoriaModel.eliminarCategoria(id);
        categoriaView.mostrarMensaje("Categoría eliminada");
        cargarCategorias();
    }
}

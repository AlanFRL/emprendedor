package com.parcial1arq.emprendedor.controllers;

import com.parcial1arq.emprendedor.models.Categoria;
import com.parcial1arq.emprendedor.models.CategoriaModel;
import com.parcial1arq.emprendedor.views.CategoriaActivity;

public class CategoriaController {
    private CategoriaModel categoriaModel;
    private CategoriaActivity categoriaView;

    public CategoriaController(CategoriaActivity view, CategoriaModel model) {
        this.categoriaView = view;
        this.categoriaModel = model;
        cargarCategorias();
    }

    // Método para agregar categoría
    public void agregarCategoria(String descripcion) {
        Categoria categoria = new Categoria(0, descripcion); // ID autogenerado
        categoriaModel.agregarCategoria(categoria);
        cargarCategorias(); // Actualizamos la lista
    }

    // Método para cargar las categorías y actualizar la vista
    public void cargarCategorias() {
        categoriaView.actualizarListaCategorias(categoriaModel.obtenerCategorias());
    }

    // Método para editar categoría
    public void editarCategoria(String descripcionVieja, String descripcionNueva) {
        // Buscar la categoría a editar en el modelo
        for (Categoria categoria : categoriaModel.obtenerCategorias()) {
            if (categoria.getDescripcion().equals(descripcionVieja)) {
                categoria.setDescripcion(descripcionNueva);
                categoriaModel.actualizarCategoria(categoria);
                break;
            }
        }
        cargarCategorias(); // Actualizamos la lista
    }

    // Método para eliminar categoría
    public void eliminarCategoria(String descripcion) {
        for (Categoria categoria : categoriaModel.obtenerCategorias()) {
            if (categoria.getDescripcion().equals(descripcion)) {
                categoriaModel.eliminarCategoria(categoria.getId());
                break;
            }
        }
        cargarCategorias(); // Actualizamos la lista
    }
}

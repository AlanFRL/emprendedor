package com.parcial1arq.emprendedor.controllers;

import com.parcial1arq.emprendedor.models.CatalogoModel;
import com.parcial1arq.emprendedor.views.CatalogoViewActivity;
import java.util.List;

public class CatalogoController {
    private CatalogoModel model;
    private CatalogoViewActivity view;

    public CatalogoController(CatalogoViewActivity view) {
        this.view = view;
        this.model = new CatalogoModel(view);
    }

    public void guardarCatalogo(String descripcion, List<Integer> productos) {
        try {
            model.agregarCatalogo(descripcion, productos);
            view.mostrarMensaje("Catálogo guardado con éxito.");
            cargarCatalogos();
        } catch (Exception e) {
            view.mostrarMensaje(e.getMessage());
        }
    }

    public void cargarCatalogos() {
        List<String[]> catalogos = model.obtenerCatalogos();
        String[] nombres = catalogos.stream().map(c -> c[1]).toArray(String[]::new);
        view.mostrarCatalogos(nombres, catalogos);
    }

    public void actualizarCatalogo(int id, String descripcion, List<Integer> productos) {
        try {
            model.actualizarCatalogo(id, descripcion, productos);
            view.mostrarMensaje("Catálogo actualizado con éxito.");
            cargarCatalogos();
        } catch (Exception e) {
            view.mostrarMensaje(e.getMessage());
        }
    }

    public void eliminarCatalogo(int id) {
        model.eliminarCatalogo(id);
        view.mostrarMensaje("Catálogo eliminado con éxito.");
        cargarCatalogos();
    }

    public List<Integer> obtenerProductosPorCatalogo(int id) {
        return model.obtenerProductosPorCatalogo(id);
    }
}

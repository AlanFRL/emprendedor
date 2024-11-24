package com.parcial1arq.emprendedor.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.parcial1arq.emprendedor.database.DBConnection;

import java.util.ArrayList;
import java.util.List;

public class CatalogoModel {
    private int id;
    private String descripcion;
    private List<Integer> productos; // IDs de productos asociados al catálogo
    private DBConnection dbConnection;

    public CatalogoModel(Context context) {
        this.dbConnection = new DBConnection(context);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<Integer> getProductos() {
        return productos;
    }
    public void setProductos(List<Integer> productos) {
        this.productos = productos;
    }

    // Método para agregar un catálogo
    public void agregarCatalogo(String descripcion, List<Integer> productos) {
        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", descripcion);

        long catalogoId = db.insert("catalogo", null, values);

        if (catalogoId != -1) {
            // Insertar la relación con los productos
            for (Integer productoId : productos) {
                ContentValues articuloValues = new ContentValues();
                articuloValues.put("idcatalogo", catalogoId);
                articuloValues.put("idproducto", productoId);
                db.insert("articulo", null, articuloValues);
            }
        }
        db.close();
    }

    // Método para obtener todos los catálogos
    public List<String[]> obtenerCatalogos() {
        List<String[]> catalogos = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getDatabase();
        Cursor cursor = db.rawQuery("SELECT id, descripcion FROM catalogo", null);

        if (cursor.moveToFirst()) {
            do {
                catalogos.add(new String[]{
                        String.valueOf(cursor.getInt(0)), // ID
                        cursor.getString(1)              // Descripción
                });
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return catalogos;
    }

    // Método para obtener los productos de un catálogo
    public List<Integer> obtenerProductosPorCatalogo(int catalogoId) {
        List<Integer> productos = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getDatabase();
        Cursor cursor = db.rawQuery("SELECT idproducto FROM articulo WHERE idcatalogo = ?", new String[]{String.valueOf(catalogoId)});

        if (cursor.moveToFirst()) {
            do {
                productos.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productos;
    }

    // Método para actualizar un catálogo
    public void actualizarCatalogo(int id, String descripcion, List<Integer> productos) {
        SQLiteDatabase db = dbConnection.getDatabase();

        // Actualizar la descripción del catálogo
        ContentValues values = new ContentValues();
        values.put("descripcion", descripcion);
        db.update("catalogo", values, "id = ?", new String[]{String.valueOf(id)});

        // Eliminar relaciones anteriores
        db.delete("articulo", "idcatalogo = ?", new String[]{String.valueOf(id)});

        // Insertar nuevas relaciones
        for (Integer productoId : productos) {
            ContentValues articuloValues = new ContentValues();
            articuloValues.put("idcatalogo", id);
            articuloValues.put("idproducto", productoId);
            db.insert("articulo", null, articuloValues);
        }

        db.close();
    }

    // Método para eliminar un catálogo
    public void eliminarCatalogo(int id) {
        SQLiteDatabase db = dbConnection.getDatabase();

        // Eliminar relaciones en "articulo"
        db.delete("articulo", "idcatalogo = ?", new String[]{String.valueOf(id)});

        // Eliminar el catálogo
        db.delete("catalogo", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

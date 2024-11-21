package com.parcial1arq.emprendedor.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.parcial1arq.emprendedor.database.DBConnection;
import java.util.ArrayList;
import java.util.List;

public class CategoriaModel {
    private DBConnection dbConnection;

    public CategoriaModel(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Método para agregar una nueva categoría.
    public void agregarCategoria(String descripcion) {
        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", descripcion);
        db.insert("categoria", null, values);
        db.close();
    }

    // Método para obtener todas las categorías.
    public List<String> obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getDatabase();
        Cursor cursor = db.rawQuery("SELECT descripcion FROM categoria", null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }

    // Método para eliminar una categoría.
    public void eliminarCategoria(String descripcion) {
        SQLiteDatabase db = dbConnection.getDatabase();
        db.delete("categoria", "descripcion = ?", new String[]{descripcion});
        db.close();
    }

    // Método para actualizar una categoría.
    public void actualizarCategoria(String descripcionVieja, String descripcionNueva) {
        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", descripcionNueva);
        db.update("categoria", values, "descripcion = ?", new String[]{descripcionVieja});
        db.close();
    }
}

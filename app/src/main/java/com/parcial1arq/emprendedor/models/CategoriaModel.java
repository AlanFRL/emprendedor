package com.parcial1arq.emprendedor.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.parcial1arq.emprendedor.database.DBConnection;
import com.parcial1arq.emprendedor.views.CategoriaActivity;
import java.util.ArrayList;
import java.util.List;

public class CategoriaModel {
    private DBConnection dbConnection;

    public CategoriaModel(CategoriaActivity view) {
        DBConnection dbConnection = new DBConnection((Context) view);
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
    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getDatabase();
        Cursor cursor = db.rawQuery("SELECT id, descripcion FROM categoria", null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(new Categoria(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }

    // Método para eliminar una categoría.
    public void eliminarCategoria(int id) {
        SQLiteDatabase db = dbConnection.getDatabase();
        db.delete("categoria", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para actualizar una categoría.
    public void actualizarCategoria(int id, String nuevaDescripcion) {
        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", nuevaDescripcion);
        db.update("categoria", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}

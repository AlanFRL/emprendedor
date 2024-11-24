package com.parcial1arq.emprendedor.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.parcial1arq.emprendedor.database.DBConnection;
import java.util.ArrayList;
import java.util.List;

public class CategoriaModel {
    private int id;
    private String descripcion;

    private DBConnection dbConnection;

    // Constructor sin parámetros (opcional, útil para inicialización vacía)
    public CategoriaModel() {
    }

    public CategoriaModel(Context context) {
        this.dbConnection = new DBConnection(context);
    }

    // Constructor con parámetros (útil para asignar atributos directamente)
    public CategoriaModel(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Getters y Setters para los atributos
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

    // Método para agregar una nueva categoría con validaciones
    public void agregarCategoria(String descripcion) throws Exception {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new Exception("La descripción no puede estar vacía.");
        }
        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", descripcion);
        db.insert("categoria", null, values);
        db.close();
    }

    // Método para obtener todas las categorías (como objetos de tipo Categoria)
    public List<String[]> obtenerCategorias() {
        List<String[]> categorias = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getDatabase();
        Cursor cursor = db.rawQuery("SELECT id, descripcion FROM categoria", null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(new String[]{String.valueOf(cursor.getInt(0)), cursor.getString(1)});
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }

    // Método para actualizar una categoría con validaciones
    public void actualizarCategoria(int id, String nuevaDescripcion) throws Exception {
        if (nuevaDescripcion == null || nuevaDescripcion.trim().isEmpty()) {
            throw new Exception("La descripción no puede estar vacía.");
        }
        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("descripcion", nuevaDescripcion);
        db.update("categoria", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para eliminar una categoría
    public void eliminarCategoria(int id) {
        SQLiteDatabase db = dbConnection.getDatabase();
        db.delete("categoria", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }


}

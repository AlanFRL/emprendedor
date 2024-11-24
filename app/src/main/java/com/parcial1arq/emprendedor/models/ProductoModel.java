package com.parcial1arq.emprendedor.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.parcial1arq.emprendedor.database.DBConnection;
import java.util.ArrayList;
import java.util.List;

public class ProductoModel {
    private int id;
    private int idCategoria;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precioVenta;
    private int stock;
    private DBConnection dbConnection;

    public ProductoModel(Context context) {
        this.dbConnection = new DBConnection(context);
    }

    public ProductoModel(int id, int idCategoria, String codigo, String nombre, String descripcion, String imagen, double precioVenta, int stock) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }

    // Atributos con getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }



    // Método para agregar producto
    public void agregarProducto(String codigo, String nombre, String descripcion, int idCategoria, String imagen, double precio, int stock) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío.");
        }

        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("idcategoria", idCategoria);
        values.put("imagen", imagen);
        values.put("precioVenta", precio);
        values.put("stock", stock);

        db.insert("producto", null, values);
        db.close();
    }

    // Método para obtener productos
    public List<String[]> obtenerProductos() {
        List<String[]> productos = new ArrayList<>();
        SQLiteDatabase db = dbConnection.getDatabase();
        Cursor cursor = db.rawQuery("SELECT id, idcategoria, nombre, descripcion, codigo, imagen, precioVenta, stock FROM producto", null);

        if (cursor.moveToFirst()) {
            do {
                productos.add(new String[]{
                        String.valueOf(cursor.getInt(0)), // ID
                        String.valueOf(cursor.getInt(1)), // ID Categoría
                        cursor.getString(2),             // Nombre
                        cursor.getString(3),             // Descripción
                        cursor.getString(4),             // Código
                        cursor.getString(5),             // Imagen
                        String.valueOf(cursor.getDouble(6)), // Precio
                        String.valueOf(cursor.getInt(7)) // Stock
                });
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productos;
    }

    // Método para actualizar producto
    public void actualizarProducto(int id, String codigo, String nombre, String descripcion, int idCategoria, String imagen, double precio, int stock) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío.");
        }

        SQLiteDatabase db = dbConnection.getDatabase();
        ContentValues values = new ContentValues();
        values.put("codigo", codigo);
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("idcategoria", idCategoria);
        values.put("imagen", imagen);
        values.put("precioVenta", precio);
        values.put("stock", stock);

        db.update("producto", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Método para eliminar producto
    public void eliminarProducto(int id) {
        SQLiteDatabase db = dbConnection.getDatabase();
        db.delete("producto", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}

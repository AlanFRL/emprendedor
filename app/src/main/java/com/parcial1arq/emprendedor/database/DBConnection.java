package com.parcial1arq.emprendedor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "emprendedor.db";
    private static final int DATABASE_VERSION = 4;

    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // La creación de tablas sigue ocurriendo aquí.
        // Crear tabla de categoría.
        db.execSQL("CREATE TABLE IF NOT EXISTS categoria (id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion TEXT NOT NULL)");

        // Crear tabla de producto
        db.execSQL("CREATE TABLE IF NOT EXISTS producto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idcategoria INTEGER NOT NULL, " +
                "codigo TEXT, " +
                "nombre TEXT NOT NULL, " +
                "descripcion TEXT, " +
                "imagen TEXT, " +
                "precioVenta REAL NOT NULL, " +
                "stock INTEGER NOT NULL, " +
                "FOREIGN KEY (idcategoria) REFERENCES categoria (id) ON DELETE CASCADE ON UPDATE CASCADE)");

        // Crear tabla de cliente
        db.execSQL("CREATE TABLE IF NOT EXISTS cliente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "ci TEXT, " +
                "telefono TEXT NOT NULL, " +
                "ubicacion TEXT NOT NULL)");

        // Crear tabla de nota de venta
        db.execSQL("CREATE TABLE IF NOT EXISTS notaventa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idcliente INTEGER NOT NULL, " +
                "nro INTEGER NOT NULL, " +
                "fecha TEXT NOT NULL, " + // Usar TEXT para almacenar datetime
                "metodoEnvio TEXT NOT NULL, " +
                "montoTotal REAL NOT NULL, " +
                "FOREIGN KEY (idcliente) REFERENCES cliente (id) ON DELETE CASCADE ON UPDATE CASCADE)");

        // Crear tabla de detalle de venta
        db.execSQL("CREATE TABLE IF NOT EXISTS detalleventa (" +
                "id INTEGER NOT NULL, " +
                "idnotaventa INTEGER NOT NULL, " +
                "idproducto INTEGER NOT NULL, " +
                "precio REAL NOT NULL, " +
                "cantidad INTEGER NOT NULL, " +
                "PRIMARY KEY (id, idproducto), " +
                "FOREIGN KEY (idnotaventa) REFERENCES notaventa (id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (idproducto) REFERENCES producto (id) ON DELETE CASCADE ON UPDATE CASCADE)");

        // Crear tabla de catálogo
        db.execSQL("CREATE TABLE IF NOT EXISTS catalogo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descripcion TEXT NOT NULL)");

        // Crear tabla de artículo
        db.execSQL("CREATE TABLE IF NOT EXISTS articulo (" +
                "idcatalogo INTEGER NOT NULL, " +
                "idproducto INTEGER NOT NULL, " +
                "PRIMARY KEY (idcatalogo, idproducto), " +
                "FOREIGN KEY (idcatalogo) REFERENCES catalogo (id) ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (idproducto) REFERENCES producto (id) ON DELETE CASCADE ON UPDATE CASCADE)");

        // Otras tablas que puedan ser necesarias pueden crearse aquí también.
        // ...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS articulo");
        db.execSQL("DROP TABLE IF EXISTS catalogo");
        db.execSQL("DROP TABLE IF EXISTS detalleventa");
        db.execSQL("DROP TABLE IF EXISTS notaventa");
        db.execSQL("DROP TABLE IF EXISTS cliente");
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        onCreate(db);
    }

    public SQLiteDatabase getDatabase() {
        return this.getWritableDatabase();
    }
}

package com.parcial1arq.emprendedor.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mi_aplicacion.db";
    private static final int DATABASE_VERSION = 1;

    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // La creación de tablas sigue ocurriendo aquí.
        // Crear tabla de categoría.
        db.execSQL("CREATE TABLE IF NOT EXISTS categoria (id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion TEXT)");

        // Otras tablas que puedan ser necesarias pueden crearse aquí también.
        // ...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categoria");
        onCreate(db);
    }

    public SQLiteDatabase getDatabase() {
        return this.getWritableDatabase();
    }
}

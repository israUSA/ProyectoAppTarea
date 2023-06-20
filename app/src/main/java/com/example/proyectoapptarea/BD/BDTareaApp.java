package com.example.proyectoapptarea.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proyectoapptarea.entidades.listaTareas;

public class BDTareaApp extends SQLiteOpenHelper {
    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE Tarea (id INTEGER PRIMARY KEY AUTOINCREMENT, fechaCreacion DATETIME, titulo_tarea TEXT, descripcion TEXT, fechaVencimiento DATETIME, hora TIME, prioridad INTEGER, completada INTEGER DEFAULT 0)";
    private static final String BD_NAME = "BDTarea.sqlite";
    private static final int DB_VERSION = 4;

    public BDTareaApp(Context context){
        super(context, BD_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


}

package com.example.proyectoapptarea.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Tarea extends SQLiteOpenHelper {
    private static final String COMMENTS_TABLE_CREATE = "CREATE TABLE Tarea ( id INTEGER PRIMARY KEY AUTOINCREMENT, titulo_tarea TEXT, fechaCreacion Datetime, fechaVencimiento DateTime";
    private static final String BD_NAME = "datosTarea.sqlite";
    private static final int DB_VERSION = 1;

    public Tarea (Context context){
        super(context, BD_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

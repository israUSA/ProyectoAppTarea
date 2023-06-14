package com.example.proyectoapptarea.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.proyectoapptarea.entidades.listaTareas;

public class BdTarea extends BDTareaApp{

    Context context;

    public BdTarea(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public listaTareas verTarea(int id) {


        BDTareaApp dbHelper = new BDTareaApp(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        listaTareas tareas = null;
        Cursor cursorTarea;

        String idd = String.valueOf(id);
        Log.d("tag", "id: " + idd);

        cursorTarea = db.query("Tarea", null, "id = ?", new String[]{idd}, null, null, null);

        if(cursorTarea != null) {
            if (cursorTarea.moveToFirst()) {
                tareas = new listaTareas();
                tareas.setTitulo_tarea(cursorTarea.getString(2));
                tareas.setFechaVencimiento(cursorTarea.getString(4));
                tareas.setDescripcion(cursorTarea.getString(3));
            }else {
                int columnIndex = cursorTarea.getColumnIndex("titulo_tarea");
            }
        }

        cursorTarea.close();
        return tareas;
    }

    public boolean editarTarea(int id, String nombre, String fecha, String descripcion) {

        boolean correcto = false;

        BDTareaApp dbHelper = new BDTareaApp(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE Tarea" + " SET fechaVencimiento = '" + fecha + "', titulo_tarea = '" + nombre + "', descripcion = '" + descripcion + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarTarea(int id) {

        boolean correcto = false;

        BDTareaApp dbHelper = new BDTareaApp(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM Tarea" + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}

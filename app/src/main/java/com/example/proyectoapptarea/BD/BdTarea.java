package com.example.proyectoapptarea.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.proyectoapptarea.entidades.listaTareas;

import java.util.ArrayList;
import java.util.List;

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

        Log.d("ver", "id: "+ id);
        String idd = String.valueOf(id);

        //cursorTarea = db.query("Tarea", null, "id = ?", new String[]{idd}, null, null, null);
        cursorTarea = db.query("Tarea", null, null, null, null, null, null);

        if(cursorTarea.moveToPosition(id)) {

            tareas = new listaTareas();
            tareas.setTitulo_tarea(cursorTarea.getString(2));
            tareas.setFechaVencimiento(cursorTarea.getString(4));
            tareas.setDescripcion(cursorTarea.getString(3));
            //Log.d("Aqui", "id de tareas: " + cursorTarea.getInt(0));
        } else {

            Log.d("no", "nuevo id: " + id);
        }

        // No olvides cerrar el cursor antes de la próxima iteración.
        if (cursorTarea != null) {
            cursorTarea.close();
        }

        return tareas;

    }

    public boolean editarTarea(int id, String nombre, String fecha, String descripcion) {

        boolean correcto = false;
        int idFinal;

        Cursor cursorTarea;

        BDTareaApp dbHelper = new BDTareaApp(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        cursorTarea = db.query("Tarea", null, null, null, null, null, null);

/*        ContentValues valores = new ContentValues();

        valores.put("titulo_tarea", titulo_tarea);
        valores.put("descripcion", descripcion);
        valores.put("fechaVencimiento", fecha);

        db.insert("Tarea", null, valores);*/

        try {
            if(cursorTarea.moveToPosition(id)) {
                Log.d("Aqui", "id de tareas: " + cursorTarea.getInt(0));
                idFinal = cursorTarea.getInt(0);
                id = idFinal;
                db.execSQL("UPDATE Tarea" + " SET fechaVencimiento = '" + fecha + "', titulo_tarea = '" + nombre + "', descripcion = '" + descripcion + "' WHERE id='" + id + "' ");
                correcto = true;
            }
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
        Log.d("id eliminar", "el id es " + id);

        Cursor cursorTarea;

        cursorTarea = db.query("Tarea", null, null, null, null, null, null);

        if (cursorTarea.moveToPosition(id)) {
            int idd = cursorTarea.getInt(cursorTarea.getColumnIndex("id"));
            db.delete("Tarea", "id = ?", new String[]{String.valueOf(idd)});
            correcto = true;
        }

        // Cerrar el cursor después de utilizarlo.
        if (cursorTarea != null) {
            cursorTarea.close();
        }

        return correcto;

    }

    public boolean marcarComoCompletada(int id) {
        boolean correcto = false;
        int idFinal;
        listaTareas tareas = null;
        Cursor cursorTarea;

        BDTareaApp dbHelper = new BDTareaApp(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        cursorTarea = db.query("Tarea", null, null, null, null, null, null);

        if(cursorTarea.moveToPosition(id)) {
            Log.d("Aqui", "id de tareas: " + cursorTarea.getInt(0));
            idFinal = cursorTarea.getInt(0);
            id = idFinal;

            ContentValues contentValues = new ContentValues();
            contentValues.put("completada", 1); // 1 significa verdadero
            db.update("Tarea", contentValues, "id = ?", new String[] { Integer.toString(id) });

            correcto = true;

        } else {

            Log.d("no", "nuevo id: " + id);
        }
        correcto = true;
        return correcto;
    }

/*    public listaTareas buscarTarea(String nombre) {

        listaTareas tareas = null;
        Cursor cursorTarea;

        BDTareaApp dbHelper = new BDTareaApp(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String tableName = "Tarea";
        String[] columns = null;
        String selection = "titulo_tarea LIKE '%" + nombre + "%'";
        cursorTarea = db.query(tableName, columns, selection, null, null, null, null);

        if(cursorTarea != null) {
            if (cursorTarea.moveToFirst()) {
                tareas = new listaTareas();
                tareas.setTitulo_tarea(cursorTarea.getString(2));
                tareas.setFechaVencimiento(cursorTarea.getString(4));
                tareas.setDescripcion(cursorTarea.getString(3));
                tareas.setFechaCreacion(cursorTarea.getString(1));
            }else {
                int columnIndex = cursorTarea.getColumnIndex("titulo_tarea");
            }
        }

        cursorTarea.close();


        return tareas;
    }*/

/*    public List<listaTareas> searchTasks(String query) {
        List<listaTareas> results = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String COLUMN_NOMBRE = "titulo_tarea";

        String selection = COLUMN_NOMBRE + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%"};
        String TABLE_NAME = "Tarea";

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE));
                listaTareas tarea = new listaTareas(nombre);
                results.add(tarea);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return results;
    }*/
}

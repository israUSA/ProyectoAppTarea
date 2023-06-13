package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoapptarea.BD.BDTareaApp;
import com.example.proyectoapptarea.adaptador.listaAdaptador;
import com.example.proyectoapptarea.entidades.listaTareas;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//public class activity_mostrar_tareas extends AppCompatActivity  {
//
//    List<listaTareas> elements;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mostrar_tareas);
//        init();
//    }
//
//    @SuppressLint("Range")
//    private void obtenerDatosDeLaBaseDeDatos() {
//
//        BDTareaApp bdTareaApp = new BDTareaApp(this);
//        final SQLiteDatabase db = bdTareaApp.getWritableDatabase();
//
//        if (db != null) {
//            TextView tituloTmp = (TextView) findViewById(R.id.ViuwTitulo);
//            TextView descripcionTmp = (TextView) findViewById(R.id.ViuwDescripcion);
//            TextView FechaLimiteTmp = (TextView) findViewById(R.id.ViuwFechaLimite);
//
//
//        Cursor ct = db.rawQuery("SELECT titulo_tarea, descripcion, fechaVencimiento FROM tabla ", null );
//            if (ct != null) {
//                ct.moveToFirst();
//                tituloTmp.setText(ct.getString(ct.getColumnIndex("titulo_tarea")).toString());
//                descripcionTmp.setText(ct.getString(ct.getColumnIndex("descripcion")).toString());
//                FechaLimiteTmp.setText(ct.getString(ct.getColumnIndex("fechaVencimiento")).toString());
//            }
//            ct.close();
//            db.close();
//        }
//
////        private List<listaTareas> obtenerDatosDeLaBaseDeDatos() {
////
////            BDTareaApp bdTareaApp = new BDTareaApp(this);
////            final SQLiteDatabase db = bdTareaApp.getWritableDatabase();
//
////        ArrayList<listaTareas> listaTareaarray =new ArrayList<>();
////        listaTareas listaTarea= null;
////        Cursor cursorListaTareas= null;
////
////        cursorListaTareas = db.rawQuery("SELECT * FROM " + COMMENTS_TABLE_CREATE, null );
////
////        if (cursorListaTareas.moveToFirst()){
////            do {
////                listaTarea = new listaTareas();
////                listaTarea.setTitulo_tarea(cursorListaTareas.getString(0));
////                listaTarea.setDescripcion(cursorListaTareas.getString(1));
////                listaTarea.setFechaVencimiento(Date.valueOf(cursorListaTareas.getString(2)));
////                listaTareaarray.add(listaTarea);
////            } while (cursorListaTareas.moveToNext());
////        }
////        cursorListaTareas.close();
////        return listaTareaarray;
//    }
//
//
//    public void init(){
//        elements = new ArrayList<>();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        listaAdaptador listAdapter = new listaAdaptador(elements, this);
//        RecyclerView recyclerView = findViewById(R.id.cardTareas);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(listAdapter);
//
//    }


    public class activity_mostrar_tareas extends AppCompatActivity {
        List<listaTareas> elements;
        listaAdaptador listAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mostrar_tareas);
            init();
        }

        @SuppressLint("Range")
        private void obtenerDatosDeLaBaseDeDatos() {
            BDTareaApp bdTareaApp = new BDTareaApp(this);
            SQLiteDatabase db = bdTareaApp.getWritableDatabase();

            if (db != null) {
                elements = new ArrayList<>();
                TextView tituloTmp = findViewById(R.id.ViuwTitulo);
                TextView descripcionTmp = findViewById(R.id.ViuwDescripcion);
                TextView FechaLimiteTmp = findViewById(R.id.ViuwFechaLimite);

                Cursor ct = db.rawQuery("SELECT titulo_tarea, descripcion, fechaVencimiento FROM tabla", null);
                if (ct.moveToFirst()) {
                    do {
                        tituloTmp.setText(ct.getString(ct.getColumnIndex("titulo_tarea")).toString());
                        descripcionTmp.setText(ct.getString(ct.getColumnIndex("descripcion")).toString());
                        FechaLimiteTmp.setText(ct.getString(ct.getColumnIndex("fechaVencimiento")).toString());
                        listaTareas tarea = new listaTareas(tituloTmp, descripcionTmp, FechaLimiteTmp);
                        elements.add(tarea);
                    } while (ct.moveToNext());
                }
                ct.close();
                db.close();
            }
        }

        public void init() {
            obtenerDatosDeLaBaseDeDatos();
            listAdapter = new listaAdaptador(elements, this);
            RecyclerView recyclerView = findViewById(R.id.cardTareas);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }







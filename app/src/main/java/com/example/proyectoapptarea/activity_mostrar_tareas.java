package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoapptarea.BD.BDTareaApp;
import com.example.proyectoapptarea.adaptador.listaAdaptador;
import com.example.proyectoapptarea.entidades.listaTareas;
import java.util.ArrayList;
import java.util.List;

    public class activity_mostrar_tareas extends AppCompatActivity {
        List<listaTareas> elements;
        listaAdaptador listAdapter;
        EditText editTextSearch;
        Button buttonSearch;
        RecyclerView cardTareas;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            editTextSearch = findViewById(R.id.editTextSearch);
            buttonSearch = findViewById(R.id.buttonSearch);
            cardTareas = findViewById(R.id.cardTareas);
            setContentView(R.layout.activity_mostrar_tareas);
            init();

            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*                    String searchText = editTextSearch.getText().toString();*/
                    Log.d("HOLA", "TEXTO BUSC: ");
                }
            });


        }

        @SuppressLint("Range")
        private void obtenerDatosDeLaBaseDeDatos() {
            BDTareaApp bdTareaApp = new BDTareaApp(this);
            SQLiteDatabase db = bdTareaApp.getWritableDatabase();

            if (db != null) {
                elements = new ArrayList<>();
//                TextView tituloTmp = findViewById(R.id.ViuwTitulo);
//                TextView descripcionTmp = findViewById(R.id.ViuwDescripcion);
//                TextView FechaLimiteTmp = findViewById(R.id.ViuwFechaLimite);

                Cursor ct = db.rawQuery("SELECT titulo_tarea, descripcion, fechaVencimiento FROM Tarea", null);
                if (ct.moveToFirst()) {
                    do {
                        String titulo = ct.getString(ct.getColumnIndex("titulo_tarea"));
                        String descripcion = ct.getString(ct.getColumnIndex("descripcion"));
                        String fechaLimite = ct.getString(ct.getColumnIndex("fechaVencimiento"));
                        listaTareas tarea = new listaTareas(titulo, descripcion, fechaLimite);

                        elements.add(tarea);
                    } while (ct.moveToNext());
                }
                ct.close();
/*                db.close();*/
            }
        }

/*        @SuppressLint("Range")
        private void obtenerDatosDeLaBaseDeDatosBusqueda() {
            BDTareaApp bdTareaApp = new BDTareaApp(this);
            SQLiteDatabase db = bdTareaApp.getWritableDatabase();

            editTextSearch = findViewById(R.id.editTextSearch);
            String nombre = editTextSearch.getText().toString();

            if (db != null) {
                elements = new ArrayList<>();
//                TextView tituloTmp = findViewById(R.id.ViuwTitulo);
//                TextView descripcionTmp = findViewById(R.id.ViuwDescripcion);
//                TextView FechaLimiteTmp = findViewById(R.id.ViuwFechaLimite);

                Cursor ct = db.rawQuery("SELECT * FROM Tarea WHERE titulo_tarea LIKE '%" + nombre + "%'");
                if (ct.moveToFirst()) {
                    do {
                        String titulo = ct.getString(ct.getColumnIndex("titulo_tarea"));
                        String descripcion = ct.getString(ct.getColumnIndex("descripcion"));
                        String fechaLimite = ct.getString(ct.getColumnIndex("fechaVencimiento"));
                        listaTareas tarea = new listaTareas(titulo, descripcion, fechaLimite);

                        elements.add(tarea);
                    } while (ct.moveToNext());
                }
                ct.close();
                //db.close();
            }
        }*/

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








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
import com.example.proyectoapptarea.BD.BdTarea;
import com.example.proyectoapptarea.adaptador.listaAdaptador;
import com.example.proyectoapptarea.entidades.listaTareas;
import java.util.ArrayList;
import java.util.List;

    public class activity_mostrar_tareas extends AppCompatActivity {
        List<listaTareas> elements;
        listaAdaptador listAdapter;
        EditText editTextSearch;
        Button buttonSearch;
        listaTareas tareas;
        String nombre;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.activity_mostrar_tareas);
            super.onCreate(savedInstanceState);
            init();
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








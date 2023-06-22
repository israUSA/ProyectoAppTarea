package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;

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
        setContentView(R.layout.activity_mostrar_tareas);
        cardTareas = findViewById(R.id.cardTareas);

        init();
    }

    @SuppressLint("Range")
    private void obtenerDatosDeLaBaseDeDatos() {
        BDTareaApp bdTareaApp = new BDTareaApp(this);
        SQLiteDatabase db = bdTareaApp.getWritableDatabase();


        if (db != null) {
            elements = new ArrayList<>();

            Cursor ct = db.rawQuery("SELECT titulo_tarea, descripcion, fechaVencimiento,hora FROM Tarea", null);
            if (ct.moveToFirst()) {
                do {
                    String titulo = ct.getString(ct.getColumnIndex("titulo_tarea"));
                    String descripcion = ct.getString(ct.getColumnIndex("descripcion"));
                    String fechaLimite = ct.getString(ct.getColumnIndex("fechaVencimiento"));
                    String hora = ct.getString(ct.getColumnIndex("hora"));
                    listaTareas tarea = new listaTareas(titulo, descripcion, fechaLimite, hora);

                    elements.add(tarea);
                } while (ct.moveToNext());
            }
            ct.close();
            //db.close();
        }
    }

    @SuppressLint("Range")
    private void obtenerDatosDeLaBaseDeDatosBusqueda(String nombreTarea) {
        BDTareaApp bdTareaApp = new BDTareaApp(this);
        SQLiteDatabase db = bdTareaApp.getWritableDatabase();


        if (db != null) {
            String consulta = "SELECT * FROM Tarea WHERE LOWER(titulo_tarea) LIKE '%" + nombreTarea + "%'";
            Cursor ct = db.rawQuery(consulta, null);

            elements.clear(); // Eliminar los elementos anteriores en lugar de crear una nueva instancia

            if (ct.moveToFirst()) {
                do {
                    String titulo = ct.getString(ct.getColumnIndex("titulo_tarea"));
                    String descripcion = ct.getString(ct.getColumnIndex("descripcion"));
                    String fechaLimite = ct.getString(ct.getColumnIndex("fechaVencimiento"));
                    String hora = ct.getString(ct.getColumnIndex("hora"));
                    listaTareas tarea = new listaTareas(titulo, descripcion, fechaLimite, hora);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mostrar_tareas, menu);
        MenuItem searchItem = menu.findItem(R.id.it_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nombreTarea) {
                obtenerDatosDeLaBaseDeDatosBusqueda(nombreTarea);
                listAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)) {
                    // Texto de búsqueda vacío, muestra todos los elementos del RecyclerView
                    obtenerDatosDeLaBaseDeDatosBusqueda("");
                    listAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.it_search) {
            Log.d("Menu", "Buscar");
            return true;
        } else if (itemId == R.id.it_configuraciones) {
            Log.d("Menu", "Configuraciones");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}







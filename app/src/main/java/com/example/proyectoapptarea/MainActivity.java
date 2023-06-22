package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void agregarTarea(View v){
        Intent aggTarea = new Intent(v.getContext(), activity_CreacionTarea.class);
        startActivity(aggTarea);
    }

    public void mostrarTarea(View v){
        Intent mostrarTareas = new Intent(v.getContext(), activity_mostrar_tareas.class);
        startActivity(mostrarTareas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.item_acerda_de) {
            Log.d("Menu", "Acerca De");
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
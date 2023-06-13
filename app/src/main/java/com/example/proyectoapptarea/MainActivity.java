package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
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


}
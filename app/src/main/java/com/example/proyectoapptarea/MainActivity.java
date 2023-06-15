package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyectoapptarea.BD.BDTareaApp;
import com.example.proyectoapptarea.adaptador.listaAdaptador;
import com.example.proyectoapptarea.entidades.listaTareas;

import java.util.ArrayList;
import java.util.List;

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
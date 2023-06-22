package com.example.proyectoapptarea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectoapptarea.BD.BdTarea;
import com.example.proyectoapptarea.entidades.listaTareas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class activity_ver_tarea extends AppCompatActivity {

    EditText txtNombre, txtFecha, txtDescripcion;
    Button btnGuarda, btnEditar, btnCancelar, btnEliminar;
    ImageButton bt_fin_calendario;
    listaTareas tareas;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tarea);

        txtNombre = findViewById(R.id.txtNombre);
        txtFecha = findViewById(R.id.txtFecha);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnEditar = findViewById(R.id.btnModificar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btnEliminar);
        bt_fin_calendario = findViewById(R.id.bt_fin_calendario);

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        BdTarea bdTarea = new BdTarea(activity_ver_tarea.this);
        tareas = bdTarea.verTarea(id);

        if(tareas != null) {
            txtNombre.setText(tareas.getTitulo_tarea());
            txtFecha.setText(tareas.getFechaVencimiento());
            txtDescripcion.setText(tareas.getDescripcion());
            btnGuarda.setVisibility(View.INVISIBLE);
            bt_fin_calendario.setVisibility(View.INVISIBLE);
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtFecha.setInputType(InputType.TYPE_NULL);
            txtDescripcion.setInputType(InputType.TYPE_NULL);
            Log.d("tag", "Quinto if");
        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_ver_tarea.this, activity_editar_tarea.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_ver_tarea.this, activity_mostrar_tareas.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_ver_tarea.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(bdTarea.eliminarTarea(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

    }
    private void lista(){
        Intent intent = new Intent(this, activity_mostrar_tareas.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_notificaciones, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.it_notificaciones) {
            Log.d("Menu", "Notificaciones");
            showDialog();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void showDialog() {
        NotificacionesDialog.display(getSupportFragmentManager());
    }

}
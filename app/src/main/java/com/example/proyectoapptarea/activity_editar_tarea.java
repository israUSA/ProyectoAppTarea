package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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

public class activity_editar_tarea extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombre, txtFecha, txtDescripcion;
    Button btnGuarda, btnEditar, btnCancelar, btnCompletar;
    ImageButton bt_fin_calendario;
    listaTareas tareas;
    int id = 0;
    boolean correcto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tarea);

        txtNombre = findViewById(R.id.txtNombre);
        txtFecha = findViewById(R.id.txtFecha);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnEditar = findViewById(R.id.btnModificar);
        bt_fin_calendario = findViewById(R.id.bt_fin_calendario);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCompletar = findViewById(R.id.btnTareaCompletada);

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

        BdTarea bdTarea = new BdTarea(activity_editar_tarea.this);
        tareas = bdTarea.verTarea(id);

        if(tareas != null) {
            txtNombre.setText(tareas.getTitulo_tarea());
            txtFecha.setText(tareas.getFechaVencimiento());
            txtDescripcion.setText(tareas.getDescripcion());
            btnCompletar.setVisibility(View.INVISIBLE);
            btnEditar.setVisibility(View.INVISIBLE);
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") && !txtFecha.getText().toString().equals("")) {
                    correcto = bdTarea.editarTarea(id, txtNombre.getText().toString(), txtFecha.getText().toString(), txtDescripcion.getText().toString());

                    if(correcto){
                        Toast.makeText(activity_editar_tarea.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(activity_editar_tarea.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity_editar_tarea.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_editar_tarea.this, activity_mostrar_tareas.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        bt_fin_calendario.setOnClickListener(this);


        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();

        // Formatear la fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActualStr = sdf.format(fechaActual);

        // Establecer el texto formateado en el EditText

        // Configurar DatePickerDialog para el EditText de fecha final
        bt_fin_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_editar_tarea.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                // Establecer la fecha mínima como la fecha actual
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, activity_ver_tarea.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == bt_fin_calendario) {
            final Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, dia, mes, ano);
            datePickerDialog.show(); // Agrega esta línea para mostrar el DatePickerDialog
        }

        if (v == bt_fin_calendario) {
            final Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH);
            int ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, dia, mes, ano);
            datePickerDialog.show(); // Agrega esta línea para mostrar el DatePickerDialog
        }
    }
}
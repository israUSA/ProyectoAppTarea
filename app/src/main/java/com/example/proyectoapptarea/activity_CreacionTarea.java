package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

    public class activity_CreacionTarea extends AppCompatActivity implements View.OnClickListener {


        Button btn_guardar;
        EditText editTextDateInicio, editTextDateFinal;

        private int dia, mes, ano;
        ImageButton bt_inicio_calendario, bt_fin_calendario;

        private Calendar calendarFechaActual, calendarFechaFinal;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_creacion_tarea);

            bt_inicio_calendario = findViewById(R.id.bt_inicio_calendario);
            bt_fin_calendario =  findViewById(R.id.bt_fin_calendario);
            editTextDateInicio= findViewById(R.id.editTextDateInicio);
            editTextDateFinal= findViewById(R.id.editTextDateFinal);
            bt_inicio_calendario.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == bt_inicio_calendario) {
                final Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDateInicio.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, dia, mes, ano);
                datePickerDialog.show(); // Agrega esta línea para mostrar el DatePickerDialog
            }
        }
    }

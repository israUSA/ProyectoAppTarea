package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectoapptarea.BD.BDTareaApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

    public class activity_CreacionTarea extends AppCompatActivity implements View.OnClickListener {


        Button btn_guardar;
        EditText editTextDateInicio, editTextDateFinal;

        private int dia, mes, ano;
        ImageButton bt_inicio_calendario, bt_fin_calendario;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_creacion_tarea);

            bt_inicio_calendario = findViewById(R.id.bt_inicio_calendario);
            bt_fin_calendario =  findViewById(R.id.bt_fin_calendario);
            editTextDateInicio= findViewById(R.id.editTextDateInicio);
            editTextDateFinal= findViewById(R.id.editTextDateFinal);
            bt_inicio_calendario.setOnClickListener(this);
            bt_fin_calendario.setOnClickListener(this);

            // Obtener la fecha actual
            Calendar calendar = Calendar.getInstance();
            Date fechaActual = calendar.getTime();

            // Formatear la fecha actual
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActualStr = sdf.format(fechaActual);

            // Establecer el texto formateado en el EditText
            editTextDateInicio.setText(fechaActualStr);

            // Configurar DatePickerDialog para el EditText de fecha final
            bt_fin_calendario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(activity_CreacionTarea.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            editTextDateFinal.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                    // Establecer la fecha mínima como la fecha actual
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

                    datePickerDialog.show();
                }
            });
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

            if (v == bt_fin_calendario) {
                final Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDateFinal.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, dia, mes, ano);
                datePickerDialog.show(); // Agrega esta línea para mostrar el DatePickerDialog
            }
        }


        public void GuardarTarea(View v){
            EditText editTextDateInicio_tmp = (EditText) findViewById(R.id.editTextDateInicio);
            EditText nombre_tarea_tmp = (EditText) findViewById(R.id.nombre_tarea);
            EditText MultiText_Descripcion_tmp = (EditText) findViewById(R.id.MultiText_Descripcion);
            EditText editTextDateFinal_tmp = (EditText) findViewById(R.id.editTextDateFinal);

            BDTareaApp bdTareaApp = new BDTareaApp(v.getContext());
            final SQLiteDatabase db = bdTareaApp.getWritableDatabase();
            if (db!= null){
                //hacemos la operacion agregar
                ContentValues cv = new ContentValues();
                cv.put("fechaCreacion", editTextDateInicio_tmp.getText().toString());
                cv.put("titulo_tarea", nombre_tarea_tmp.getText().toString());
                cv.put("descripcion", MultiText_Descripcion_tmp.getText().toString());
                cv.put("fechaVencimiento", editTextDateFinal_tmp.getText().toString());
                db.insert("Tarea",null,cv);
                Toast.makeText(v.getContext(), "Genial, Tenemos una nueva tarea", Toast.LENGTH_LONG).show();

            }

        }





    }


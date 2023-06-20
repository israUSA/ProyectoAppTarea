package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proyectoapptarea.BD.BDTareaApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

    public class activity_CreacionTarea extends AppCompatActivity implements View.OnClickListener {


        EditText editTextDateInicio, editTextDateFinal,editTextHora;

        ImageButton bt_inicio_calendario,bt_fin_calendario,bt_fin_hora;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_creacion_tarea);

//            bt_inicio_calendario = findViewById(R.id.bt_inicio_calendario);
            bt_fin_calendario =  findViewById(R.id.bt_fin_calendario);
            bt_fin_hora= findViewById(R.id.bt_fin_hora);

            editTextDateInicio= findViewById(R.id.editTextDateInicio);
            editTextDateFinal= findViewById(R.id.editTextDateFinal);
            editTextHora= findViewById(R.id.editTextHora);

//            bt_inicio_calendario.setOnClickListener(this);
            bt_fin_calendario.setOnClickListener(this);
            bt_fin_hora.setOnClickListener(this);

            EditText editTextDateInicio = findViewById(R.id.editTextDateInicio);
            editTextDateInicio.setEnabled(false);

            // Obtener la fecha actual
            Calendar calendar = Calendar.getInstance();
            Date fechaActual = calendar.getTime();

            // Formatear la fecha actual
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
//            if (v == bt_inicio_calendario) {
//                final Calendar c = Calendar.getInstance();
//                int dia = c.get(Calendar.DAY_OF_MONTH);
//                int mes = c.get(Calendar.MONTH);
//                int ano = c.get(Calendar.YEAR);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        editTextDateInicio.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                    }
//                }, dia, mes, ano);
//                datePickerDialog.show(); // Agrega esta línea para mostrar el DatePickerDialog
//            }

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

            if (v == bt_fin_hora) {
                final Calendar c = Calendar.getInstance();
                int hora = c.get(Calendar.HOUR_OF_DAY);
                int minutos = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editTextHora.setText(hourOfDay+":"+minute);
                    }
                }, hora,minutos,false);
                timePickerDialog.show();

            }
        }


        public void GuardarTarea(View v){
            EditText editTextDateInicio_tmp = (EditText) findViewById(R.id.editTextDateInicio);
            EditText nombre_tarea_tmp = (EditText) findViewById(R.id.nombre_tarea);
            EditText MultiText_Descripcion_tmp = (EditText) findViewById(R.id.MultiText_Descripcion);
            EditText editTextDateFinal_tmp = (EditText) findViewById(R.id.editTextDateFinal);
            EditText editTextHora_tmp = (EditText) findViewById(R.id.editTextHora);

            String fechaInicio = editTextDateInicio_tmp.getText().toString().trim();
            String nombreTarea = nombre_tarea_tmp.getText().toString().trim();
            String descripcion = MultiText_Descripcion_tmp.getText().toString().trim();
            String fechaFinal = editTextDateFinal_tmp.getText().toString().trim();
            String horaFinal = editTextHora_tmp.getText().toString().trim();

            if (fechaInicio.isEmpty()) {
                editTextDateInicio_tmp.setError("Ingrese la fecha de inicio");
                return;
            }

            if (nombreTarea.isEmpty()) {
                nombre_tarea_tmp.setError("Ingrese el nombre de la tarea");
                return;
            }

            if (descripcion.isEmpty()) {
                MultiText_Descripcion_tmp.setError("Ingrese la descripción de la tarea");
                return;
            }

            if (fechaFinal.isEmpty()) {
                editTextDateFinal_tmp.setError("Ingrese la fecha final");
                return;
            }

            try {
                BDTareaApp bdTareaApp = new BDTareaApp(v.getContext());
                final SQLiteDatabase db = bdTareaApp.getWritableDatabase();

/*                String sentenciaSql = "CREATE TABLE Tarea (id INTEGER PRIMARY KEY AUTOINCREMENT, fechaCreacion DATETIME, titulo_tarea TEXT, descripcion TEXT, fechaVencimiento DATETIME, hora TIME, prioridad INTEGER, completada INTEGER DEFAULT 0)";
                db.execSQL(sentenciaSql);*/

                if (db!= null){
                    //hacemos la operacion agregar
                    ContentValues cv = new ContentValues();
                    cv.put("fechaCreacion", editTextDateInicio_tmp.getText().toString());
                    cv.put("titulo_tarea", nombre_tarea_tmp.getText().toString());
                    cv.put("descripcion", MultiText_Descripcion_tmp.getText().toString());
                    cv.put("fechaVencimiento", editTextDateFinal_tmp.getText().toString());
                    cv.put("hora", editTextHora_tmp.getText().toString());
                    db.insert("Tarea",null,cv);
                    Toast.makeText(v.getContext(), "Genial, Tenemos una nueva tarea", Toast.LENGTH_LONG).show();
                }
                //db.close();
                Intent regreso = new Intent(v.getContext(), MainActivity.class);
                startActivity(regreso);
            } catch (Exception ex){
                Toast.makeText(v.getContext(), "Ups, algo ocurrio..", Toast.LENGTH_LONG).show();
            }
        }

        public void cerrarVentana(View v){
            Intent regreso = new Intent(v.getContext(), MainActivity.class);
            startActivity(regreso);
        }

    }


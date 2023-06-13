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

public class activity_mostrar_tareas extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_tarea);

    }
}






//                @Override
//                public void onDateSet(DatePicker view, int dayOfMonth, int monthOfYear, int year) {
//                    editTextDateInicio.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                }
//            }, dia, mes, ano);
//            datePickerDialog.show();


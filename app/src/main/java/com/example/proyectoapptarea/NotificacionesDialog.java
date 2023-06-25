package com.example.proyectoapptarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;

import com.example.proyectoapptarea.BD.BDTareaApp;
import com.example.proyectoapptarea.NotificacionReceiver;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NotificacionesDialog extends DialogFragment {

    private Toolbar toolbar;
    private int ID;
    private String nombreTarea;
    private String descripcionTarea;
    private List<Integer> chipGroupIds;
    private Calendar calendario;
    private Date fechaActual;
    private Date fechaFinalizacion;
    private long diferenciaTiempo;
    private long minutosRestantes;
    public static final String TAG = "Intervalo Recordatorios";


    public static NotificacionesDialog display(FragmentManager fragmentManager, int ID, String nombreTarea, String descripcionTarea) {
        NotificacionesDialog exampleDialog = new NotificacionesDialog();
        exampleDialog.show(fragmentManager, TAG);
        exampleDialog.ID = ID;
        exampleDialog.nombreTarea = nombreTarea;
        exampleDialog.descripcionTarea = descripcionTarea;
        return exampleDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        crearCanal();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_notificaciones_dialog, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        chipGroupIds = Arrays.asList(R.id.chipGroup1, R.id.chipGroup2, R.id.chipGroup3, R.id.chipGroup4);
        calendario  = Calendar.getInstance();
        fechaActual = calendario.getTime();
        fechaFinalizacion = getFechaFinalizacion();
        diferenciaTiempo = fechaFinalizacion.getTime() - fechaActual.getTime();
        minutosRestantes = TimeUnit.MILLISECONDS.toMinutes(diferenciaTiempo);

        for (int chipGroupId : chipGroupIds) {
            ChipGroup chipGroup = view.findViewById(chipGroupId);
            int chipCount = chipGroup.getChildCount();
            for (int i = 0; i < chipCount; i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                chip.setOnCheckedChangeListener(chipCheckedChangeListener);
            }
        }

        //Inactiva chips que superen con su rango la finalizacion de la tarea
        for (int i = 0; i < chipGroupIds.size(); i++) {
            ChipGroup chipGroup = view.findViewById(chipGroupIds.get(i));

            for (int j = 0; j < chipGroup.getChildCount(); j++) {
                Chip chip = (Chip) chipGroup.getChildAt(j);
                int minutosChip = obtenerMinutosDesdeChip(chip);

                if (minutosChip <= minutosRestantes) {
                    chip.setEnabled(true);
                } else {
                    chip.setEnabled(false);
                }
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle("Recordatorios");
        toolbar.inflateMenu(R.menu.menu_dialog_notificaciones);
        toolbar.setOnMenuItemClickListener(item -> {
            dismiss();
            return true;
        });
    }

    //Agrega a cada chip de cada groupchip un listener
    private CompoundButton.OnCheckedChangeListener chipCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                for (int chipGroupId : chipGroupIds) {
                    ChipGroup chipGroup = getView().findViewById(chipGroupId);
                    int chipCount = chipGroup.getChildCount();
                    for (int i = 0; i < chipCount; i++) {
                        Chip chip = (Chip) chipGroup.getChildAt(i);
                        if (chip != buttonView) {
                            chip.setOnCheckedChangeListener(null);
                            chip.setChecked(false);
                            chip.setOnCheckedChangeListener(chipCheckedChangeListener);
                        }
                    }
                }

                int minutosIntervalo = obtenerMinutosDesdeChip((Chip) buttonView);
                programarNotificacion(minutosIntervalo);
            }
        }
    };

    //Obtiene la fecha de finalizacion de la tarea por medio de una consulta a la BD
    private Date getFechaFinalizacion(){
        BDTareaApp dbHelper = new BDTareaApp(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Date fechaFinalizacion = null;

        Cursor cursor = db.rawQuery("SELECT fechaVencimiento, hora FROM Tarea WHERE id = ?", new String[]{String.valueOf(ID)});
            if (cursor.moveToFirst()) {
                String fechaVencimiento = cursor.getString(cursor.getColumnIndex("fechaVencimiento"));
                String hora = cursor.getString(cursor.getColumnIndex("hora"));

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String fechaHoraString = fechaVencimiento + " " + hora;

                try {
                    fechaFinalizacion = dateFormat.parse(fechaHoraString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        cursor.close();
        db.close();

        return fechaFinalizacion;
    }

    private int obtenerMinutosDesdeChip(Chip chip) {
        String textoChip = chip.getText().toString();
        int minutos = 0;

        // Verifica si el texto del chip contiene la palabra "minutos o minuto"
        if (textoChip.contains("minutos") || textoChip.contains("minuto")) {
            minutos = extraerCantidad(textoChip, "minutos");
            Log.d("MINUTOS DE MINUTOS", String.valueOf(minutos));
        }
        // Verifica si el texto del chip contiene la palabra "hora"
        else if (textoChip.contains("hora")) {
            minutos = extraerCantidad(textoChip, "hora") * 60;
            Log.d("MINUTOS DE HORAS", String.valueOf(minutos));
        }
        // Verifica si el texto del chip contiene la palabra "día" o "dias"
        else if (textoChip.contains("dia") || textoChip.contains("dias")) {
            minutos = extraerCantidad(textoChip, "día") * 24 * 60;
            Log.d("MINUTOS DE DIAS", String.valueOf(minutos));
        }
        // Verifica si el texto del chip es "mensual"
        else if (textoChip.equals("mensual")) {
            minutos = 30 * 24 * 60; //Mes de 30 días
            Log.d("MINUTOS DE MENSUAL", String.valueOf(minutos));
        }
        // Verifica si el texto del chip es "trimestral"
        else if (textoChip.equals("trimestral")) {
            minutos = 3 * 30 * 24 * 60;
            Log.d("MINUTOS DE TRIMESTRAL", String.valueOf(minutos));
        }
        // Verifica si el texto del chip es "anual"
        else if (textoChip.equals("anual")) {
            minutos = 365 * 24 * 60; // año de 365 días
            Log.d("MINUTOS DE AÑO", String.valueOf(minutos));
        }

        return minutos;
    }

    private int extraerCantidad(String texto, String unidad) {
        String[] partes = texto.split(" ");
        String cantidadTexto = partes[0];

        int cantidad = 0;

        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return cantidad;
    }

    private void programarNotificacion(int minutosIntervalo) {
        Log.d("MINUTOS INTERVALO", String.valueOf(minutosIntervalo));

        Context context = requireContext();

        Intent intent = new Intent(context, NotificacionReceiver.class);
        intent.putExtra("ContentTitle", nombreTarea);
        intent.putExtra("ContentText", descripcionTarea);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Calcula el tiempo en milisegundos para el intervalo seleccionado
        Log.d("ANTES", "aqui antes");
        long intervaloMillis = minutosIntervalo * 60 * 1000;
        Log.d("MINUTOS INTERVALO", String.valueOf(minutosIntervalo));
        Log.d("INTERVALOMILLIS", String.valueOf(intervaloMillis));

        // Calcula el tiempo de inicio de la notificaciónn
        long tiempoInicioMillis = Calendar.getInstance().getTimeInMillis() + intervaloMillis;


        // Programa la notificación repetitiva utilizando AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tiempoInicioMillis, intervaloMillis, pendingIntent);
        Log.d("TIEMPO DE INICIO", String.valueOf(tiempoInicioMillis));
        Log.d("NOTIFICACION", "NOTIFICACION PUESTA EN MARCHA");
    }

    //Canal donde iran las notificaciones
    private void crearCanal(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence nombreCanal = "Nombre del Canal";
            String descripcionCanal = "Canal para las alarmas";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel canal = new NotificationChannel("canal_alarma", nombreCanal, importancia);
            canal.setDescription(descripcionCanal);

            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(canal);
            Log.d("CANAL", "CANAL CREADO");
        }
    }

}
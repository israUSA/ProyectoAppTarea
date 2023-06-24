package com.example.proyectoapptarea.adaptador;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.proyectoapptarea.R;

public class NotificacionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // Aquí puedes implementar la lógica para mostrar la notificación
        // cuando se reciba la transmisión de la alarma.

        // Por ejemplo, puedes crear una notificación utilizando NotificationCompat.Builder:
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Título de la notificación")
                .setContentText("Contenido de la notificación")
                .setSmallIcon(R.drawable.sharp_access_alarm_24);

        // Aquí puedes configurar cualquier otra opción de la notificación, como el sonido, vibración, etc.

        // Obtén el administrador de notificaciones y muestra la notificación
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}

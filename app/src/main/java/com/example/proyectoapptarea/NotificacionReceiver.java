package com.example.proyectoapptarea;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;


import com.example.proyectoapptarea.R;

public class NotificacionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RECEIVER", "EJECUCION DE RECEIVER");

        String contentTitle = intent.getStringExtra("ContentTitle");
        String contentText = intent.getStringExtra("ContentText");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "canal_alarma")
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.sharp_access_alarm_24);

        // Obtiene el administrador de notificaciones y muestra la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
        Log.d("RECEIVER", "TERMINACION DE RECEIVER");
    }
}

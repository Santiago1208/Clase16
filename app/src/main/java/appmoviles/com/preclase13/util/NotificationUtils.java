package appmoviles.com.preclase13.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import appmoviles.com.preclase13.R;
import appmoviles.com.preclase13.app.AlbumApp;

public class NotificationUtils {

    public static final String CHANNEL_ID = "App192";
    public static final String CHANNEL_NAME = "Comentarios";
    public static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;

    public static void createNotification(int id, String title, String msg) {
        NotificationManager manager;
        manager = (NotificationManager) AlbumApp.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
            manager.createNotificationChannel(canal);
        }
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(AlbumApp.getAppContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        manager.notify(id, builder.build());
    }

}

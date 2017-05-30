package diogoferreira.positioningsystem;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;


public class Notifications extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int position_item = Database.item;
        Thread thread = new Thread(new ThreadClass(startId, position_item));
        thread.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ThreadClass implements Runnable{
        int service_id;
        int position_item;

        ThreadClass(int service_id, int position_item){
            this.service_id = service_id;
            this.position_item = position_item;
        }

        @Override
        public void run() {
            synchronized (this) {
                Vibrator v = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(400);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                mBuilder.setSmallIcon(R.mipmap.inescidwhite);
                mBuilder.setContentTitle("New item found!");
                mBuilder.setContentText("at (" + Database.position[0] + ", " + Database.position[1]+")");


                Intent resultIntent = new Intent(getApplicationContext(), Item.class);
                resultIntent.putExtra("position", position_item);

                PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int notificationID = 2;

                // notificationID allows you to update the notification later on.
                mNotificationManager.notify(notificationID, mBuilder.build());

                /*MediaPlayer alarm_sound = new MediaPlayer();
                // som do alarm put na pasta raw dos recursos
                alarm_sound = MediaPlayer.create(getApplicationContext(), R.raw.alarm_sound);
                alarm_sound.start();*/

                stopSelf(service_id);
            }
        }
    }
}

package diogoferreira.positioningsystem;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;


public class BackEndConnection extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int option = intent.getIntExtra("OPTION",1);
        Thread thread = new Thread(new BackEndConnection.ThreadClass(startId, option));
        thread.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ThreadClass implements Runnable {

        int service_id;
        int option;

        ThreadClass(int service_id, int option) {
            this.service_id = service_id;
            this.option = option;
        }

        @Override
        public void run() {
            synchronized (this) {
                if (isNetworkAvailable(getApplicationContext())) {
                    if(option==1){//Get version from Server, compare if lower -> download
                        Client new_client = new Client();
                        Database.recent_version_number = new_client.connect_getVersion("192.168.1.3", 2000);
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BackEndConnection.this);
                        localBroadcastManager.sendBroadcast(new Intent("action.compare_version"));
                    }else{
                        Client new_client = new Client();
                        new_client.connect_sendHistory("192.168.1.3", 2000);
                    }
                }
                stopSelf();
            }
        }

        private boolean isNetworkAvailable(final Context context) {
            final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }

    }
}
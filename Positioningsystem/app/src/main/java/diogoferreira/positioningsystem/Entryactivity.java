package diogoferreira.positioningsystem;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ListIterator;

public class Entryactivity extends AppCompatActivity {

    private boolean firstscan = false;
    private static boolean firsttime = true;

    ImageView itemslist;
    ImageView map;

    TextView textmap;
    TextView textitems;

    //Bluetooth/BLEScanning
    private BluetoothManager btManager;
    private BluetoothAdapter btAdapter;
    private BluetoothLeScanner mLeScanner;
    private Handler scanHandler = new Handler();
    private int scan_interval_ms = 1000;
    private boolean isScanning = false;

    //Position calculation
    private Handler positionHandler = new Handler();
    private int position_calculation_interval = 1000;

    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("action.compare_version")){
                if (Integer.parseInt(Database.recent_version_number) > getVersionCode(getApplicationContext())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Entryactivity.this);
                    builder.setTitle("Update Available");
                    builder.setMessage("This app won't run unless you update it.");
                    builder.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://137.74.194.152/appuptodate/appuptodate.apk"));
                            startActivity(updateIntent);
                        }
                    });
                    builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {//finish app
                            finish();
                        }
                    });
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.show();
                }
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        /*
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            try{
                // this will create a new name everytime and unique
                File root = new File(Environment.getExternalStorageDirectory(),"ThesisData");
                // if external memory exists and folder with name Notes
                if (!root.exists()) {
                    root.mkdirs(); // this will create folder.
                }
                File filepath = new File(root, "Experiment.txt");  // file path to save
                FileWriter writer = new FileWriter(filepath);
                ListIterator<double[]> listIterator = Database.rssihistory.listIterator();
                while (listIterator.hasNext()) {
                    double[] temp = listIterator.next();
                    String text = temp[0]+", "+temp[1]+", "+temp[2]+", "+temp[3]+", "+temp[4]+", "+temp[5]+", "+temp[6]+", "+temp[7]+", "+temp[8]+", "+temp[9]+", "+temp[10];
                    writer.append(text+"\n");
                    writer.flush();
                }
                writer.close();

            }catch( IOException e){
                e.printStackTrace();
            }
        }*/

        //Check if position history has values
        //if(!Database.positionhistory.isEmpty()){
            double[] temp = new double[3];
            temp[0]= 2.3;
            temp[0]= 2.3;
            temp[0]= 23564645;
            Database.positionhistory.add(temp);
            Intent intent =  new Intent(this, BackEndConnection.class);
            intent.putExtra("OPTION",2);
            startService(intent);
        //}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryactivity);

        itemslist = (ImageView)(findViewById(R.id.itemslist));
        itemslist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Listitems.class);
                startActivity(intent);

            }
        });

        textitems = (TextView) (findViewById(R.id.textView));
        textitems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Listitems.class);
                startActivity(intent);

            }
        });

        map = (ImageView)(findViewById(R.id.maps));
        map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Map.class);
                startActivity(intent);

            }
        });

        textmap = (TextView) (findViewById(R.id.textView2));
        textmap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Map.class);
                startActivity(intent);

            }
        });

        // init BLE
        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        if (btAdapter !=null){
            if (!btAdapter.isEnabled()) {
                btAdapter.enable();
            }
        }else{
            finish();
        }

        //Receive intent backendconnection service to compare versions and display the alert
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("action.compare_version");
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, mIntentFilter);

        //Get version from the server and update
        Intent intent =  new Intent(this, BackEndConnection.class);
        intent.putExtra("OPTION",1);
        startService(intent);

        if(firsttime){
            scanHandler.post(scanRunnable);
            firsttime = false;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    //------------------------------------------
    //             Position Calculation
    //------------------------------------------

    private Runnable positionCalculus = new Runnable()
    {
        @Override
        public void run() {
            /*
            double[] rsshistory = new double[11];
            rsshistory[0]=Database.timerssireceived[0];
            rsshistory[1]=Database.timerssireceived[1];
            rsshistory[2]=Database.timerssireceived[2];
            rsshistory[3]=Database.timerssireceived[3];
            rsshistory[4]=Database.timerssireceived[4];
            rsshistory[5]=Database.timerssireceived[5];
            rsshistory[6]=Database.timerssireceived[6];
            rsshistory[7]=Database.timerssireceived[7];
            rsshistory[8]=Database.timerssireceived[8];
            rsshistory[9]=Database.timerssireceived[9];
            rsshistory[10]=(double) System.currentTimeMillis();
            Database.rssihistory.addLast(rsshistory);
            */

            //Fingerprinting
            Database.fingerprint.setRssi(Database.timerssireceived);
            double[] eposition = Database.ksolver.estimatePosition();

            //Kalman Filter
            double[] kposition = Database.tracking.calculatePosition(eposition[0],eposition[1]);

            Database.position = kposition;
            double[] history = new double[3];
            history[0]=kposition[0];
            history[1]=kposition[1];
            history[2]=(double) System.currentTimeMillis();
            Database.positionhistory.addLast(history);

            //Se a posicao for interessante entao meter o int no item e lancar a notificacao
            //so da notificacao se for a primeira vez nesta posicao deps ja na da mais
            if(Database.position[1] == 0.8){
                Database.item = 2;
                Intent intent =  new Intent(Entryactivity.this, Notifications.class);
                startService(intent);
            }

            Database.timerssireceived = new double[Database.number_beacons];
            Database.timenumberbeacons = new int[Database.number_beacons];

            Database.positioncalculated = true; //Only after first calculation display myPaint in Map
            positionHandler.postDelayed(this, position_calculation_interval);
        }
    };

    //------------------------------------------
    //              Beacons Scanning
    //------------------------------------------

    private Runnable scanRunnable = new Runnable()
    {
        @Override
        public void run() {
            if (isScanning) {
                if (btAdapter != null) {
                    btAdapter.stopLeScan(leScanCallback);
                }
            } else {
                if (btAdapter != null) {
                    btAdapter.startLeScan(leScanCallback);
                }
            }

            isScanning = !isScanning;

            scanHandler.postDelayed(this, scan_interval_ms);
        }
    };


    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback()
    {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord)
        {
            int startByte = 2;
            boolean patternFound = false;
            while (startByte <= 5)
            {
                if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an iBeacon
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15)
                { //Identifies correct data length
                    patternFound = true;
                    break;
                }
                startByte++;
            }

            if (patternFound)
            {
                //Convert to hex String
                /*byte[] uuidBytes = new byte[16];
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                String hexString = bytesToHex(uuidBytes);

                //UUID detection
                String uuid =  hexString.substring(0,8) + "-" +
                        hexString.substring(8,12) + "-" +
                        hexString.substring(12,16) + "-" +
                        hexString.substring(16,20) + "-" +
                        hexString.substring(20,32);*/

                // major
                int major = (scanRecord[startByte + 20] & 0xff) * 0x100 + (scanRecord[startByte + 21] & 0xff);

                // minor
                int minor = (scanRecord[startByte + 22] & 0xff) * 0x100 + (scanRecord[startByte + 23] & 0xff);

                //meausured power
                //int measuredpower = (scanRecord[startByte + 24]);

                //Beacon beacon = new Beacon(major, minor, uuid, measuredpower);
                //BeaconDetected beacondetected = new BeaconDetected(beacon, System.currentTimeMillis(), rssi);
                if(major==1 &&  minor>0 && minor<11) {
                    Database.latestrssireceived[minor - 1] = rssi;
                    Database.timerssireceived[minor - 1] = (rssi + Database.timerssireceived[minor - 1] * Database.timenumberbeacons[minor - 1]) / Database.timenumberbeacons[minor - 1] + 1;
                    Database.timenumberbeacons[minor - 1]++;

                    if (!firstscan) {
                        positionHandler.postDelayed(positionCalculus, position_calculation_interval);
                        firstscan = true;
                    }
                }
            }
        }
    };

    /*
    // Conversion method from bytes to hexadecimal
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }*/

    private int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {

        }
        return 0;
    }
}
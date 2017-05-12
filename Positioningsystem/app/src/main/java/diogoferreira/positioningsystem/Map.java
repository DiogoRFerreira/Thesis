package diogoferreira.positioningsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class Map extends AppCompatActivity {

    CustomMap map;

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent =  new Intent(this, BackEndConnection.class);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = (CustomMap) findViewById(R.id.custommap);
    }
}

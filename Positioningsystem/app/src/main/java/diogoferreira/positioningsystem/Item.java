package diogoferreira.positioningsystem;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


public class Item extends AppCompatActivity {

    String[] titles;
    String[] description;
    int[] imgs = {R.drawable.wc, R.drawable.students, R.drawable.poster, R.drawable.computersv, R.drawable.first};

    ImageView image;
    TextView text;

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent =  new Intent(this, BackEndConnection.class);
        startService(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        //String position = intent.getStringExtra("position");
        int position = intent.getIntExtra("position",0);
        Resources res = getResources();

        titles = res.getStringArray(R.array.titles);
        description = res.getStringArray(R.array.description);

        image = (ImageView)(findViewById(R.id.imageView));
        image.setImageResource(imgs[position]);
        text = (TextView)(findViewById(R.id.textView));
        text.setText(description[position]);

    }

}

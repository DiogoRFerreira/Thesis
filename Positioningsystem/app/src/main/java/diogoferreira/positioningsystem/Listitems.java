package diogoferreira.positioningsystem;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import static diogoferreira.positioningsystem.R.*;


public class Listitems extends AppCompatActivity {

    ListView list;
    String[] titles;
    String[] description;
    int[] imgs =  {drawable.wc, drawable.students, drawable.poster, drawable.computersv, drawable.first};

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent =  new Intent(this, BackEndConnection.class);
        startService(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_listitems);

        Resources res = getResources();

        titles = res.getStringArray(array.titles);
        description = res.getStringArray(array.description);

        list = (ListView) findViewById(id.list1);
        MyAdapter adapter = new MyAdapter(this,titles,imgs,description);
        list.setAdapter(adapter);

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        int[] imgs;
        String myTitles[];
        String myDescription[];

        MyAdapter(Context c, String[] titles, int[] imgs, String[] description ){
            super(c, layout.row, id.text1,titles);
            this.context = c;
            this.imgs = imgs;
            this.myDescription = description;
            this.myTitles = titles;
        }



        @Override
        public View getView(final int position, View convertView , ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView images = (ImageView) row.findViewById(R.id.icon);
            TextView myTitle = (TextView) row.findViewById(R.id.text1);
            TextView myDescription = (TextView) row.findViewById(R.id.text2);
            images.setImageResource(imgs[position]);
            myTitle.setText(titles[position]);
            myDescription.setText(description[position]);

            row.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Item.class);
                    Log.d("message"," "+position);
                    intent.putExtra("position",position);
                    startActivity(intent);

                }
            });

            return row;
        }
    }
}

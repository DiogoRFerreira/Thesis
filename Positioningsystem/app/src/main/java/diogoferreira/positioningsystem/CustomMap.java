package diogoferreira.positioningsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;



public class CustomMap extends View {

    Paint myPaint;
    Bitmap image;
    float x,y;

    //Tamanho real do mapa - Mapa de Casa
    //double ymax = 6.20;
    //double xmax = 6.50;

    double ymax = 6.20;
    double xmax = 6.50;

    public CustomMap(Context context) {
        super(context);
        init(context);
    }

    public CustomMap(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public CustomMap(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context){
        myPaint = new Paint();
        myPaint.setColor(Color.RED);
        myPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap temp;

        //temp = BitmapFactory.decodeResource(getResources(), R.drawable.mapa); - Mapa de Casa

        temp = BitmapFactory.decodeResource(getResources(),R.drawable.mapa_v5);
        image = Bitmap.createScaledBitmap(temp, canvas.getWidth(), canvas.getHeight(),true);
        canvas.drawBitmap(image, 0, 0, null);

        //Só se tiver posição - colocar if
        //mudar o eixo tá trocado
        if (Database.positioncalculated) {
            x = (float) ((Database.position[0] / xmax) * canvas.getWidth());
            y = (float) (((ymax - Database.position[1]) / ymax) * canvas.getHeight());
            canvas.drawCircle(x, y, 8.0f * 2, myPaint);
        }

        invalidate();
    }
}

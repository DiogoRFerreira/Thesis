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

    double ymax = 11.4;
    double xmax = 9.0;

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
        myPaint.setColor(Color.GREEN);
        myPaint.setStrokeWidth(8);
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
        /*
        if (Database.positioncalculated) {
            //x = (float) ((Database.position[0] / xmax) * canvas.getWidth());
            //y = (float) (((ymax - Database.position[1]) / ymax) * canvas.getHeight());
            x = (float) ((4 / xmax) * canvas.getWidth());
            y = (float) (((ymax - 6) / ymax) * canvas.getHeight());
            canvas.drawCircle(x, y, 8.0f * 2, myPaint);
        }
        */

        //WKNN
        /*
        canvas.drawCircle((float) ((5.4 / xmax) * canvas.getWidth()), (float) (((ymax - 7.82) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.4 / xmax) * canvas.getWidth()), (float) (((ymax - 7.68) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.4 / xmax) * canvas.getWidth()), (float) (((ymax - 8.00) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.4 / xmax) * canvas.getWidth()), (float) (((ymax - 7.94) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((4.9/ xmax) * canvas.getWidth()), (float) (((ymax - 8.4) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((6.0 / xmax) * canvas.getWidth()), (float) (((ymax - 8.4) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.46 / xmax) * canvas.getWidth()), (float) (((ymax - 8.4) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.7 / xmax) * canvas.getWidth()), (float) (((ymax - 8.4) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.34/ xmax) * canvas.getWidth()), (float) (((ymax - 7.2) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.77 / xmax) * canvas.getWidth()), (float) (((ymax - 7.2) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((4.29 / xmax) * canvas.getWidth()), (float) (((ymax - 7.2) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.81 / xmax) * canvas.getWidth()), (float) (((ymax - 4.55) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.48 / xmax) * canvas.getWidth()), (float) (((ymax - 1.80) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.0 / xmax) * canvas.getWidth()), (float) (((ymax - 2.2) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((1.6 / xmax) * canvas.getWidth()), (float) (((ymax - 1.6) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.33 / xmax) * canvas.getWidth()), (float) (((ymax - 2.72) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.25 / xmax) * canvas.getWidth()), (float) (((ymax - 2.49) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.28 / xmax) * canvas.getWidth()), (float) (((ymax - 4.8) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.0 / xmax) * canvas.getWidth()), (float) (((ymax - 3.74) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.33 / xmax) * canvas.getWidth()), (float) (((ymax - 2.6) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((1.6 / xmax) * canvas.getWidth()), (float) (((ymax - 2.16) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((1.6 / xmax) * canvas.getWidth()), (float) (((ymax - 2.25) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.1 / xmax) * canvas.getWidth()), (float) (((ymax - 2.27) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((1.9 / xmax) * canvas.getWidth()), (float) (((ymax - 1.94) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        */
        //KF
        canvas.drawCircle((float) ((5.34 / xmax) * canvas.getWidth()), (float) (((ymax - 7.749) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.37 / xmax) * canvas.getWidth()), (float) (((ymax - 7.714) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.38 / xmax) * canvas.getWidth()), (float) (((ymax - 7.827) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.38 / xmax) * canvas.getWidth()), (float) (((ymax - 7.86) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.24 / xmax) * canvas.getWidth()), (float) (((ymax - 8.02) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((5.46 / xmax) * canvas.getWidth()), (float) (((ymax - 8.13) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((4.91 / xmax) * canvas.getWidth()), (float) (((ymax - 8.206) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((4.56 / xmax) * canvas.getWidth()), (float) (((ymax - 8.259) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((4.32 / xmax) * canvas.getWidth()), (float) (((ymax - 7.97) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((4.06 / xmax) * canvas.getWidth()), (float) (((ymax - 7.76) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.98 / xmax) * canvas.getWidth()), (float) (((ymax - 7.60) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((4.06 / xmax) * canvas.getWidth()), (float) (((ymax - 6.782) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.72 / xmax) * canvas.getWidth()), (float) (((ymax - 5.43) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.39 / xmax) * canvas.getWidth()), (float) (((ymax - 4.56) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((3.12 / xmax) * canvas.getWidth()), (float) (((ymax - 3.76) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.82 / xmax) * canvas.getWidth()), (float) (((ymax - 3.48) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.495 / xmax) * canvas.getWidth()), (float) (((ymax - 3.401) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.70 / xmax) * canvas.getWidth()), (float) (((ymax - 3.779) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.589 / xmax) * canvas.getWidth()), (float) (((ymax - 3.83) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.451 / xmax) * canvas.getWidth()), (float) (((ymax - 3.81) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.221 / xmax) * canvas.getWidth()), (float) (((ymax - 3.48) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.529 / xmax) * canvas.getWidth()), (float) (((ymax - 3.25) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.27 / xmax) * canvas.getWidth()), (float) (((ymax - 2.95) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.095 / xmax) * canvas.getWidth()), (float) (((ymax - 2.76) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.12 / xmax) * canvas.getWidth()), (float) (((ymax - 2.63) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);
        canvas.drawCircle((float) ((2.06 / xmax) * canvas.getWidth()), (float) (((ymax - 2.449) / ymax) * canvas.getHeight()), 8.0f * 2, myPaint);


        invalidate();
    }
}

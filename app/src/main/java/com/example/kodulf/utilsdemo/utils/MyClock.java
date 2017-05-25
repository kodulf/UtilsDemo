package com.example.kodulf.utilsdemo.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kodulf on 2017/5/25.
 */

public class MyClock extends View {
    Paint mPaint;
    int width;
    int height;
    private int radius;
    private int gap;
    private Paint timePaint;

    public MyClock(Context context) {
        //super(context);
        this(context,null);
    }

    public MyClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.reset();
        mPaint.setColor(Color.RED);
        //一定要设置为描边的样式，不然就是一整个了
        mPaint.setStyle(Paint.Style.STROKE);//设置描边
        mPaint.setStrokeWidth(2);//设置描边线的粗细
        mPaint.setAntiAlias(true);//设置抗锯齿，使圆形更加圆滑

        timePaint = new Paint();
        timePaint.reset();
        timePaint.setColor(Color.BLACK);
        timePaint.setAntiAlias(true);//设置抗锯齿，使圆形更加圆滑
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        int centerY = height / 2;
        int centerX = width / 2;
        radius = width / 3;
        gap = width /10;

        drawCircle(canvas, centerY, centerX);
        drawNumbers(canvas, centerY, centerX);


        //还是从3开始画
        for (int i = 0; i < 12; i++) {
            //需要注意sin，cos 里面的是double 类型的
            int length = (int)((radius) * Math.cos((Math.PI*i * 30)/180));
            int height = (int)((radius) * Math.sin((Math.PI*i * 30)/180));
            Log.d("kodulf","length="+length);
            Log.d("kodulf","width="+width);

            canvas.drawLine(centerX+length,centerY-height,centerX,centerY,timePaint);

        }


    }

    /**
     * 画出数字
     * @param canvas
     * @param centerY
     * @param centerX
     */
    private void drawNumbers(Canvas canvas, int centerY, int centerX) {
        mPaint.setTextSize(gap);//设置字体的大小
        mPaint.setTextAlign(Paint.Align.CENTER);

        //从3开始画，3，2，1，12，11，10，9，8，7，6，5，4，这样划过来
        for (int i = 0; i <12; i++) {
            //需要注意sin，cos 里面的是double 类型的
            int length = (int)((radius+gap) * Math.cos((Math.PI*i * 30)/180));
            int height = (int)((radius+gap) * Math.sin((Math.PI*i * 30)/180));
            Log.d("kodulf","length="+length);
            Log.d("kodulf","width="+width);

            int num = (15-i)%12;

            if(num==0){
                num = 12;
            }

            canvas.drawText(num+"",centerX+length,centerY-height,mPaint);

        }
    }

    private void drawCircle(Canvas canvas, int centerY, int centerX) {

        canvas.drawCircle(centerX, centerY, radius, mPaint);
        canvas.drawCircle(centerX, centerY,2, mPaint);
    }

}

package com.example.sharpcj.dreammusic.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by joy on 2016/7/21.
 */
public class MyTextViewForRank extends View {

    private Paint mpaint;
    private int num;
    private int color=Color.BLACK;

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setNum(int num) {
        this.num = num;
        invalidate();
    }

    public void setColorAndNum(int color, int num) {
        this.color = color;
        this.num = num;
        invalidate();
    }

    public MyTextViewForRank(Context context) {
        super(context);
    }

    public MyTextViewForRank(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mpaint = new Paint();
        mpaint.setColor(color);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setAntiAlias(true);
        mpaint.setDither(true);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(getWidth(), 0);
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, mpaint);

        if (num != 0) {
            mpaint.setColor(Color.WHITE);
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setTextSize(30);
            String text;
            if (num < 10) {
                text = "0" + num;
            } else {
                text = "" + num;
            }
            canvas.drawText(text, getWidth() / 2 - 25, getHeight() / 2, mpaint);
        }
    }
}

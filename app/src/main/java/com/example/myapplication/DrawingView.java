package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.FileOutputStream;

public class DrawingView extends androidx.appcompat.widget.AppCompatImageView {

    private Path drawPath;
    private Paint drawPaint;
    private int lineWidth;
    private int selectedColor;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = getNewPaintPen(Color.BLACK);
        lineWidth = 150;
        selectedColor = Color.BLACK;
    }

    private Paint getNewPaintPen(int color) {
        Paint mPaintPen = new Paint();
        mPaintPen.setStrokeWidth(lineWidth);
        mPaintPen.setAntiAlias(true);
        mPaintPen.setDither(true);
        mPaintPen.setStyle(Paint.Style.STROKE);
        mPaintPen.setStrokeCap(Paint.Cap.ROUND);
        mPaintPen.setColor(color);
        return mPaintPen;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // Save or process the drawing when the finger is lifted.
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(drawPath, drawPaint);
    }

    public void setLineWidth(int width) {
        lineWidth = width;
        drawPaint.setStrokeWidth(width);
    }

    public void setColor(int color) {
        selectedColor = color;
        drawPaint.setColor(color);
    }

    public void clearDrawing() {
        drawPath.reset();
        invalidate();
    }
//v.getWidth()
    public Bitmap saveDrawing(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }


}

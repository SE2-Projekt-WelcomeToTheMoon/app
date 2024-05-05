package com.example.se2_projekt_app.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.se2_projekt_app.R;

public class CardDrawView extends SurfaceView implements SurfaceHolder.Callback, ScaleGestureDetector.OnScaleGestureListener {

    private Bitmap leftImageBitmap, middleImageBitmap, centerImageBitmap;
    private int leftNumber, middleNumber, centerNumber;

    public CardDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);

        // Load default images
        leftImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf);
        middleImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf);
        centerImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf);

        // Set default numbers
        leftNumber = 0;
        middleNumber = 0;
        centerNumber = 0;
    }

    @Override
    public boolean onScale(@NonNull ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(@NonNull ScaleGestureDetector detector) {

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // Call draw method when surface is created
        draw();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // Call draw method when surface is changed
        draw();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    private void draw() {
        SurfaceHolder holder = getHolder();
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            // Clear canvas
            canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);

            // Calculate positions for the three fields
            int fieldWidth = canvas.getWidth() / 3;
            int fieldHeight = canvas.getHeight();
            int fieldPadding = 20; // Adjust as needed

            // Draw left field
            canvas.drawBitmap(leftImageBitmap, fieldPadding, 0, null);
            drawNumber(canvas, leftNumber, fieldPadding + leftImageBitmap.getWidth() / 2, fieldHeight / 2);

            // Draw middle field
            canvas.drawBitmap(middleImageBitmap, fieldWidth + fieldPadding, 0, null);
            drawNumber(canvas, middleNumber, fieldWidth + fieldPadding + middleImageBitmap.getWidth() / 2, fieldHeight / 2);

            // Draw center field
            canvas.drawBitmap(centerImageBitmap, 2 * fieldWidth + fieldPadding, 0, null);
            drawNumber(canvas, centerNumber, 2 * fieldWidth + fieldPadding + centerImageBitmap.getWidth() / 2, fieldHeight / 2);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawNumber(Canvas canvas, int number, float x, float y) {
        Paint paint = new Paint();
        paint.setTextSize(50); // Adjust text size as needed
        paint.setColor(getResources().getColor(android.R.color.black)); // Adjust text color as needed
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(number), x, y, paint);
    }
}

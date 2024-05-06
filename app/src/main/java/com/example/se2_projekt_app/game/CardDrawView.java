package com.example.se2_projekt_app.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.se2_projekt_app.R;

public class CardDrawView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap leafBitmap;
    private Bitmap robotBitmap;
    private int number;

    public CardDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        leafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf);
        robotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
        number = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    private void draw() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            if (canvas != null) {
                // Clear canvas
                canvas.drawColor(Color.WHITE);

                // Calculate image dimensions
                int imageHeight = canvas.getHeight() / 5;
                int imageWidth = canvas.getWidth() / 3; // Limit width to one third of the screen width

                // Draw the image and number three times with appropriate spacing
                for (int i = 0; i < 3; i++) {
                    int left = i * (imageWidth + 20);
                    int top = 0;
                    int right = left + imageWidth;
                    int bottom = top + imageHeight;

                    canvas.drawBitmap(leafBitmap, null, new android.graphics.Rect(left, top, right, bottom), null);

                    int robotWidth = imageWidth / 4;
                    int robotHeight = imageHeight / 4;
                    canvas.drawBitmap(robotBitmap, null, new android.graphics.Rect(left, top, left + robotWidth, top + robotHeight), null);

                    Paint paint = new Paint();
                    paint.setTextSize(50);
                    paint.setColor(Color.BLACK);
                    paint.setTextAlign(Paint.Align.CENTER);

                    int numberX = left + (imageWidth / 2);
                    int numberY = top + (imageHeight / 2) + 25;
                    canvas.drawText(String.valueOf(number), numberX, numberY, paint);
                }
            }
        } finally {
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }
}

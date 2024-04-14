package com.example.se2_projekt_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

public class GameBoardView extends SurfaceView implements SurfaceHolder.Callback, ScaleGestureDetector.OnScaleGestureListener {
    private float scaleFactor = 1.0f;
    private float translateX = 0f, translateY = 0f;
    private float lastTouchX;
    private float lastTouchY;
    private List<GameBox> gameBoxes = new ArrayList<>();
    private ScaleGestureDetector scaleGestureDetector;
    private List<Section> sections = new ArrayList<>();

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
        init(context);
    }

    private void init(Context context) {
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        createSection(0, 0, 3, Element.ROBOT);
        createSection(0, 200, 3, Element.WATER);
        createSection(0, 400, 3, Element.WILDCARD);
        createSection(300, 0, 3, Element.PLANNING);
    }

    public void createSection(int x, int y, int boxCount, Element element) {
        Section section = new Section(x, y);
        int boxSize = 200;
        int color = element.getColor();

        for (int i = 0; i < boxCount; i++) {
            GameBox box = new GameBox(x + i * boxSize, y, boxSize, color, 0);
            section.addBox(box);
        }

        sections.add(section);
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        redraw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    public void drawGameBoard() {
        final Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            try {
                synchronized (getHolder()) {
                    canvas.save();
                    canvas.drawColor(Color.WHITE); // Clear with white background
                    canvas.translate(translateX, translateY); // Apply translation
                    canvas.scale(scaleFactor, scaleFactor); // Apply scaling

                    for (GameBox box : gameBoxes) {
                        box.draw(canvas);
                    }
                    for (Section section : sections) {
                        section.draw(canvas);
                    }

                    canvas.restore();
                }
            } finally {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);

        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                checkClick(lastTouchX, lastTouchY);
                return true;

            case MotionEvent.ACTION_MOVE:
                if (!scaleGestureDetector.isInProgress()) {
                    final float x = event.getX();
                    final float y = event.getY();

                    final float dx = (x - lastTouchX) * (1 / scaleFactor);
                    final float dy = (y - lastTouchY) * (1 / scaleFactor);

                    translateX += dx;
                    translateY += dy;

                    lastTouchX = x;
                    lastTouchY = y;

                    redraw();
                }
                return true;


            case MotionEvent.ACTION_UP:

                return true;
        }
        return true;
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        try {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f)); // Limit zooming
            redraw();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void checkClick(float x, float y) {
        float scaledX = (x - translateX) / scaleFactor;
        float scaledY = (y - translateY) / scaleFactor;

        for (Section section : sections) {
            section.handleClick(scaledX, scaledY, this);
        }
    }

    public void redraw() {
        drawGameBoard();
    }
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }
}

package com.example.se2_projekt_app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a view that supports scaling and touch interactions to manage and display game elements.
 */
public class GameBoardView extends SurfaceView implements SurfaceHolder.Callback, ScaleGestureDetector.OnScaleGestureListener {
    private float scaleFactor = 1.0f;
    private float translateX = 0f, translateY = 0f;
    private float lastTouchX;
    private float lastTouchY;
    private final List<Section> sections = new ArrayList<>();
    private ScaleGestureDetector scaleGestureDetector;

    /**
     * Constructs the game board view with necessary context and attributes.
     * @param context The context of the application.
     * @param attrs The set of attributes from XML.
     */
    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
        init(context);
    }

    /**
     * Initializes the scale gesture detector and sections of the game.
     * @param context The application context.
     */
    private void init(Context context) {
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        createSection(0, 0, 3, Element.ROBOT);
        createSection(0, 200, 3, Element.WATER);
        createSection(0, 400, 3, Element.WILDCARD);
        createSection(300, 0, 5, Element.PLANNING);
    }

    /**
     * Creates a game section with boxes at specified positions.
     * @param x The x-coordinate for the section.
     * @param y The y-coordinate for the section.
     * @param boxCount The number of boxes to create.
     * @param element The type of elements each box should represent.
     */
    private void createSection(int x, int y, int boxCount, Element element) {
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
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        drawGameBoard();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {}

    /**
     * Redraws the game board on the canvas, applying translation and scaling transformations.
     */
    public void drawGameBoard() {
        final Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            try {
                synchronized (getHolder()) {
                    canvas.save();
                    canvas.drawColor(Color.WHITE); // Clear with white background
                    canvas.translate(translateX, translateY);
                    canvas.scale(scaleFactor, scaleFactor);

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

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                float adjustedX = (event.getX() - translateX) / scaleFactor;
                float adjustedY = (event.getY() - translateY) / scaleFactor;
                sections.forEach(section -> section.handleClick(adjustedX, adjustedY, this));
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                if (!scaleGestureDetector.isInProgress()) {
                    final float dx = (event.getX() - lastTouchX) * (1 / scaleFactor);
                    final float dy = (event.getY() - lastTouchY) * (1 / scaleFactor);

                    translateX += dx;
                    translateY += dy;
                    lastTouchX = event.getX();
                    lastTouchY = event.getY();
                    drawGameBoard();
                }
                return true;
        }
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f)); // Limit zooming
        drawGameBoard();
        return true;
    }

    @Override
    public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(@NonNull ScaleGestureDetector detector) {
    }
}

package com.example.se2_projekt_app.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class CardDrawView extends SurfaceView implements SurfaceHolder.Callback, ScaleGestureDetector.OnScaleGestureListener{
    public CardDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}

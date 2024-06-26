package com.example.se2_projekt_app.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import lombok.Setter;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.game.CardCombination;
import com.example.se2_projekt_app.screens.GameScreen;

public class CardDrawView extends SurfaceView implements SurfaceHolder.Callback, ScaleGestureDetector.OnScaleGestureListener {

    private Bitmap plantBitmap;
    private Bitmap robotBitmap;
    private Bitmap energyBitmap;
    private Bitmap planningBitmap;
    private Bitmap spacesuitBitmap;
    private Bitmap waterBitmap;
    private final int[] xPositions;
    private CardCombination[] currentCombination;

    private int yHeight;
    private GameScreen gameScreen;
    @Setter
    private int selectedCombination;

    public CardDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setFocusable(true);
        xPositions = new int[3];
        selectedCombination=10;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        initializeBitmaps();
        if (currentCombination == null) {
            gameScreen.updateCards();

        }
    }

    private void initializeBitmaps() {

            plantBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.plant);
            robotBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot);
            energyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.energy);
            planningBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.planning);
            spacesuitBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spacesuit);
            waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water);

    }
    public void setGameScreen(GameScreen gameScreen){

        this.gameScreen=gameScreen;

    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        updateCanvas(currentCombination);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // No action needed
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if(gameScreen==null){
            return false;
        }
        if (action == MotionEvent.ACTION_DOWN) {
            if (event.getX() > xPositions[0] && event.getX() < xPositions[1] && event.getY() < yHeight) {
                gameScreen.setSelectedCard(currentCombination[0]);
                selectedCombination=0;
            } else if (event.getX() > xPositions[1] && event.getX() < xPositions[2] && event.getY() < yHeight) {
                gameScreen.setSelectedCard(currentCombination[1]);
                selectedCombination=1;
            } else if (event.getY() < yHeight) {
                gameScreen.setSelectedCard(currentCombination[2]);
                selectedCombination=2;
            }
        }
        updateCanvas(currentCombination);
        return true;
    }


    public void updateCanvas(CardCombination[] combination) {
        if(combination==null)throw new IllegalArgumentException("Cannot update on null combination");
        this.currentCombination = combination;
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            if (canvas != null) {
                // Clear canvas
                canvas.drawColor(Color.WHITE);

                // Calculate image dimensions
                int imageHeight = canvas.getHeight() ;
                int imageWidth = canvas.getWidth() / 3; // Limit width to one third of the screen width

                // Draw the image and number three times with appropriate spacing
                for (int i = 0; i < 3; i++) {
                    xPositions[i] = i * (imageWidth + 20); // Adjust spacing as needed
                    drawCombination(canvas, xPositions[i], combination[i], imageWidth, imageHeight, i == selectedCombination);
                }
                yHeight = imageHeight;
            }
        } finally {
            if (canvas != null) {
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    private void drawCombination(Canvas canvas, int offsetX, CardCombination combination, int symbolWidth, int symbolHeight, boolean selected) {
        if (combination == null || combination.getCurrentNumber() == null || combination.getCurrentSymbol() == null || combination.getNextSymbol() == null) {
            throw new IllegalArgumentException("Cannot draw from empty combination");
        }
        Bitmap currentSymbol = getBitMapFromElement(combination.getCurrentSymbol());
        Bitmap nextSymbol = getBitMapFromElement(combination.getNextSymbol());
        int currentNumber = combination.getCurrentNumber().getValue();

        // Draw current Symbol
        //Need to assert Not null because Sonarcloud even though its checked at the start of the method
        assert currentSymbol != null;
        canvas.drawBitmap(currentSymbol, null, new android.graphics.Rect(offsetX, 0, offsetX + symbolWidth, symbolHeight), null);

        // Draw nextSymbol on top of Current symbol at 1/4 the size
        int nextSymbolWidth = symbolWidth / 3;
        int nextSymbolHeight = symbolHeight / 3;
        int nextSymbolTop = symbolHeight - nextSymbolHeight;  // Bottom left position
        //Need to assert Not null because Sonarcloud even though its checked at the start of the method
        assert nextSymbol != null;
        canvas.drawBitmap(nextSymbol, null, new android.graphics.Rect(offsetX, nextSymbolTop, offsetX + nextSymbolWidth, nextSymbolTop + nextSymbolHeight), null);

        // Draw number on top of current Symbol with a white outline
        Paint outlinePaint = new Paint();
        outlinePaint.setTextSize(90); // Increase text size for outline
        outlinePaint.setColor(Color.WHITE); // Outline color
        outlinePaint.setStyle(Paint.Style.STROKE); // Outline style
        outlinePaint.setStrokeWidth(6); // Increase outline width
        outlinePaint.setTextAlign(Paint.Align.CENTER);
        // Calculate position to center number on Symbol
        int outlineNumberX = offsetX + (symbolWidth / 2);
        int outlineNumberY = symbolHeight / 2 + 25;
        canvas.drawText(String.valueOf(currentNumber), outlineNumberX, outlineNumberY, outlinePaint);

        // Draw the number again in black (centered on top of the outline)
        Paint textPaint = new Paint();
        textPaint.setTextSize(90); // Increase text size
        textPaint.setColor(Color.BLACK); // Text color
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(currentNumber), outlineNumberX, outlineNumberY, textPaint);
        // Draw green outline if selected
        if (selected) {
            Paint greenOutlinePaint = new Paint();
            greenOutlinePaint.setColor(Color.GREEN);
            greenOutlinePaint.setStyle(Paint.Style.STROKE);
            greenOutlinePaint.setStrokeWidth(10); // Adjust stroke width as needed
            canvas.drawRect(new android.graphics.Rect(offsetX, 0, offsetX + symbolWidth, symbolHeight), greenOutlinePaint);
        }
    }

    private Bitmap getBitMapFromElement(FieldCategory element) {
        switch (element) {
            case ENERGY: return energyBitmap;
            case PLANNING: return planningBitmap;
            case SPACESUIT: return spacesuitBitmap;
            case WATER: return waterBitmap;
            case PLANT: return plantBitmap;
            case ROBOT: return robotBitmap;
            default: return null;
        }
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
        // No action needed
    }
}

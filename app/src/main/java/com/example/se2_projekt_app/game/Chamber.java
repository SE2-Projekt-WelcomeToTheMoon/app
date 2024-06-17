package com.example.se2_projekt_app.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a section of the game board that contains multiple GameBoxes.
 */
public class Chamber implements Clickable {
    private final List<Field> fields;
    private final int x;
    private final int y;
    int boxSize = 200;
    private Field lastAccessedField = null;
    private int lastAccessedFieldIndex = -1;
    private Paint outlinePaint;
    private Paint rewardBoxPaint;
    private Paint rewardBoxBackgroundPaint;
    private Paint textPaint;

    /**
     * Constructs a Section with a specified origin.
     *
     * @param x The x-coordinate of the top-left corner of the section.
     * @param y The y-coordinate of the top-left corner of the section.
     */
    public Chamber(int x, int y, int count, FieldCategory category) {
        this.fields = new ArrayList<>();

        initPaint();

        for (int i = 0; i < count; i++) {
            fields.add(new Field(x + (i * boxSize), y, boxSize, category, FieldValue.NONE));
        }
        this.x = x;
        this.y = y;
    }

    /**
     * For Testing purposes
     */
    public void addField(Field field) {
        this.fields.add(field);
    }

    private void initPaint() {
        outlinePaint = new Paint();
        outlinePaint.setColor(Color.BLACK);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(10);

        rewardBoxPaint = new Paint();
        rewardBoxPaint.setColor(Color.BLACK);
        rewardBoxPaint.setStyle(Paint.Style.STROKE);
        rewardBoxPaint.setStrokeWidth(10);

        rewardBoxBackgroundPaint = new Paint();
        rewardBoxBackgroundPaint.setColor(Color.GRAY);
        rewardBoxBackgroundPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * Draws all GameBoxes in the section, translated to the section's position.
     *
     * @param canvas The canvas on which to draw the boxes.
     */
    public void draw(Canvas canvas) {

        for (Field field : fields) {
            field.draw(canvas);
        }

        float offset = 100;
        float rewardBoxLeft = x;
        float rewardBoxTop = y - offset;
        float rewardBoxRight = x + boxSize * fields.size();
        float rewardBoxBottom = y;

        canvas.drawRect(rewardBoxLeft, rewardBoxTop, rewardBoxRight, rewardBoxBottom, rewardBoxPaint);
        canvas.drawRect(rewardBoxLeft, rewardBoxTop, rewardBoxRight, rewardBoxBottom, rewardBoxBackgroundPaint);

        float rewardBoxCenterX = rewardBoxLeft + (rewardBoxRight - rewardBoxLeft) / 2;
        float rewardBoxCenterY = rewardBoxTop + (rewardBoxBottom - rewardBoxTop) / 2;

        String value1 = "RocketCount";
        String value2 = "ErrorCount";
        canvas.drawText(value1, rewardBoxCenterX + 100, rewardBoxCenterY + 20, textPaint);
        canvas.drawText(value2, rewardBoxCenterX - 100, rewardBoxCenterY + 20, textPaint);

        canvas.drawRect(x, y, (float) x + boxSize * fields.size(), (float) y + boxSize, outlinePaint);

    }




    public void setActive() {
        outlinePaint.setColor(Color.GREEN);
    }

    public void setInactive() {
        outlinePaint.setColor(Color.BLACK);
    }

    public Field getField(int index) {
        return fields.get(index);
    }

    public List<Field> getFields() {
        return new ArrayList<>(fields);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * For Testing purposes
     *
     * @return
     */
    public int getSize() {
        return fields.size();
    }

    /**
     * Handles click events within the section, checks if a click is within any GameBox,
     * and updates the box color and number if clicked.
     *
     * @param x         The x-coordinate of the click relative to the section's parent container.
     * @param y         The y-coordinate of the click relative to the section's parent container.
     * @param boardView The view that needs to be redrawn after handling the click.
     */
    @Override
    public boolean handleClick(float x, float y, GameBoardView boardView, FieldValue fieldValue) {
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (field.handleClick(x, y, boardView, fieldValue)) {
                lastAccessedFieldIndex = i;
                lastAccessedField = field;
                return true;
            }
        }
        return false;
    }

    public Field getLastAccessedField() {
        return lastAccessedField;
    }

    public int getLastAccessedFieldIndex() {
        return lastAccessedFieldIndex;
    }
}
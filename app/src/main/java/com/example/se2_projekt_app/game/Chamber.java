package com.example.se2_projekt_app.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.se2_projekt_app.R;
import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.enums.RewardCategory;
import com.example.se2_projekt_app.game_interface.Clickable;
import com.example.se2_projekt_app.views.GameBoardView;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Represents a section of the game board that contains multiple GameBoxes.
 */
public class Chamber implements Clickable {
    private final List<Field> fields;
    @Getter
    private final int x;
    @Getter
    private final int y;
    int boxSize = 200;
    @Getter
    private List<Reward> rewards;
    @Getter
    private Field lastAccessedField = null;
    @Getter
    private int lastAccessedFieldIndex = -1;
    private Paint outlinePaint;
    private Paint rewardBoxPaint;
    private Paint rewardBoxBackgroundPaint;
    private Paint textPaint;
    private Drawable rocketIcon;
    private Drawable errorIcon;
    private int errorCount;
    private int rocketCount;
    @Getter
    private boolean isActive = false;

    /**
     * Constructs a Section with a specified origin.
     *
     * @param x The x-coordinate of the top-left corner of the section.
     * @param y The y-coordinate of the top-left corner of the section.
     */
    public Chamber(int x, int y, int count, FieldCategory category) {
        this.fields = new ArrayList<>();
        errorCount = 0;
        rocketCount = 0;

        initPaint();

        for (int i = 0; i < count; i++) {
            fields.add(new Field(x + (i * boxSize), y, boxSize, category, FieldValue.NONE));
        }
        this.x = x;
        this.y = y;
        this.rewards = new ArrayList<>();
    }

    /**
     * Constructor for TESTING purposes
     */
    public Chamber(int x, int y, int count, FieldCategory category, Drawable rocketIcon, Drawable errorIcon, Paint paint) {
        this.fields = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.rewards = new ArrayList<>();
        this.rocketIcon = rocketIcon;
        this.errorIcon = errorIcon;
        this.textPaint = paint;
        this.rewardBoxPaint = paint;
        this.rewardBoxBackgroundPaint = paint;
        this.outlinePaint = paint;

        // Initialize fields
        for (int i = 0; i < count; i++) {
            fields.add(new Field(x + (i * boxSize), y, boxSize, category, FieldValue.NONE));
        }
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
    public void draw(Canvas canvas, Context context) {

        if (rocketIcon == null) {
            rocketIcon = ContextCompat.getDrawable(context, R.drawable.rocket_logo);
        }
        if (errorIcon == null) {
            errorIcon = ContextCompat.getDrawable(context, R.drawable.system_error_logo);
        }

        drawFields(canvas);
        drawRewardBox(canvas);
        drawIcons(canvas);
        drawOutline(canvas);
    }

    public void drawFields(Canvas canvas) {
        for (Field field : fields) {
            field.draw(canvas);
        }
    }

    public void drawRewardBox(Canvas canvas) {
        float offset = 100;
        float rewardBoxLeft = x;
        float rewardBoxTop = y - offset;
        float rewardBoxRight = (float) x + boxSize * fields.size();
        float rewardBoxBottom = y;

        canvas.drawRect(rewardBoxLeft, rewardBoxTop, rewardBoxRight, rewardBoxBottom, rewardBoxPaint);
        canvas.drawRect(rewardBoxLeft, rewardBoxTop, rewardBoxRight, rewardBoxBottom, rewardBoxBackgroundPaint);
    }

    public void drawIcons(Canvas canvas) {
        float rewardBoxCenterX = x + (float) boxSize * fields.size() / 2;
        float rewardBoxCenterY = (float) y - 50;

        int iconSpacing = 10;
        int maxIconSize = 90;

        drawIconAndText(canvas, rocketIcon, rocketCount, rewardBoxCenterX + 50, rewardBoxCenterY, iconSpacing, maxIconSize);
        drawIconAndText(canvas, errorIcon, errorCount, rewardBoxCenterX - 50 - textPaint.measureText(String.valueOf(errorCount)), rewardBoxCenterY, iconSpacing, maxIconSize);
    }

    private void drawIconAndText(Canvas canvas, Drawable icon, int count, float textX, float centerY, int spacing, int maxSize) {
        String text = String.valueOf(count);
        canvas.drawText(text, textX, centerY + 20, textPaint);

        int iconWidth = Math.min(icon.getIntrinsicWidth(), maxSize);
        int iconHeight = Math.min(icon.getIntrinsicHeight(), maxSize);

        int iconLeft = (int) (textX + textPaint.measureText(text) + spacing);
        int iconTop = (int) (centerY - (float) iconHeight / 2);
        icon.setBounds(iconLeft, iconTop, iconLeft + iconWidth, iconTop + iconHeight);
        icon.draw(canvas);
    }

    private void drawOutline(Canvas canvas) {
        canvas.drawRect(x, y, (float) x + boxSize * fields.size(), (float) y + boxSize, outlinePaint);
    }

    public void setActive() {
        this.isActive = true;
        outlinePaint.setColor(Color.GREEN);
    }

    public void setInactive() {
        this.isActive = false;
        outlinePaint.setColor(Color.BLACK);
    }

    public Field getField(int index) {
        return fields.get(index);
    }

    public List<Field> getFields() {
        return new ArrayList<>(fields);
    }

    /**
     * For Testing purposes
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

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
        for (Reward reward : rewards) {
            if (reward.getCategory() == RewardCategory.ROCKET) {
                rocketCount += reward.getNumberRockets();
            } else if (reward.getCategory() == RewardCategory.SYSTEMERROR) {
                errorCount++;
            }
        }
    }
}
package com.example.se2_projekt_app.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.views.GameBoardView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FieldTest {
    private Field field;
    @Mock
    private Field mockedField;
    @Mock
    private GameBoardView boardView;
    @Mock
    private Canvas mockCanvas;

    private final int initialX = 0;
    private final int initialY = 0;
    private final int boxSize = 200;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        field = new Field(initialX, initialY, boxSize, FieldCategory.ENERGY, FieldValue.ONE);
    }


    @Test
    void testHandleClick() {
        assertTrue(field.handleClick(15, 30, boardView),
                "Handle click should return true when click is inside a chamber threshold");

        assertFalse(field.handleClick(500, 30, boardView),
                "Handle click should return false when click is outside any chamber");
    }

    @Test
    void testDraw() {
        field.draw(mockCanvas);
        verify(mockCanvas, times(2)).drawRect(
                eq((float) initialX),
                eq((float) initialY),
                eq((float) initialX + boxSize),
                eq((float) initialY + boxSize),
                any());
    }

    @Test
    void testSetNumber() {
        assertEquals(FieldValue.ONE, field.getNumber(), "Initial Field number should be set correctly");
        field.setNumber(FieldValue.TWO);
        assertEquals(FieldValue.TWO, field.getNumber(), "Field number should be updated");
    }

    @Test
    void testIsPointInside() {
        assertTrue(field.isPointInsideBox(15, 30), "Should return true when click is inside the box");
        assertFalse(field.isPointInsideBox(500, 30), "Should return false when click is outside the box");
    }
}

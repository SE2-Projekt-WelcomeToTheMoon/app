package com.example.se2_projekt_app.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Canvas;

import com.example.se2_projekt_app.enums.FieldCategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ChamberTest {
    private Chamber chamber;
    @Mock
    private Field mockField;
    @Mock
    private Canvas mockCanvas;

    private final int initialX = 0;
    private final int initialY = 0;
    private final int boxSize = 200;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProperInitialization() {
        chamber = new Chamber(initialX, initialY, 5, FieldCategory.ENERGY);
        assertEquals(5, chamber.getSize(), "Chamber should have correct size");

        assertNotNull(chamber.getField(0), "The added field should be retrievable");
    }

    @Test
    void testHandleClick() {
        Chamber chamber = new Chamber(initialX, initialY, 0, FieldCategory.ENERGY);

        chamber.addField(mockField);
        chamber.addField(mockField);

        when(mockField.handleClick(anyFloat(), anyFloat(), any(), any())).thenAnswer(invocation -> {
            float x = invocation.getArgument(0);
            float y = invocation.getArgument(1);
            return (x >= -boxSize && x <= boxSize && y >= 0 && y <= boxSize);
        });

        assertTrue(chamber.handleClick(15, 30, null, null),
                "Handle click should return true when click is inside a chamber threshold");

        assertFalse(chamber.handleClick(500, 30, null, null),
                "Handle click should return false when click is outside any chamber");
    }

    @Test
    void testDraw() {
        Chamber chamber = new Chamber(initialX, initialY, 0, FieldCategory.ENERGY);

        chamber.addField(mockField);
        chamber.addField(mockField);

        chamber.draw(mockCanvas);

        for (Field field : chamber.getFields()) {
            verify(field, times(2)).draw(mockCanvas);
        }
    }
}

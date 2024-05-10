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

import java.util.List;

class FloorTest {
    private Floor floor;
    @Mock
    private Chamber mockChamber;
    @Mock
    private Canvas mockCanvas;

    private final int initialX = 0;
    private final int initialY = 0;
    private final int boxSize = 200;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        floor = new Floor(initialX, initialY, FieldCategory.ENERGY);
    }

    @Test
    void testChamberOperations() {
        floor.addChamber(5);
        assertEquals(1, floor.getChambers().size(), "Chamber count should increase");

        int expectedNextX = initialX + 5 * boxSize;
        assertEquals(expectedNextX, floor.getNextX(), "NextX should be correctly updated");

        Chamber addedChamber = floor.getChamber(0);
        assertEquals(5, addedChamber.getSize(), "The added chamber should have correct size");

        floor.addChamber(3);
        assertEquals(2, floor.getChambers().size(), "Second chamber added, count should increase");
        Chamber secondChamber = floor.getChamber(1);
        assertEquals(3, secondChamber.getSize(), "Second chamber should have correct size");

        List<Chamber> chambers = floor.getChambers();
        chambers.clear();
        assertEquals(2, floor.getChambers().size(), "GetChambers should return a defensive copy");
    }

    @Test
    void testHandleClick() {
        floor.addChamber(mockChamber);

        when(mockChamber.handleClick(anyFloat(), anyFloat(), any())).thenAnswer(invocation -> {
            float x = invocation.getArgument(0);
            float y = invocation.getArgument(1);
            return (x >= -boxSize && x <= boxSize && y >= 0 && y <= boxSize);
        });

        assertTrue(floor.handleClick(15, 30, null),
                "Handle click should return true when click is inside a chamber threshold");

        assertFalse(floor.handleClick(500, 30, null),
                "Handle click should return false when click is outside any chamber");
    }

    @Test
    void testDraw() {
        floor.addChamber(mockChamber);
        floor.addChamber(mockChamber);

        floor.draw(mockCanvas);

        for (Chamber chamber : floor.getChambers()) {
            verify(chamber, times(2)).draw(mockCanvas);
        }

    }

    @Test
    void testProperPlacement() {
        floor.addChamber(5);
        floor.addChamber(3);
        floor.addChamber(4);

        assertEquals(floor.getChamber(0).getX(), initialX, "First chamber should be at initialX");
        assertEquals(floor.getChamber(1).getX(), initialX + 5 * boxSize, "Second chamber should be at initialX + 5 * boxSize");
        assertEquals(floor.getChamber(2).getX(), initialX + 8 * boxSize, "Third chamber should be at initialX + 8 * boxSize");

        assertEquals(floor.getChamber(0).getY(), initialY, "First chamber should be at initialY");
    }
}

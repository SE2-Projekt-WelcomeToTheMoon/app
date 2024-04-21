package com.example.se2_projekt_app.game_interface;

import com.example.se2_projekt_app.views.GameBoardView;
public interface Clickable {
    boolean handleClick(float x, float y, GameBoardView boardView);
}
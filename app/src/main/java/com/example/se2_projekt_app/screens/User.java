package com.example.se2_projekt_app.screens;

import com.example.se2_projekt_app.game.GameBoard;

import java.util.Objects;

public class User {
    private String username;
    private GameBoard gameBoard;
    private boolean isLocalUser;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setLocalUser(boolean localUser) {
        isLocalUser = localUser;
    }

    public boolean getLocalUser() {
        return isLocalUser;
    }
}

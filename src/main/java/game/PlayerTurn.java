package game;

import javafx.scene.paint.Color;

public enum PlayerTurn {
    PLAYER("Player Turn", Color.BLUE),
    ENEMY("Enemy Turn", Color.YELLOW),
    EMPTY("", Color.BLACK);

    private final String text;
    private final Color colour;

    PlayerTurn(String text, Color colour) {
        this.text = text;
        this.colour = colour;
    }

    public String getText() {
        return text;
    }

    public Color getColour() {
        return colour;
    }
}

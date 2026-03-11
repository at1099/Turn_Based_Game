package game;

public enum PlayerTurn {
    PLAYER("Player Turn"),
    ENEMY("Enemy Turn"),
    EMPTY("");

    ;
    private String text;
    PlayerTurn(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

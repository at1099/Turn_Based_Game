package game.board;

import game.Assets;
import javafx.scene.image.Image;

public enum TileType {

    GRASS(1, 0, Assets.GRASS),
    WATER(99, -10, Assets.WATER),
    MOUNTAIN(3, 20, Assets.MOUNTAIN);

    private int movementCost;
    private int hitChanceChange;
    private Image image;

    TileType(int movementCost, int hitChanceChange, Image image) {
        this.movementCost = movementCost;
        this.hitChanceChange = hitChanceChange;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public int getHitChanceChange() {
        return hitChanceChange;
    }
}
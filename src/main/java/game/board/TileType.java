package game.board;

import game.Assets;
import javafx.scene.image.Image;

public enum TileType {

    GRASS(true, 1, 0, Assets.GRASS),
    WATER(false, 999, -10, Assets.WATER),
    MOUNTAIN(false, 3, 20, Assets.MOUNTAIN);

    private boolean walkable;
    private int movementCost;
    private int hitChanceChange;
    private Image image;

    TileType(boolean walkable, int movementCost, int hitChanceChange, Image image) {
        this.walkable = walkable;
        this.movementCost = movementCost;
        this.hitChanceChange = hitChanceChange;
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public boolean getWalkable() {
        return walkable;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public int getHitChanceChange() {
        return hitChanceChange;
    }
}
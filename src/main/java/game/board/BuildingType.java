package game.board;

import java.awt.*;

import game.Assets;
import javafx.scene.image.Image;

public enum BuildingType {

    VILLAGE(false, Assets.VILLAGE, 20),
    CASTLE(true, Assets.CASTLE, 0);

    private boolean canSummon;
    private Image image;
    private int healthRejen;

    BuildingType(boolean canSummon, Image image, int healthRejen){
        this.canSummon = canSummon;
        this.image = image;
        this.healthRejen = healthRejen;
    }

    public Image getImage() {
        return image;
    }

    public boolean getCanSummon(){
        return canSummon;
    }

    public int getHealthRejen(){
        return healthRejen;
    }
}

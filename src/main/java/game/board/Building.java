package game.board;
import game.units.UnitType;
import javafx.scene.image.Image;

public class Building {
    private BuildingType type;
    private Tile position;

    public Building(BuildingType type, Tile position) {
        this.type = type;
        this.position = position;
    }

    public Image getImage() {
        return type.getImage();
    }

    public Tile getPosition(){
        return position;
    }

    public void setPosition(Tile position){
        this.position = position;
    }

    public BuildingType getType(){
        return type;
    }
}

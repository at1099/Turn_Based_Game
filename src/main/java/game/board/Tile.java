package game.board;
import game.units.Unit;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
    public static final Color HIGHLIGHT_YELLOW = Color.color(1,1,0,0.4);
    public static final Color GREYED_OUT = Color.color(0.0, 0.3, 0.0, 0.6); // semi-transparent gray

    private TileType type;
    private int x;
    private int y;

    private Building building;
    private ImageView backgroundImage;
    private ImageView buildingImage;
    private Rectangle highlight;
    private Unit unit;
    private ImageView unitImage;
    private StackPane stack;

    public Tile(TileType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;

        backgroundImage = new ImageView(type.getImage());
        backgroundImage.setFitWidth(64);
        backgroundImage.setFitHeight(64);

        highlight = new Rectangle(64, 64);
        highlight.setFill(Color.TRANSPARENT);

        buildingImage = new ImageView();
        buildingImage.setFitWidth(60);
        buildingImage.setFitHeight(60);

        unitImage = new ImageView();
        unitImage.setFitWidth(48);
        unitImage.setFitHeight(48);

        stack = new StackPane(backgroundImage, highlight, buildingImage, unitImage);
    }

    public StackPane getNode() {
        return stack;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building){
        this.building = building;

        if (building != null) {
            buildingImage.setImage(building.getType().getImage());
            building.setPosition(this);
        } else {
            buildingImage.setImage(null);
            building.setPosition(null);
        }
    }

    public ImageView getBuildingImage(){
        return buildingImage;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Unit getUnit(){
        return unit;
    }

    public void setUnit(Unit unit){
        this.unit = unit;

        if (unit != null) {
            unitImage.setImage(unit.getType().getImage());
            unit.setPosition(this);
        } else {
            unitImage.setImage(null);
            unit.setPosition(null);
        }
    }

    public void removeUnit(){
        unit = null;
        unitImage.setImage(null);
    }

    public void setHighlighted(boolean value) {
        if (value) {
            highlight.setFill(HIGHLIGHT_YELLOW); // transparent yellow
        } else {
            highlight.setFill(Color.TRANSPARENT);
        }
    }

    public void setGreyedOut(boolean value){
        if (value) {
            highlight.setFill(HIGHLIGHT_YELLOW);
        } else {
            highlight.setFill(Color.TRANSPARENT);
        }
    }

    public Rectangle getHighlight(){
        return highlight;
    }

    public boolean isHighlighted(){
        Color color = (Color) highlight.getFill();
        return color.equals(HIGHLIGHT_YELLOW);
    }

    public TileType getType(){
        return type;
    }

    public boolean getWalkable(){
        return type.getWalkable();
    }

    public int getMovementCost(){
        return type.getMovementCost();
    }

    public int getHitChanceChange(){
        return type.getHitChanceChange();
    }
}

package game.board;
import game.units.Unit;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
    public static final Color HIGHLIGHT_YELLOW = Color.color(1,1,0,0.4);
    public static final Color GREYED_OUT = Color.color(0.4, 0.4, 0.4, 0.6); // semi-transparent gray
    public static final Color HIGHLIGHT_BLUE = Color.color(0.2, 0.6, 1.0, 0.4);
    public static final Color HIGHLIGHT_GREEN = Color.color(0, 1, 0, 0.7);
    public static final Color HIGHLIGHT_RED = Color.color(1, 0, 0, 0.7);

    private TileType type;
    private int x;
    private int y;

    private Rectangle highlight;
    private Building building;
    private ImageView backgroundImage;
    private ImageView buildingImage;
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
        stack.setStyle("-fx-border-color: black; -fx-border-width: 0.5;"); //creates balck gap
    }

    public StackPane getNode() {
        return stack;
    }

    public Building getBuilding() {
        return building;
    }

    public void clearBorderHighlight(){
        stack.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
    }

    public void setBorderHighlight(Color color) {
        int r = (int)(color.getRed() * 255);
        int g = (int)(color.getGreen() * 255);
        int b = (int)(color.getBlue() * 255);;
        stack.setStyle("-fx-border-color: " + "rgb(" + r + "," + g + "," + b + ")" + "; -fx-border-width: 3;");
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

    public void setHighlightColour(Color colour) {
        highlight.setFill(colour);
    }

    public Rectangle getHighlight(){
        return highlight;
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

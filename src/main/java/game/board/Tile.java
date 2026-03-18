package game.board;
import game.units.Unit;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
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
    private ProgressBar healthBar = new ProgressBar();

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

        healthBar = new ProgressBar(1);
        healthBar.setPrefWidth(40);
        healthBar.setMaxWidth(40);
        healthBar.setStyle("-fx-accent: red;");
        healthBar.setVisible(false);

        stack = new StackPane(backgroundImage, highlight, buildingImage, unitImage, healthBar);
        StackPane.setAlignment(healthBar, Pos.TOP_CENTER);
        stack.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");
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

    public void updateHealthBar() {
        if (unit != null) {
            double hp = (double) unit.getCurrentHealth() / unit.getMaxHealth();
            healthBar.setProgress(hp);
            healthBar.setVisible(true);
        } else {
            healthBar.setVisible(false);
        }
    }
}

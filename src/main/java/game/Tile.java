package game;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Tile {
    private TileType type;
    private int x;
    private int y;

    private Rectangle rectangle;
    private Unit unit;
    private Building building;
    private boolean highlighted;

    public Tile(TileType type){
        this.type = type;
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle){
        this.rectangle = rectangle;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean isEmpty(){
        return building == null;
    }

    public Unit getUnit(){
        return unit;
    }
    public void setUnit(Unit unit){
        this.unit = unit;
    }

    public void removeUnit(){
        unit = null;
    }

    public void highlight(){
        highlighted = true;
        if(rectangle != null){
            rectangle.setFill(Color.YELLOW);
        }
    }

    public void clearHighlight(){
        highlighted = false;
        if(rectangle != null){
            rectangle.setFill(Color.LIGHTGREEN);
        }
    }

    public boolean isHighlighted(){
        return highlighted;
    }

    public TileType getType(){
        return type;
    }

    public void setType(TileType type){
        this.type = type;
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

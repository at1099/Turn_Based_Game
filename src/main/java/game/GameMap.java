package game;

import exceptions.InvalidLocationException;

public class GameMap {
    private int width;
    private int height;
    private Object[][] grid;

    public GameMap(int width, int height){
        this.width = width;
        this.height = height;
        this.grid = new Object[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Object[][] getGrid(){
        return grid;
    }

    public void addObject(int x, int y, Object object) throws InvalidLocationException {
        if (grid[x][y] != null){
            throw new InvalidLocationException("Location already has an object in it");
        }
        grid[x][y] = object;
    }

    public void removeObject(int x, int y) throws InvalidLocationException{
        if (grid[x][y] == null){
            throw new InvalidLocationException("Location has no object in it");
        }
        grid[x][y] = null;
    }

    public boolean IsObstacle(int x, int y){
        return grid[x][y] instanceof Obstacle;
    }

    public boolean IsEnemy(int x, int y){
        return (grid[x][y] instanceof Unit && !grid[x][y].isAlly());
    }

    public boolean LegalMove(Unit unit, int newX, int newY){
        if (unit.IsInRange(newX, newY) && !IsObstacle(newX, newY)){
            return true;
        }
        return false;
    }

    public boolean LegalAttack(Unit unit, int newX, int newY){
        if (unit.IsInRange(newX, newY) && IsEnemy(newX, newY)){
            return true;
        }
        return false;
    }
}

package game;

import exceptions.InvalidLocationException;

/* represents a level */
public class GameMap {
    private int width;
    private int height;
    private Tile[][] tileGrid;

    public GameMap(int width, int height){
        this.width = width;
        this.height = height;
        this.tileGrid = new Tile[width][height];

        // initialize all tiles as grass by default
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tileGrid[x][y] = new Tile(TileType.GRASS);
                tileGrid[x][y].setX(x);
                tileGrid[x][y].setY(y);
            }
        }
    }

    public Tile getTile(int x, int y){
        return tileGrid[x][y];
    }

    public Tile[][] getTiles() {
        return tileGrid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void placeUnit(Unit unit, int x, int y){
        Tile tile = tileGrid[x][y];
        tile.setUnit(unit);
        unit.setPosition(tile);
    }
}

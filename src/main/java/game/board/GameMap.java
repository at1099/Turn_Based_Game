package game.board;

import game.units.Unit;

/* represents a level */
public class GameMap {
    private int width;
    private int height;
    private Tile[][] tileGrid;
    private Unit selectedUnit;

    public GameMap(int width, int height){
        this.width = width;
        this.height = height;
        this.tileGrid = new Tile[width][height];
        this.selectedUnit = null;

        // initialize all tiles as grass by default
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                tileGrid[x][y] = new Tile(TileType.GRASS, x, y);
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

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    public void removeUnit(Unit unit) {
        Tile tile = unit.getPosition();
        if (tile != null) {
            tile.removeUnit();
        }
    }
}

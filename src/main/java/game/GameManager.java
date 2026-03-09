package game;

import game.board.GameMap;
import game.board.Tile;
import game.units.Unit;

public class GameManager {
    private GameMap currentLevel;
    private Unit selectedUnit;

    public GameManager(GameMap currentLevel){
        this.currentLevel = currentLevel;
        selectedUnit = null;
    }

    public GameMap getMap(){
        return currentLevel;
    }

    public void clearHighlights(){
        for(int x = 0; x < currentLevel.getWidth(); x++){
            for(int y = 0; y < currentLevel.getHeight(); y++){
                currentLevel.getTile(x,y).setHighlighted(false);
            }
        }
    }

    public void moveUnit(Unit unit, Tile destination){
        Tile oldTile = unit.getPosition();
        oldTile.removeUnit();

        destination.setUnit(unit);
        unit.moveTo(destination);
    }

    public void highlightPossibleMoveSquares(Unit selectedUnit){
        for(int x = 0; x < currentLevel.getWidth(); x++) {
            for (int y = 0; y < currentLevel.getHeight(); y++) {
                Tile currentTile = currentLevel.getTile(x,y);
                if (!selectedUnit.canMove(currentTile)){
                    currentTile.setGreyedOut(true);
                }
            }
        }
    }

    public void handleTileClick(int x, int y){
        Tile clickedTile = currentLevel.getTile(x, y);

        if(selectedUnit == null){
            if(clickedTile.getUnit() != null){
                selectedUnit = clickedTile.getUnit();
                clickedTile.setHighlighted(true);
                highlightPossibleMoveSquares(selectedUnit);
            }
        } else {
            if(selectedUnit.canMove(clickedTile) && clickedTile.getUnit() == null){
                moveUnit(selectedUnit, clickedTile);
            }

            selectedUnit = null;
            clearHighlights();
        }
    }
}

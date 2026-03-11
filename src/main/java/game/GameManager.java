package game;

import game.board.GameMap;
import game.board.Tile;
import game.units.Unit;
import javafx.scene.paint.Color;

public class GameManager {
    private GameMap currentLevel;
    private Unit selectedUnit;
    private TurnManager turnManager;

    public GameManager(GameMap currentLevel){
        this.currentLevel = currentLevel;
        selectedUnit = null;
        turnManager = new TurnManager(PlayerTurn.PLAYER);
    }

    public TurnManager getTurnManager(){
        return turnManager;
    }

    public GameMap getMap(){
        return currentLevel;
    }

    public void clearHighlights(){
        for(int x = 0; x < currentLevel.getWidth(); x++){
            for(int y = 0; y < currentLevel.getHeight(); y++){
                currentLevel.getTile(x,y).setHighlightColour(Color.TRANSPARENT);
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
                    currentTile.setHighlightColour(currentTile.GREYED_OUT);
                }
            }
        }
    }

    public void handleTileClick(int x, int y){
        Tile clickedTile = currentLevel.getTile(x, y);

        if(selectedUnit == null){
            if(clickedTile.getUnit() != null &&  clickedTile.getUnit().getCurrentTeam() == turnManager.getCurrentTurn()){
                selectedUnit = clickedTile.getUnit();
                clickedTile.setHighlightColour(clickedTile.HIGHLIGHT_YELLOW);
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

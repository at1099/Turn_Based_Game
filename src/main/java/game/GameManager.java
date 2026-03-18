package game;

import game.board.GameMap;
import game.board.Tile;
import game.units.AttackType;
import game.units.Unit;
import game.units.UnitState;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private GameMap currentLevel;
    private Unit selectedUnit;
    private TurnManager turnManager;
    List<Unit> unitsToMove = new ArrayList<>();

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
        unit.setDestination(destination);
        unit.setState(UnitState.MOVING);
        unitsToMove.add(unit);
    }

    public Tile findAttackPos(Unit unit, Tile destination){
        //first test moving left
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        Tile closestTile = null;

        int destinationX = destination.getX();
        int destinationY = destination.getY();

        for(int[] d : directions){
            if(destinationX + d[0] <= currentLevel.getWidth() && destinationY + d[1] <= currentLevel.getHeight()){
                Tile currentTile = currentLevel.getTile(destinationX+ d[0], destinationY + d[1]);
                if (closestTile == null){
                    closestTile = currentTile;
                }else if (unit.getDistance(currentTile) < unit.getDistance(closestTile)) {
                    closestTile = currentTile;
                }
            }
        }

        return closestTile;
    }

    public void attackUnit(Unit unit, Tile destination){
        Unit enemy = destination.getUnit();
        unit.setDestination(findAttackPos(unit, destination));
        unit.setState(UnitState.ATTACKING);
        unitsToMove.add(unit);
    }

    public void highlightSquares(Unit selectedUnit){
        for(int x = 0; x < currentLevel.getWidth(); x++) {
            for (int y = 0; y < currentLevel.getHeight(); y++) {
                Tile currentTile = currentLevel.getTile(x,y);
                if (!selectedUnit.canMove(currentTile)){
                    currentTile.setHighlightColour(currentTile.GREYED_OUT);
                }else{
                    if (currentTile.getUnit() != null && currentTile.getUnit().getCurrentTeam() != selectedUnit.getCurrentTeam()){
                        currentTile.setHighlightColour(currentTile.HIGHLIGHT_RED);
                    }
                }
            }
        }
    }

    public void handleTileClick(int x, int y){
        Tile clickedTile = currentLevel.getTile(x, y);

        if(selectedUnit == null){
            if(clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() == turnManager.getCurrentTurn() && !clickedTile.getUnit().getHasMoved()){
                selectedUnit = clickedTile.getUnit();
                clickedTile.setHighlightColour(clickedTile.HIGHLIGHT_GREEN);
                currentLevel.setSelectedUnit(selectedUnit);
                highlightSquares(selectedUnit);
            }
        } else {
            if(selectedUnit.canMove(clickedTile) && clickedTile.getUnit() == null){
                moveUnit(selectedUnit, clickedTile);
                highlightSquares(selectedUnit);
            }else if(selectedUnit.canMove(clickedTile) && clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() != selectedUnit.getCurrentTeam()){
                attackUnit(selectedUnit, clickedTile);
            }

            selectedUnit = null;
            clearHighlights();
        }
    }

    public void switchTurn(){
        for(int x = 0; x < currentLevel.getWidth(); x++){
            for(int y = 0; y < currentLevel.getHeight(); y++){
                Tile currentTile = currentLevel.getTile(x,y);
                currentTile.setHighlightColour(Color.TRANSPARENT); //resets highlights
            }
        }

        for (Unit unit : unitsToMove) { //moves units
            unit.move();
            unit.setHasMoved(false);
        }

        unitsToMove.clear();
        turnManager.switchTurn();
    }
}

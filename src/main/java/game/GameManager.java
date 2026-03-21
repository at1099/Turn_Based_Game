package game;

import game.board.GameMap;
import game.board.Tile;
import game.units.AttackType;
import game.units.Unit;
import game.units.UnitState;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class GameManager {
    private GameMap currentLevel;
    private Unit selectedUnit;
    private TurnManager turnManager;
    List<Unit> unitsToMove = new ArrayList<>();
    List<Unit> unitsToAttack = new ArrayList<>();

    public GameManager(GameMap currentLevel){
        this.currentLevel = currentLevel;
        selectedUnit = null;
        turnManager = new TurnManager(PlayerTurn.PLAYER);
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
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

        if(unit.getEnemyToAttack() != null){
            unit.getEnemyToAttack().setAttackedBy(null);
            unit.setEnemyToAttack(null);
        }
        unitsToAttack.remove(unit);

        selectedUnit = null;
        clearHighlights();
    }

    public Tile findAttackPos(Unit unit, Tile destination){
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        Tile closestTile = null;

        int destinationX = destination.getX();
        int destinationY = destination.getY();

        for(int[] d : directions){
            int nx = destinationX + d[0];
            int ny = destinationY + d[1];
            if(nx >= 0 && nx < currentLevel.getWidth() && ny >= 0 && ny < currentLevel.getHeight()){
                Tile currentTile = currentLevel.getTile(nx, ny);
                if (closestTile == null){
                    closestTile = currentTile;
                } else if (unit.getDistance(currentTile) < unit.getDistance(closestTile)) {
                    closestTile = currentTile;
                }
            }
        }

        return closestTile;
    }

    public void moveAttackingUnit(Unit unit, Tile destination){
        unit.setDestination(findAttackPos(unit, destination));
        unit.setState(UnitState.READY_TO_ATTACK);
        unit.setEnemyToAttack(destination.getUnit());
        unitsToMove.remove(unit);
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

    public void handleAttackClick(AttackType attack){
        if (selectedUnit != null && selectedUnit.getState() == UnitState.READY_TO_ATTACK){
            Unit enemy = selectedUnit.getEnemyToAttack();
            enemy.setAttackedBy(attack);

            unitsToAttack.add(selectedUnit);
            selectedUnit.setState(UnitState.ATTACKING);

            selectedUnit = null;
            clearHighlights();
            out.println("Attack clicked: " + attack);
            out.println("Attacking unit: " + selectedUnit);
        }
    }

    public void handleTileClick(int x, int y){
        Tile clickedTile = currentLevel.getTile(x, y);

        if(selectedUnit == null){

            if(clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() == turnManager.getCurrentTurn() && clickedTile.getUnit().getState() == UnitState.IDLE){
                System.out.println("Hello");
                selectedUnit = clickedTile.getUnit();
                selectedUnit.setDestination(null);
                clearHighlights();
                clickedTile.setHighlightColour(clickedTile.HIGHLIGHT_GREEN);
                setSelectedUnit(selectedUnit);
                highlightSquares(selectedUnit);
            } else if(clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() == turnManager.getCurrentTurn() && clickedTile.getUnit().getState() !=  UnitState.IDLE){
                selectedUnit = null;
                Unit unit = clickedTile.getUnit();
                unit.setDestination(null);
                unit.setState(UnitState.IDLE);
                if (unitsToMove.contains(unit)){
                    unitsToMove.remove(unit);
                }
                if(unitsToAttack.contains(unit)){
                    unitsToAttack.remove(unit);
                }
                clearHighlights();
            }
        } else {
            if(selectedUnit.canMove(clickedTile) && clickedTile.getUnit() == null){
                moveUnit(selectedUnit, clickedTile);
                //highlightSquares(selectedUnit);
            }else if(selectedUnit.canMove(clickedTile) && clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() != selectedUnit.getCurrentTeam()){
                moveAttackingUnit(selectedUnit, clickedTile);
                //highlightSquares(selectedUnit);
            }
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
            unit.setState(UnitState.IDLE);
        }

        for (Unit unit : unitsToAttack) { //moves units
            //if (unit.getEnemyToAttack() == null || unit.getAttackedBy() == null) continue;
            unit.move();
            Unit enemy = unit.getEnemyToAttack();
            enemy.takeDamage();
            enemy.setAttackedBy(null);

            unit.setState(UnitState.IDLE);
            unit.setHasAttacked(false);
            unit.setHasMoved(false);
            if (unit.getEnemyToAttack().isDead()){
                currentLevel.removeUnit(unit.getEnemyToAttack());
            }
        }
        out.println("Units to attack: " + unitsToAttack.size());
        if (selectedUnit != null) {
            System.out.println("Hi");
            selectedUnit.setDestination(null);
        }
        selectedUnit = null;
        unitsToMove.clear();
        unitsToAttack.clear();
        clearHighlights();
        turnManager.switchTurn();
    }
}

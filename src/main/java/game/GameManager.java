package game;

import game.board.Building;
import game.board.BuildingType;
import game.board.GameMap;
import game.board.Tile;
import game.units.AttackType;
import game.units.Unit;
import game.units.UnitState;
import game.units.UnitType;
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
    List<Unit> unitsToSummon = new ArrayList<>();

    public GameManager(GameMap currentLevel){
        this.currentLevel = currentLevel;
        selectedUnit = null;
        turnManager = new TurnManager(PlayerTurn.PLAYER);
    }

    public void resetUnit(){
        if (selectedUnit != null){
            selectedUnit.resetUnit();
            selectedUnit = null;
        }
        clearHighlights();
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

    public void selectSummonLocation(Tile tile){
        selectedUnit.setState(UnitState.READY_TO_SUMMON);
        tile.setHighlightColour(selectedUnit.getCurrentTeam().getColour());
        selectedUnit.setDestination(tile);
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

    public void highlightMoveSquares(Unit selectedUnit){
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

    public void highlightSummonSquares(Unit selectedUnit){
        for(int x = 0; x < currentLevel.getWidth(); x++) {
            for (int y = 0; y < currentLevel.getHeight(); y++) {
                Tile currentTile = currentLevel.getTile(x,y);
                if (!selectedUnit.canSummonIn(currentTile)){
                    currentTile.setHighlightColour(currentTile.GREYED_OUT);
                }
            }
        }
    }

    public void handleSummonButtonClick(){
        highlightSummonSquares(selectedUnit);
        selectedUnit.setState(UnitState.SELECTING_SUMMON_LOC);
        selectedUnit.setCanCurrentlySummon(false);
    }

    public void handleSummonClick(UnitType unit){
        if (selectedUnit != null){
            Unit newUnit = new Unit(selectedUnit.getDestination(), "Summoned Unit", selectedUnit.getCurrentTeam(), unit, UnitState.IDLE);
            unitsToSummon.add(newUnit);

            selectedUnit = null;
            clearHighlights();
            out.println("Summoning unit: " + newUnit);
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

    public void handleTileClick(int x, int y) {
        Tile clickedTile = currentLevel.getTile(x, y);

        if (selectedUnit == null) {
            //if an idle unit is selected
            if (clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() == turnManager.getCurrentTurn() && clickedTile.getUnit().getState() == UnitState.IDLE) {
                selectedUnit = clickedTile.getUnit();

                if (clickedTile.getBuilding() != null && clickedTile.getBuilding().getType() == BuildingType.CASTLE) {
                    selectedUnit.setCanCurrentlySummon(true);
                }

                selectedUnit.setDestination(null);
                clearHighlights();
                clickedTile.setHighlightColour(clickedTile.HIGHLIGHT_GREEN);
                setSelectedUnit(selectedUnit);
                highlightMoveSquares(selectedUnit);
            //if a unit not in idle is selected (resets)
            } else if (clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() == turnManager.getCurrentTurn() && clickedTile.getUnit().getState() != UnitState.IDLE) {
                selectedUnit = null;
                Unit unit = clickedTile.getUnit();
                unit.setDestination(null);
                unit.setState(UnitState.IDLE);
                if (unitsToMove.contains(unit)) {
                    unitsToMove.remove(unit);
                }
                if (unitsToAttack.contains(unit)) {
                    unitsToAttack.remove(unit);
                }
                clearHighlights();
            }
        //if unit is already selected
        } else {
            //if a unit on the same team is selected (resets)
            if (clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() == turnManager.getCurrentTurn()) {
                selectedUnit.resetUnit();
                selectedUnit = null;
                clearHighlights();
            //if an empty square is picked (and unit not about to summon)
            } else if (selectedUnit.canMove(clickedTile) && clickedTile.getUnit() == null && selectedUnit.getState() != UnitState.SELECTING_SUMMON_LOC) {
                moveUnit(selectedUnit, clickedTile);
            //if enemy unit is selected to attack
            } else if (selectedUnit.canMove(clickedTile) && clickedTile.getUnit() != null && clickedTile.getUnit().getCurrentTeam() != selectedUnit.getCurrentTeam()) {
                moveAttackingUnit(selectedUnit, clickedTile);
            //if the user is selecting a location to summon an ally
            } else if (clickedTile.getUnit() == null && selectedUnit != null && selectedUnit.getState() == UnitState.SELECTING_SUMMON_LOC) {
                selectSummonLocation(clickedTile);
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
            unit.resetUnit();
        }

        for (Unit unit : unitsToAttack) { //moves units
            //if (unit.getEnemyToAttack() == null || unit.getAttackedBy() == null) continue;
            unit.move();
            Unit enemy = unit.getEnemyToAttack();
            enemy.takeDamage();
            enemy.setAttackedBy(null);

            unit.resetUnit();
            if (enemy.isDead()){
                currentLevel.removeUnit(enemy);
            }
        }

        for (Unit unit : unitsToSummon){
            currentLevel.placeUnit(unit, unit.getPosition().getX(), unit.getPosition().getY());
        }

        unitsToMove.clear();
        unitsToAttack.clear();
        unitsToSummon.clear();
        turnManager.switchTurn();
        resetUnit();

    }
}

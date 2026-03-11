package game.units;

import game.PlayerTurn;
import game.board.Tile;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Unit {

    private Tile position;
    private String name;
    private PlayerTurn currentTeam;

    private UnitType type;
    private UnitState state;

    private int currentHealth;
    private List<AttackType> attacks;

    public Unit(Tile position, String name, PlayerTurn currentTeam, UnitType type, UnitState state) {
        this.position = position;
        this.name = name;
        this.currentTeam = currentTeam;
        this.type = type;
        this.state = state;

        this.currentHealth = type.getMaxHealth();
        this.attacks = new ArrayList<>(type.getAttacks());
    }

    public PlayerTurn getCurrentTeam(){
        return currentTeam;
    }

    public void setCurrentTeam(PlayerTurn currentTeam){
        this.currentTeam = currentTeam;
    }

    public int getMoveRadius() {
        return type.getMoveRadius();
    }

    public boolean canSummon() {
        return type.canSummon();
    }

    public int getMaxHealth() {
        return type.getMaxHealth();
    }

    public UnitType getType(){
        return type;
    }

    public UnitState getState(){
        return state;
    }

    public void setState(UnitState state){
        this.state = state;
    }

    public Tile getPosition(){
        return position;
    }

    public void setPosition(Tile position){
        this.position = position;
    }

    public List<AttackType> getAttacks() {
        return attacks;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > type.getMaxHealth()) {
            currentHealth = type.getMaxHealth();
        }
    }

    public boolean canMove(Tile tile){
        int distance = Math.abs(tile.getX()-position.getX()) + Math.abs(tile.getY()-position.getY());
        return distance < getMoveRadius();
    }

    public void attack(Unit target){
        //how can player select an attack?
    }

    public void moveTo(Tile tile){
        position.removeUnit();
        position = tile;
        tile.setUnit(this);
        state = UnitState.IDLE;
    }
}
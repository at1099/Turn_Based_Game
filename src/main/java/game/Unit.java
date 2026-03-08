package game;

import java.util.ArrayList;
import java.util.List;

public class Unit {

    private Tile position;
    private String name;
    private boolean isAlly;

    private UnitType type;
    private UnitState state;

    private int currentHealth;
    private List<AttackType> attacks;

    public Unit(Tile position, String name, boolean isAlly, UnitType type, UnitState state) {
        this.position = position;
        this.name = name;
        this.isAlly = isAlly;
        this.type = type;
        this.state = state;

        this.currentHealth = type.getMaxHealth();
        this.attacks = new ArrayList<>(type.getAttacks());
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
package game.units;

import game.PlayerTurn;
import game.board.Tile;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;

public class Unit {

    private Tile position;
    private Tile destination;
    private Unit enemyToAttack;
    private boolean hasAttacked;
    private boolean hasMoved;
    private String name;
    private PlayerTurn currentTeam;

    private UnitType type;
    private UnitState state;

    private int currentHealth;
    private List<AttackType> attacks;

    public Unit(Tile position, String name, PlayerTurn currentTeam, UnitType type, UnitState state) {
        this.position = position;
        this.destination = null;
        this.enemyToAttack = null;
        this.hasMoved = false;
        this.hasAttacked = false;
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

    public Tile getDestination(){
        return destination;
    }

    public void setDestination(Tile destination){
        this.destination = destination;
    }

    public void setPosition(Tile position){
        this.position = position;
    }

    public List<AttackType> getAttacks() {
        return attacks;
    }

    public Unit getEnemyToAttack(){
        return enemyToAttack;
    }

    public void setEnemyToAttack(Unit enemyToAttack){
        this.enemyToAttack = enemyToAttack;
    }

    public boolean getHasAttacked(){
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked){
        this.hasAttacked = hasAttacked;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public boolean isDead(){
        return currentHealth == 0;
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

    public int getDistance(Tile destination){
        return (Math.abs(destination.getX()-position.getX()) + Math.abs(destination.getY()-position.getY()));
    }

    public boolean canMove(Tile tile){
        int distance = getDistance(tile);
        return (distance < getMoveRadius() && !hasMoved);
    }

    public void attack(Unit target){
        //how can player select an attack?
    }

    public boolean getHasMoved(){
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public void move(){
        position.removeUnit();
        position.updateHealthBar();

        position = destination;
        destination.setUnit(this);
        destination.updateHealthBar();

        state = UnitState.IDLE;
        hasMoved = true;
    }
}
package game;
import java.util.ArrayList;
import java.util.Arrays;

public class Unit {
    private int x;
    private int y;
    private String name;
    private boolean isAlly;
    private boolean canSummon;
    private final int maxHealth;
    private int currentHealth;
    private int moveRadius;
    private ArrayList<Attack> attacks;

    public Unit(int x, int y, String name, boolean isAlly, boolean canSummon, int maxHealth, int moveRadius, ArrayList<Attack> attacks){
        this.x = x;
        this.y = y;
        this.name = name;
        this.isAlly = isAlly;
        this.canSummon = canSummon;

        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.moveRadius = moveRadius;
        this.attacks = attacks;
    }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlly() {
        return isAlly;
    }

    public void setAlly(boolean ally) {
        isAlly = ally;
    }

    public int getMoveRadius() {
        return moveRadius;
    }

    public void setMoveRadius(int moveRadius) {
        this.moveRadius = moveRadius;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void addAttack(Attack newAttack) { //add/remove??
        attacks.add(newAttack);
    }

    public void removeAttack(int attackIndex) { //add/remove??
        attacks.remove(attackIndex);
    }

    public int getMaxHealth() {
        return maxHealth;
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
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }
}

class LightSoldier extends Unit{
    public LightSoldier(int x, int y, String name, boolean isAlly){
        super(x, y, name, isAlly, false, 200, 5, new ArrayList<>(Arrays.asList(new ShortSword(), new LongSword(), new CrossBow())));
    }
}

class LightArcher extends Unit{
    public LightArcher(int x, int y, String name, boolean isAlly){
        super(x, y, name, isAlly, false, 100, 7, new ArrayList<>(Arrays.asList(new ShortSword(), new CrossBow(), new LongBow())));
    }
}

class King extends Unit{
    public King(int x, int y, String name, boolean isAlly){
        super(x, y, name, isAlly, true, 500, 10, new ArrayList<>(Arrays.asList(new ShortSword(), new CrossBow(), new LongBow(), new Excalibur())));
    }
}
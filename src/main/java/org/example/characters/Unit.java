package org.example.characters;

public class Unit {
    public int maxHealth;
    public int healthRemaining = 10;
    public String name = "Soldier";
    public int maxMoveRadius = 2;
    public int x;
    public int y ;
    public int damage;
    public boolean isEnemy;

    public void goodUnitSetup(int maxHealth, int healthRemaining, int x, int y, int damage){
        this.maxHealth = maxHealth;
        this.healthRemaining = healthRemaining;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.isEnemy = false;
    }

    public void enemyUnitSetup(int maxHealth, int healthRemaining, int x, int y, int damage){
        this.maxHealth = maxHealth;
        this.healthRemaining = healthRemaining;
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.isEnemy = true;
    }

}

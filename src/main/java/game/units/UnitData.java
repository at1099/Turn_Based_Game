package game.units;

import game.PlayerTurn;

import java.io.Serializable;

public class UnitData implements Serializable {

    public int x;
    public int y;

    public String name;

    public PlayerTurn team;
    public UnitType type;

    public int currentHealth;

    public UnitData() {

    }
}
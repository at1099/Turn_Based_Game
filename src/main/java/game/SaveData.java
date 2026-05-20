package game;

import game.board.BuildingData;
import game.units.UnitData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveData implements Serializable {

    public List<UnitData> units = new ArrayList<>();
    public List<BuildingData> buildings = new ArrayList<>();

    public PlayerTurn currentTurn;
}
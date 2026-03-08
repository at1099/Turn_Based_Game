package game;

public enum TileType {
    GRASS(true, 1, 0),
    WATER(false, 0, 0),
    FOREST(true, 2, -20),
    MOUNTAIN(true, 2, 10);

    private boolean canWalkOn;
    private int movementCost;
    private int hitChanceChange;

    TileType(boolean canWalkOn, int movementCost, int hitChanceChange){
        this.canWalkOn = canWalkOn;
        this.movementCost = movementCost;
        this.hitChanceChange = hitChanceChange;
    }

    public boolean getWalkable(){
        return canWalkOn;
    }

    public int getMovementCost(){
        return movementCost;
    }

    public int getHitChanceChange(){
        return hitChanceChange;
    }
}

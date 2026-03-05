package game;

public class Location {
    private int x;
    private int y;
    private String name;
    private boolean regeneratesHealth;
    private int healthRegen;
    private boolean canSpawnUnits;
    private boolean isAlly;

    public Location(int x, int y, String name, boolean regeneratesHealth, int healthRegen, boolean canSpawnUnits, boolean isAlly){
        this.x = x;
        this.y = y;
        this.name = name;
        this.regeneratesHealth = regeneratesHealth;
        this.healthRegen = healthRegen;
        this.canSpawnUnits = canSpawnUnits;
        this.isAlly = isAlly;
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

    public boolean getRegeneratesHealth() {
        return regeneratesHealth;
    }

    public void setRegeneratesHealth(boolean regeneratesHealth) {
        this.regeneratesHealth = regeneratesHealth;
    }

    public int getHealthRegen() {
        return healthRegen;
    }

    public void setHealthRegen(int healthRegen) {
        this.healthRegen = healthRegen;
    }

    public boolean getCanSpawnUnits() {
        return canSpawnUnits;
    }

    public void setCanSpawnUnits(boolean canSpawnUnits) {
        this.canSpawnUnits = canSpawnUnits;
    }

    public boolean getIsAlly() {
        return isAlly;
    }

    public void setIsAlly(boolean isAlly) {
        this.isAlly = isAlly;
    }
}

class Castle extends Location{
    public Castle(int x, int y, String name, boolean isALly){
        super(x, y, name, false, 0, true, isALly);
    }
}

class Village extends Location{
    public Village(int x, int y, String name, boolean isALly){
        super(x, y, name, true, 25, false, isALly);
    }
}

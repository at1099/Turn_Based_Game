package game;

public enum AttackType {

    SHORT_SWORD(25, 2, 75, 1, 3),
    LONG_SWORD(50, 3, 50, 3, 2),
    CROSSBOW(10, 6, 50, 2, 4),
    LONGBOW(30, 15, 30, 5, 7),
    EXCALIBUR(100, 4, 60, 6, 2);

    private final int damage;
    private final int radius;
    private final int chanceToHit;
    private final int reloadTicks;
    private final int numStrikes;

    AttackType(int damage, int radius, int chanceToHit, int reloadTicks, int numStrikes) {
        this.damage = damage;
        this.radius = radius;
        this.chanceToHit = chanceToHit;
        this.reloadTicks = reloadTicks;
        this.numStrikes = numStrikes;
    }

    public int getDamage() {
        return damage;
    }

    public int getRadius() {
        return radius;
    }

    public int getChanceToHit() {
        return chanceToHit;
    }

    public int getReloadTicks() {
        return reloadTicks;
    }

    public int getNumStrikes() {
        return numStrikes;
    }
}

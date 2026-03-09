package game.units;

public class Attack {

    private AttackType type;
    private int cooldownRemaining;

    public Attack(AttackType type) {
        this.type = type;
        this.cooldownRemaining = 0;
    }

    public AttackType getType() {
        return type;
    }

    public int getDamage() {
        return type.getDamage();
    }

    public int getRadius() {
        return type.getRadius();
    }

    public int getChanceToHit() {
        return type.getChanceToHit();
    }

    public int getReloadTicks() {
        return type.getReloadTicks();
    }

    public int getNumStrikes() {
        return type.getNumStrikes();
    }

    public int getCooldownRemaining() {
        return cooldownRemaining;
    }

    public void incrementCooldownRemaining(){
        cooldownRemaining -= 1;
    }

    public void setCooldownRemaining(int cooldownRemaining) {
        this.cooldownRemaining = cooldownRemaining;
    }
}
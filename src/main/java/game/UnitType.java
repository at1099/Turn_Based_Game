package game;

import java.util.List;

public enum UnitType {

    LIGHT_SOLDIER(
            200,
            5,
            false,
            List.of(AttackType.SHORT_SWORD, AttackType.LONG_SWORD, AttackType.CROSSBOW)
    ),

    LIGHT_ARCHER(
            100,
            7,
            false,
            List.of(AttackType.SHORT_SWORD, AttackType.CROSSBOW, AttackType.LONGBOW)
    ),

    KING(
            500,
            10,
            true,
            List.of(AttackType.SHORT_SWORD, AttackType.CROSSBOW, AttackType.LONGBOW, AttackType.EXCALIBUR)
    );

    private final int maxHealth;
    private final int moveRadius;
    private final boolean canSummon;
    private final List<AttackType> attacks;

    UnitType(int maxHealth, int moveRadius, boolean canSummon, List<AttackType> attacks) {
        this.maxHealth = maxHealth;
        this.moveRadius = moveRadius;
        this.canSummon = canSummon;
        this.attacks = attacks;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMoveRadius() {
        return moveRadius;
    }

    public boolean canSummon() {
        return canSummon;
    }

    public List<AttackType> getAttacks() {
        return attacks;
    }
}
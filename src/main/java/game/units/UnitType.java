package game.units;
import game.Assets;
import javafx.scene.image.Image;

import java.util.List;

public enum UnitType {

    LIGHT_SOLDIER(
            200,
            5,
            false,
            List.of(AttackType.SHORT_SWORD, AttackType.LONG_SWORD, AttackType.CROSSBOW),
            null,
            Assets.KNIGHT
    ),

    LIGHT_ARCHER(
            100,
            7,
            false,
            List.of(AttackType.SHORT_SWORD, AttackType.CROSSBOW, AttackType.LONGBOW),
            null,
            Assets.ARCHER
    ),

    KING(
            500,
            10,
            true,
            List.of(AttackType.SHORT_SWORD, AttackType.CROSSBOW, AttackType.LONGBOW, AttackType.EXCALIBUR),
            List.of(LIGHT_ARCHER, LIGHT_SOLDIER),
            Assets.KING
    );

    private final int maxHealth;
    private final int moveRadius;
    private final boolean canSummon;
    private final List<AttackType> attacks;
    private final List<UnitType> summonableUnits;
    private Image image;

    UnitType(int maxHealth, int moveRadius, boolean canSummon, List<AttackType> attacks, List<UnitType> summonableUnits, Image image) {
        this.maxHealth = maxHealth;
        this.moveRadius = moveRadius;
        this.canSummon = canSummon;
        this.attacks = attacks;
        this.summonableUnits = summonableUnits;
        this.image = image;

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

    public List<UnitType> getSummonableUnits() {return summonableUnits;}

    public Image getImage() {
        return image;
    }
}
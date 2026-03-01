package game;

public class Attack {
    private int damage;
    private int radius;
    private int chanceToHit;
    private int reloadTicks;
    private int numStrikes;

    public Attack(int damage, int radius, int chanceToHit, int reloadTicks, int numStrikes) {
        this.damage = damage;
        this.radius = radius;
        this.chanceToHit = chanceToHit;
        this.reloadTicks = reloadTicks; //only count ally ticks
        this.numStrikes = numStrikes;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){ //is this needed??
        this.damage = damage;
    }

    public int getRadius(){
        return radius;
    }

    public void setRadius(int radius){ //is this needed??
        this.radius = radius;
    }

    public int getChanceToHit(){
        return chanceToHit;
    }

    public void setChanceToHit(int chanceToHit){ //is this needed??
        this.chanceToHit = chanceToHit;
    }

    public int getReloadTicks(){
        return reloadTicks;
    }

    public void setReloadTicks(int reloadTicks){ //is this needed??
        this.reloadTicks = reloadTicks;
    }

    public int getNumStrikes(){
        return numStrikes;
    }

    public void setNumStrikes(int numStrikes){ //is this needed??
        this.numStrikes = numStrikes;
    }
}

class ShortSword extends Attack{
    public ShortSword(){
        super(25, 2, 75, 1, 3);
    }
}

class LongSword extends Attack{
    public LongSword(){
        super(50, 3, 50, 3, 2);
    }
}

class CrossBow extends Attack{
    public CrossBow(){
        super(10, 6, 50, 2, 4);
    }
}

class LongBow extends Attack{
    public LongBow(){
        super(30, 15, 30, 5, 7);
    }
}

class Excalibur extends Attack{
    public Excalibur(){
        super(100, 4, 60, 6, 2);
    }
}

/*
class Shield extends Attack{
    public Shield(){
        super(10, 5, 50);
    }
}
*/
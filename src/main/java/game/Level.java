package game;

public class Level {
    public final int MAX_ENEMIES = 10; //for now
    public final int MAX_ALLIES = 10; //for now

    private Unit[][] allyList;
    private Unit[][] enemyList;
    private int currentAllyCount;
    private int currentEnemyCount;
    private String levelName;

    public GameMap map;

    private int currentNumTurns = 0;

    public Level(Unit[][] allyList, Unit[][] enemyList, int currentAllyCount, int currentEnemyCount, String levelName){
        this.allyList = allyList;
        this.enemyList = enemyList;
        this.currentAllyCount = currentAllyCount;
        this.currentEnemyCount = currentEnemyCount;
        this.levelName = levelName;
    }

    public Object[][] Initialise(){ //file input??
        return map;
    }
}

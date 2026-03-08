package game;

public class TurnManager {
    private PlayerTurn currentTurn;
    private int turnNumber;

    public TurnManager(PlayerTurn currentTurn){
        this.currentTurn = currentTurn;
        turnNumber = 0;
    }
    public int getTurnNumber(){
        return turnNumber;
    }

    public void incrementTurnNumber(){
        turnNumber += 1;
    }

    public PlayerTurn getCurrentTurn(){
        return currentTurn;
    }

    public void setCurrentTurn(PlayerTurn currentTurn){
        this.currentTurn = currentTurn;
    }
}

package game;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        int numAllies = 5;
        int numEnemies = 10;

        GameView view = new GameView();
        view.createGrid(stage, numAllies, numEnemies);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


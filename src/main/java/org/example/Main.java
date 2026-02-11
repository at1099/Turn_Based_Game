package org.example;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.ui.GameView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Stage gets javaFX to create the window
        GameView view = new GameView();
        view.createGrid(stage);

    }

    public static void main(String[] args) {
        launch();
    }
}

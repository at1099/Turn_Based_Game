package game;

import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage; //is the window
import javafx.scene.Scene; //one screen inside a window (e.g. main menu)
import javafx.scene.layout.VBox; //layout that stacks items vertically
import javafx.scene.control.Button; //lets you create buttons
import javafx.scene.control.Label; //allows you to display text
import javafx.geometry.Pos; //controls alignment of layouts
import javafx.scene.layout.GridPane; //lets you make gridPnaes

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Main extends Application { //main inherits behaviour from Application

    private Stage window; //stores current window so it can be changed
    private GameMap gameMap;
    private GameManager gameManager;

    private final int TILE_SIZE = 50;

    public static void main(String[] args) {
        launch(args); //starts javafx, creates ui thread and calls start()
    }

    @Override
    public void start(Stage primaryStage) { //where program actually begins, javafx provides a stage
        window = primaryStage; //save stage for later access
        window.setTitle("Fantasy Strategy Game"); //name

        Label title = new Label("Fantasy Strategy Game"); //creates text on screen
        Button startButton = new Button("Start Game"); //start game button

        startButton.setOnAction(e -> window.setScene(createGameScene())); //tells program to run showGameScene when button is clicked

        VBox menuLayout = new VBox(20); //creates layou with 20 pixel spacing between elements
        menuLayout.setAlignment(Pos.CENTER); //Centres everything in layout
        menuLayout.getChildren().addAll(title, startButton); //Adds items to layout

        Scene menuScene = new Scene(menuLayout, 800, 600); //creates scene

        window.setScene(menuScene); //sets scene as active scene
        window.show(); //shows scene
    }

    private Scene createGameScene() { //creates the board
        gameMap = new GameMap(10, 10);
        gameManager = new GameManager(gameMap);

        GridPane mapGrid = new GridPane();

        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                Tile tile = gameMap.getTile(x, y);

                Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                rect.setFill(Color.LIGHTGREEN);
                rect.setStroke(Color.BLACK);
                tile.setRectangle(rect);

                int finalX = x;
                int finalY = y;

                rect.setOnMouseClicked(e -> {
                    gameManager.handleTileClick(finalX, finalY);
                    redrawBoard();
                });


                StackPane cell = new StackPane();
                cell.getChildren().add(rect);

                mapGrid.add(cell, x, y);
            }
        }

        // place some example units
        Unit king = new Unit(gameMap.getTile(2, 2), "King", true, UnitType.KING, UnitState.IDLE);
        gameMap.placeUnit(king, 2, 2);

        redrawBoard(); // draw units

        return new Scene(mapGrid, 600, 600);
    }

    private void redrawBoard() {
        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                Tile tile = gameMap.getTile(x, y);
                Rectangle rect = tile.getRectangle();

                if (tile.isHighlighted()) {
                    rect.setFill(Color.YELLOW);
                } else {
                    rect.setFill(Color.LIGHTGREEN);
                }

                // TODO: draw unit images on top of rectangle
            }
        }
    }
}
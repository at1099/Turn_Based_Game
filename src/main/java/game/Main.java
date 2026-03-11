package game;

import game.board.Building;
import game.board.BuildingType;
import game.board.GameMap;
import game.board.Tile;
import game.units.Unit;
import game.units.UnitState;
import game.units.UnitType;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage; //is the window
import javafx.scene.Scene; //one screen inside a window (e.g. main menu)
import javafx.scene.layout.VBox; //layout that stacks items vertically
import javafx.scene.control.Button; //lets you create buttons
import javafx.scene.control.Label; //allows you to display text
import javafx.geometry.Pos; //controls alignment of layouts
import javafx.scene.layout.GridPane; //lets you make gridPnaes
import javafx.scene.text.Font;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.awt.*;

public class Main extends Application { //main inherits behaviour from Application

    private Stage window; //stores current window so it can be changed
    private GameMap gameMap;
    private GameManager gameManager;

    private final int TILE_SIZE = 50;

    public static void main(String[] args) {
        launch(args); //starts javafx, creates ui thread and calls start()
    }

    @Override
    //runs the main menu
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

    private Scene createGameScene() {

        int width = 10;
        int height = 10;
        gameMap = new GameMap(width, height);
        gameManager = new GameManager(gameMap);

        GridPane mapGrid = new GridPane();

        // Create tiles and add to GridPane ---
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = gameMap.getTile(x, y); // already created in GameMap

                // Add the StackPane from Tile to GridPane
                mapGrid.add(tile.getNode(), x, y);

                int finalX = x;
                int finalY = y;

                // Handle clicks for selection/movement
                tile.getNode().setOnMouseClicked(e -> {
                    gameManager.handleTileClick(finalX, finalY);
                    redrawBoard();
                });
            }
        }
        String turnText = gameManager.getTurnManager().getCurrentTurn().getText();
        //label to display whos turn it is
        Label turnLabel = new Label(turnText);
        turnLabel.setFont(new Font(20));
        //creates new hbox for it
        HBox topBar = new HBox(turnLabel);
        topBar.setPadding(new Insets(10));

        BorderPane root = new BorderPane(); //layout that divides the screen into 5 regions
                                            //called root because it is the highest level layout that stores other layouts inside it

        // create the end turn button
        Button endTurnButton = new Button("End Turn");
        endTurnButton.setStyle("-fx-font-size: 16px;"); //makes button bigger
        endTurnButton.setOnAction(e -> {
            gameManager.getTurnManager().switchTurn();
            String newTurn = gameManager.getTurnManager().getCurrentTurn().getText(); //changes text
            turnLabel.setText(newTurn);
            redrawBoard();
        });

        // container for the bottom buttons
        HBox bottomBar = new HBox(endTurnButton);

        // align button to the right
        bottomBar.setAlignment(Pos.BOTTOM_RIGHT);

        // add spacing from screen edges
        bottomBar.setPadding(new Insets(10));

        //put label in top left
        root.setTop(topBar);
        // put the board in the center
        root.setCenter(mapGrid);
        // put the button bar at the bottom
        root.setBottom(bottomBar);

        // Place static buildings (set once, no redraw needed)
        Tile castleTile = gameMap.getTile(3, 3);
        Building castle = new Building(BuildingType.CASTLE, castleTile);
        castleTile.setBuilding(castle);

        Tile villageTile = gameMap.getTile(6, 2);
        Building village = new Building(BuildingType.VILLAGE, villageTile);
        villageTile.setBuilding(village);

        //Place units
        Tile knightTile = gameMap.getTile(1, 1);
        Unit knight = new Unit(knightTile, "Knight", PlayerTurn.PLAYER, UnitType.LIGHT_SOLDIER, UnitState.IDLE);
        knightTile.setUnit(knight);

        Tile archerTile = gameMap.getTile(2, 1);
        Unit archer = new Unit(archerTile, "Archer", PlayerTurn.ENEMY, UnitType.LIGHT_ARCHER, UnitState.IDLE);
        archerTile.setUnit(archer);

        // Initial redraw (highlights/units) ---
        redrawBoard();

        return new Scene(root, width * TILE_SIZE, height * TILE_SIZE + 40);
    }

    private void redrawBoard() {
        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                Tile tile = gameMap.getTile(x, y);

                if(tile.getUnit() != null) {
                    if(tile.getUnit().getCurrentTeam() == PlayerTurn.PLAYER){
                        tile.setBorderHighlight(tile.HIGHLIGHT_GREEN);
                        //tile.setHighlightColour(tile.HIGHLIGHT_GREEN);
                    }else if(tile.getUnit().getCurrentTeam() == PlayerTurn.ENEMY){
                        tile.setBorderHighlight(tile.HIGHLIGHT_RED);
                }
                    //tile.setHighlightColour(tile.HIGHLIGHT_RED);
                }else{
                    //tile.clearBorderHighlight();
                    tile.setBorderHighlight(Color.TRANSPARENT);
                }

            }
        }
    }
}
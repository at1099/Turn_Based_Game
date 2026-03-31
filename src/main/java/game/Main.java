package game;

import game.board.Building;
import game.board.BuildingType;
import game.board.GameMap;
import game.board.Tile;
import game.units.*;

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
import java.util.List;
import java.util.ArrayList;

public class Main extends Application { //main inherits behaviour from Application

    private Stage window; //stores current window so it can be changed
    private GameMap gameMap;
    private GameManager gameManager;

    private Label nameLabel;
    private Label healthText;
    private Label instructionsLabel;

    private final int TILE_SIZE = 50;

    public Button[] sideButtons;
    public Button summonButton;
    public static void main(String[] args) {
        launch(args); //starts javafx, creates ui thread and calls start()
    }

    List<Unit> units = new ArrayList<>();


    @Override
    //runs the main menu
    public void start(Stage primaryStage) { //where program actually begins, javafx provides a stage
        window = primaryStage; //save stage for later access
        window.setTitle("Fantasy Strategy Game"); //name

        Label title = new Label("Fantasy Strategy Game"); //creates text on screen
        title.setFont(new Font(50));
        Button startButton = new Button("Start Game"); //start game button
        startButton.setPrefSize(400, 150);
        startButton.setFont(new Font(40));

        startButton.setOnAction(e -> window.setScene(createGameScene())); //tells program to run showGameScene when button is clicked

        VBox menuLayout = new VBox(20); //creates layou with 20 pixel spacing between elements
        menuLayout.setAlignment(Pos.CENTER); //Centres everything in layout
        menuLayout.getChildren().addAll(title, startButton); //Adds items to layout

        Scene menuScene = new Scene(menuLayout, 800, 600); //creates scene

        window.setScene(menuScene); //sets scene as active scene
        window.show(); //shows scene
    }

    private void createUnit(int x, int y, String name, PlayerTurn playerTurn, UnitType type) {
        /*switch (type){
            case KING:
                Tile knightTile = gameMap.getTile(x, y);
                Unit knight = new Unit(knightTile, "Knight", playerTurn, type, UnitState.IDLE);
                knightTile.setUnit(knight);
                units.add(knight);
                break;
            case LIGHT_SOLDIER:
                Tile kingTile = gameMap.getTile(x, y);
                Unit king = new Unit(kingTile, "Knight", playerTurn, type, UnitState.IDLE);
                kingTile.setUnit(king);
                units.add(king);
                break;
            case LIGHT_ARCHER:
                Tile archerTile = gameMap.getTile(x, y);
                Unit archer = new Unit(archerTile, "Archer", PlayerTurn.ENEMY, type, UnitState.IDLE);
                archerTile.setUnit(archer);
                units.add(archer);
                break;
        }*/

        Tile tile = gameMap.getTile(x,y);
        Unit unit = new Unit(tile, name, playerTurn, type, UnitState.IDLE);
        tile.setUnit(unit);
        units.add(unit);

    }

    private void createBuilding(int x, int y, BuildingType type) {
        /* switch(type){
            case CASTLE:
                Tile castleTile = gameMap.getTile(x, y);
                Building castle = new Building(type, castleTile);
                castleTile.setBuilding(castle);
                break;
            case VILLAGE:
                Tile villageTile = gameMap.getTile(x, y);
                Building village = new Building(type, villageTile);
                villageTile.setBuilding(village);
        } */

        Tile tile = gameMap.getTile(x, y);
        Building building = new Building(type, tile);
        tile.setBuilding(building);
    }

    private Scene createGameScene() {

        int width = 10;
        int height = 10;
        gameMap = new GameMap(width, height);
        gameManager = new GameManager(gameMap);


        String turnText = gameManager.getTurnManager().getCurrentTurn().getText();
        Color turnColour = gameManager.getTurnManager().getCurrentTurn().getColour();
        //label to display whos turn it is
        Label turnLabel = new Label(turnText);
        turnLabel.setFont(new Font(30));
        turnLabel.setTextFill(turnColour);

        //instruction label
        instructionsLabel = new Label("Select a unit or end turn");
        instructionsLabel.setFont(new Font(30));

        //creates new hbox for it
        HBox topBar = new HBox(turnLabel, instructionsLabel);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);

        BorderPane root = new BorderPane(); //layout that divides the screen into 5 regions
                                            //called root because it is the highest level layout that stores other layouts inside it

        // create the end turn button
        Button endTurnButton = new Button("End Turn");
        endTurnButton.setStyle("-fx-font-size: 48px;"); //makes button bigger
        endTurnButton.setPrefSize(300, 100);
        endTurnButton.setOnAction(e -> {
            for (int j = 0; j < sideButtons.length; j++) {
                sideButtons[j].setText(null);
                sideButtons[j].setOnMouseClicked(null);
            }
            gameManager.switchTurn();

            String newText = gameManager.getTurnManager().getCurrentTurn().getText(); //changes text
            Color newColour = gameManager.getTurnManager().getCurrentTurn().getColour();
            turnLabel.setTextFill(newColour);
            turnLabel.setText(newText);
            instructionsLabel.setText("Select a unit or end turn");
            redrawBoard();
        });

        summonButton = new Button("");
        summonButton.setStyle("-fx-font-size: 48px;");
        summonButton.setPrefSize(300, 100);

        // side buttons
        sideButtons = new Button[4];
        for (int i = 0; i < 4; i++) {
            sideButtons[i] = new Button("");
            sideButtons[i].setStyle("-fx-font-size: 48px;");
            sideButtons[i].setPrefSize(500, 150);
        }
        nameLabel = new Label();
        healthText = new Label();
        nameLabel.setFont(new Font(30));
        healthText.setFont(new Font(30));

        VBox sideBar = new VBox(10);
        sideBar.getChildren().addAll(nameLabel, healthText);
        sideBar.getChildren().addAll(sideButtons);
        sideBar.setAlignment(Pos.TOP_RIGHT);
        sideBar.setPadding(new Insets(10));

        // container for the bottom buttons
        HBox bottomBar = new HBox(summonButton, endTurnButton);
        bottomBar.setAlignment(Pos.BOTTOM_RIGHT); // align button to the right
        bottomBar.setPadding(new Insets(10));  // add spacing from screen edges
        bottomBar.setSpacing(10);

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
                    String newInstructionText = gameManager.handleTileClick(finalX, finalY);
                    instructionsLabel.setText(newInstructionText);
                    Unit selected = gameManager.getSelectedUnit();

                    for (int i = 0; i < sideButtons.length; i++) {
                        sideButtons[i].setText(null);
                        sideButtons[i].setOnMouseClicked(null);
                    }

                    if (selected != null && selected.getState() == UnitState.READY_TO_ATTACK) {

                        List<AttackType> attacks = selected.getAttacks();

                        for (int i = 0; i < attacks.size(); i++) {
                            int index = i;

                            sideButtons[index].setText(attacks.get(index).toString());
                            sideButtons[index].setOnMouseClicked(event -> {
                                for (int j = 0; j < sideButtons.length; j++) {
                                    sideButtons[j].setText(null);
                                    sideButtons[j].setOnMouseClicked(null);
                                }
                                gameManager.handleAttackClick(attacks.get(index));
                                instructionsLabel.setText("Select a unit or end turn");
                                redrawBoard();
                            }); //e renamed to event to not use twice
                        }
                    }

                    if (selected != null && selected.getCanCurrentlySummon()) {
                        summonButton.setText("Summon");

                        summonButton.setOnMouseClicked(event -> {
                            gameManager.handleSummonButtonClick();
                            instructionsLabel.setText("Select a location to summon a new unit");
                        });
                    } else{
                        summonButton.setText("");
                        summonButton.setOnMouseClicked(null);
                    }

                    if (selected != null && selected.getState() == UnitState.READY_TO_SUMMON) {
                        List<UnitType> summonableUnits = selected.getType().getSummonableUnits();

                        for (int i = 0; i < summonableUnits.size(); i++) {
                            int index = i;
                            sideButtons[index].setText(summonableUnits.get(index).toString());
                            sideButtons[index].setOnMouseClicked(event -> {
                                for  (int j = 0; j < sideButtons.length; j++) {
                                    sideButtons[j].setText(null);
                                    sideButtons[j].setOnMouseClicked(null);
                                }
                                gameManager.handleSummonClick(summonableUnits.get(index));
                                instructionsLabel.setText("Select a unit or end turn");
                                redrawBoard();
                            });
                        }
                    }

                    //change text
                    if (selected != null) {
                        nameLabel.setText(selected.getType().toString());
                        healthText.setText(
                                selected.getCurrentHealth() + " / " + selected.getMaxHealth()
                        );
                    } else {
                        nameLabel.setText("");
                        healthText.setText("");
                    }

                    redrawBoard();
                });
            }
        }

        //sidebar on the right
        root.setRight(sideBar);
        //put label in top left
        root.setTop(topBar);
        // put the board in the center
        root.setCenter(mapGrid);
        // put the button bar at the bottom
        root.setBottom(bottomBar);


        // Place static buildings (set once, no redraw needed)
        createBuilding(3,3, BuildingType.CASTLE);
        createBuilding(6,7, BuildingType.VILLAGE);

        //Place units
        createUnit(3,2, "Knight", PlayerTurn.PLAYER, UnitType.KING);
        createUnit(1,1 , "Knight", PlayerTurn.PLAYER, UnitType.LIGHT_SOLDIER);
        createUnit(4,4, "Archer", PlayerTurn.ENEMY, UnitType.LIGHT_ARCHER);

        // Initial draw (highlights/units)
        redrawBoard();

        return new Scene(root, width * TILE_SIZE, height * TILE_SIZE + 40);
    }

    private void redrawBoard() {
        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                Tile tile = gameMap.getTile(x, y);
                Unit unit = tile.getUnit();

                if (unit != null) {
                    tile.setBorderHighlight(unit.getCurrentTeam().getColour());
                    tile.updateHealthBar();

                    if (unit.getState() == UnitState.READY_TO_ATTACK && unit.getDestination() != null) {
                        unit.getDestination().setHighlightColour(unit.getCurrentTeam().getColour());
                    }

                } else {
                    tile.setBorderHighlight(Color.TRANSPARENT);
                    tile.updateHealthBar(); // hides health bar
                }
            }
        }

        for (Unit unit : gameManager.unitsToMove) {
            if (unit.getDestination() != null) {
                unit.getDestination().setHighlightColour(unit.getCurrentTeam().getColour());
            }
        }

        for (Unit unit: gameManager.unitsToAttack){
            if (unit.getDestination() != null){
                unit.getDestination().setHighlightColour(unit.getCurrentTeam().getColour());
            }
        }

        for (Unit unit: gameManager.unitsToSummon){
            unit.getPosition().setHighlightColour(unit.getCurrentTeam().getColour());
        }
    }
}
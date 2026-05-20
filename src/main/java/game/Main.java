package game;

import game.board.*;
import game.units.*;

import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage; //is the window
import javafx.scene.Scene; //one screen inside a window (e.g. main menu)
import javafx.scene.control.Button; //lets you create buttons
import javafx.scene.control.Label; //allows you to display text
import javafx.geometry.Pos; //controls alignment of layouts
import javafx.scene.text.Font;

import javafx.geometry.Insets;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public Button endTurnButton;
    public Button summonButton;
    public Button saveButton;
    public Button loadButton;
    public static void main(String[] args) {
        launch(args); //starts javafx, creates ui thread and calls start()
    }


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

    public void saveGame(){
        gameManager.saveGame();
    }

    public void loadGame(){
        gameManager.loadGame();
        redrawBoard();
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
        endTurnButton = new Button("End Turn");
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

        saveButton = new Button("Save");
        saveButton.setStyle("-fx-font-size: 48px;");
        saveButton.setPrefSize(300, 100);
        saveButton.setOnAction(e -> {saveGame();});

        loadButton = new Button("Load");
        loadButton.setStyle("-fx-font-size: 48px;");
        loadButton.setPrefSize(300, 100);
        loadButton.setOnAction(e-> {loadGame();});

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
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottomBar = new HBox(
                saveButton,
                loadButton,
                spacer,
                summonButton,
                endTurnButton
        );

        bottomBar.setPadding(new Insets(10));
        bottomBar.setSpacing(10);
        bottomBar.setAlignment(Pos.CENTER);

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

                    if (selected != null && selected.getState().equals(UnitState.READY_TO_ATTACK)){

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

                    if (selected != null && selected.getState().equals(UnitState.READY_TO_SUMMON)){
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
        gameManager.createBuilding(3,3, BuildingType.CASTLE);
        gameManager.createBuilding(6,7, BuildingType.VILLAGE);

        //Place units
        gameManager.createUnit(3,2, PlayerTurn.PLAYER, UnitType.KING);
        gameManager.createUnit(1,1 , PlayerTurn.PLAYER, UnitType.LIGHT_SOLDIER);
        gameManager.createUnit(4,4, PlayerTurn.ENEMY, UnitType.LIGHT_ARCHER);

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

                    if (unit.getState().equals(UnitState.READY_TO_ATTACK) && unit.getDestination() != null) {
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
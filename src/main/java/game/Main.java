package game;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.geometry.Insets;

import java.awt.*;

public class Main extends Application { //this means that it can use methods from the Application class

    Stage window;
    Scene MainMenu;
    Scene level1Scene;
    Button level1Button;

    public static void main(String[] args){
        launch(args); //runs the launch method in Application
    }

    public void Level1(){
        System.out.println("Level 1 started");
        GridPane gridPane = new GridPane(); //layout (in a grid)
        gridPane.setPadding(new Insets(10,10,10,10)); //makes a 10 pixel space between the window and gridpane
        gridPane.setVgap(10); //space between vertical squares
        gridPane.setHgap(10); //space between horiz squares

        Label instructions = new Label("Green square is one team, Blue square is another, Red is obstacle, Purple is village, Yellow is castle");

        //draw grid
        Level level1 = new Level();
        Object[][] grid = level1.initialise();

        String colour;
        for (int i = 0; i < grid.getWidth(); i++){
            for (int j = 0; j < grid.getHeight(); j++){
                switch (grid[i][j]){
                    case instanceof Unit:
                        if (grid[i][j].isAlly()){
                            colour = "Green";
                        }else{
                            colour = "Blue";
                        }
                    case instanceof Obstacle:
                        colour = "Red";
                    case instanceof Village:
                        colour = "Purple";
                    case instanceof Castle:
                        colour = "Yellow";
                    case instanceof null:
                        colour = "Grey"; //change to actually do the gridPnae
                } //make them all buttons how?
            }
        }

        level1Scene = new Scene(gridPane, 500, 500);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Main Menu");

        Label label = new Label("Welcome to the game!");
        level1Button = new Button("Level 1");
        level1Button.setOnAction(e -> Level1()); //makes the button run the Level1 method

        StackPane layout = new StackPane(); //how all buttons and labels are laid out
        layout.getChildren().add(level1Button); //adds item to layout

        MainMenu = new Scene(layout, 500, 500); //creates a new scene with the layout inside 500x500 pixels
        primaryStage.setScene(MainMenu); //sets the stage's contents to the scene
        primaryStage.show(); //shows it on the screen


    }

}


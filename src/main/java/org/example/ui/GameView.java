package org.example.ui;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.characters.Unit;

public class GameView {

    private static final int TILE_SIZE = 64;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    public Unit[][] mapArray = new Unit[WIDTH][HEIGHT];

    public void createGrid(Stage stage) {
        GridPane grid = new GridPane();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                StackPane tile = createTile(x, y);
                grid.add(tile, x, y);

            }
        }

        Scene scene = new Scene(grid);
        stage.setTitle("Turn-Based Strategy Game");
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createTile(int x, int y) {
        StackPane tile = new StackPane();
        tile.setPrefSize(TILE_SIZE, TILE_SIZE);

        if ((x + y) % 2 == 0) {
            tile.setStyle("-fx-background-color: #dcdcdc;");
        } else {
            tile.setStyle("-fx-background-color: #a9a9a9;");
        }

        // This method is run automatically as a reaction to the mouse being pressed
        tile.setOnMouseClicked(e ->
                System.out.println("Clicked tile: (" + x + ", " + y + ")")
        );

        return tile;
    }

}

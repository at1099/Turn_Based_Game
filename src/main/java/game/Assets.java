package game;

import javafx.scene.image.Image;

public class Assets {

    public static final Image GRASS =
            new Image(Assets.class.getResource("/images/tiles/grass.png").toExternalForm());
    public static final Image WATER =
            new Image(Assets.class.getResource("/images/tiles/water.png").toExternalForm());
    public static final Image MOUNTAIN =
            new Image(Assets.class.getResource("/images/tiles/mountain.png").toExternalForm());

    public static final Image KING =
            new Image(Assets.class.getResource("/images/units/king.png").toExternalForm());
    public static final Image KNIGHT =
            new Image(Assets.class.getResource("/images/units/knight.png").toExternalForm());
    public static final Image ARCHER =
            new Image(Assets.class.getResource("/images/units/archer.png").toExternalForm());

    public static final Image VILLAGE =
            new Image(Assets.class.getResource("/images/buildings/village.png").toExternalForm());
    public static final Image CASTLE =
            new Image(Assets.class.getResource("/images/buildings/castle.png").toExternalForm());
}
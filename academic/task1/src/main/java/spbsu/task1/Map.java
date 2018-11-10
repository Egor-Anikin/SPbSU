package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** The Map of Game. */
public class Map {

    private final GraphicsContext gc;
    private final Image background;

    /** Create Map. */
    public Map(GraphicsContext gc) {
        this.gc = gc;
        background = new Image("background.png");
    }

    /** Draw Map. */
    public void draw() {
        gc.drawImage(background, 0, 0);
    }

    public boolean checkGroundY(Coordinates object) {
        return object.getY() <= GroundY(object.getX());
    }

    public void getGroundY(Coordinates object){
        object.setY(GroundY(object.getX()));
    }

    private static int GroundY(int x){
        return 10;
    }

}


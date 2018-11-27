package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** The Map of Game. */
public class Map {
    private final GraphicsContext gc;
    private final Image background;

    public Map(GraphicsContext gc) {
        this.gc = gc;
        background = new Image("background.png");
    }

    /** Draw Map. */
    public void draw() {
        gc.drawImage(background, 0, 0);
    }

    /** Y coordinate of the earth. */
    public static int GroundY(int x) {
        int[] X = {0, 205, 405, 700, 830, 990, 1260, 1360};
        int[] Y = {412, 480, 380, 490, 445, 480, 395, 415};

        if (x <= 0) {
            return 0;
        }

        for(int i = 1; i < 8; i++) {
            if (x <= X[i])
                return Y[i - 1] + (Y[i] - Y[i - 1]) * (x - X[i - 1]) / (X[i] - X[i - 1]);
        }

        return 0;
    }
}


package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletSmall extends  Bullet {
    private static final int MASS_BULLET = 160;
    protected static final int BULLET_SIZE = 40;

    public BulletSmall(GraphicsContext gc, int x, int y, int fi) {
        this.gc = gc;
        this.x = x;
        this.y = y;

        bullet = new Image("small.png");

        begin(fi);
    }
}

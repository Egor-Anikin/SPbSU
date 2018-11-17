package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletBig extends Bullet {
    private static final int MASS_BULLET = 160;
    protected static final int BULLET_SIZE = 40;

    public BulletBig(GraphicsContext gc, int x, int y, int fi) {
        this.gc = gc;
        this.x = x;
        this.y = y;

        bullet = new Image("big.png");

        begin(fi);
    }
}

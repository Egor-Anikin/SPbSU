package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**Big bullet class. */
public class BulletBig extends Bullet {
    public BulletBig(GraphicsContext gc, int x, int y, int fi) {
        this.gc = gc;
        setX(x);
        setY(y);

        MASS_BULLET = 160;
        BULLET_SIZE = 50;
        begin(fi);

        bullet = new Image("big.png");
    }
}

package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletBig extends Bullet {

    public BulletBig(GraphicsContext gc, int x, int y, int fi) {
        this.gc = gc;
        this.x = x;
        this.y = y;

        MASS_BULLET = 160;
        BULLET_SIZE = 40;
        begin(fi);

        bullet = new Image("big.png");


    }
}

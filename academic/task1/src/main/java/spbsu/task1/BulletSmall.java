package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BulletSmall extends  Bullet {

    public BulletSmall(GraphicsContext gc, int x, int y, int fi) {
        this.gc = gc;
        this.x = x;
        this.y = y;

        MASS_BULLET = 100;
        BULLET_SIZE = 40;
        begin(fi);

        bullet = new Image("small.png");

    }
}

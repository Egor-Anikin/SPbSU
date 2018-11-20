package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/** Cannon class. */
public class Cannon implements Coordinates{
    private static final int MAX_WIDTH = 1360;
    private static final int CANNON_X_SIZE = 100;
    private static final int CANNON_Y_SIZE = 45;//22
    private static final int HEAD = -2;
    private static final int HEAD_SIZE = 15;
    private static final int MUZZEL_SIZE = 40;
    private final static int DISTANS = 20;
    private final GraphicsContext gc;
    private final Image cannon;


    private int x;
    private int y;
    private int fi;

    public Cannon(GraphicsContext gc, int x, int y, Image cannon) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.fi = 90;

        this.cannon = cannon;
    }

    public void angleLeft() {
        if( fi == 360) {
            fi = 0;
        }
            fi++;
    }

    public void angleRight() {
        if( fi == 0) {
            fi = 360;
        }
        fi--;
    }
    /** Draw Turret. */
    public void draw() {
        gc.drawImage(cannon, x - CANNON_X_SIZE / 2, y - CANNON_Y_SIZE / 2);

        gc.setLineWidth(5);// Дуло с заданным углом
        gc.setStroke(Color.rgb(0, 0, 0));
        gc.strokeLine(x+ Math.cos(Math.PI * fi / 180) * HEAD_SIZE, y + HEAD - Math.sin(Math.PI * fi / 180) * HEAD_SIZE,
                x + Math.cos(Math.PI * fi / 180) * MUZZEL_SIZE, y + HEAD - Math.sin(Math.PI * fi / 180) * MUZZEL_SIZE);
    }

    public Bullet fireBig(){
        return new BulletBig(gc, x + (int)(Math.cos(Math.PI * fi / 180) * (MUZZEL_SIZE + DISTANS)),
                y + HEAD - (int)(Math.sin(Math.PI * fi / 180) * (MUZZEL_SIZE + DISTANS)), fi);
    }

    public Bullet fireSmall(){
        return new BulletSmall(gc, x + (int)(Math.cos(Math.PI * fi / 180) * (MUZZEL_SIZE + DISTANS)),
                y + HEAD - (int)(Math.sin(Math.PI * fi / 180) * (MUZZEL_SIZE + DISTANS)), fi);
    }

    public int getFi() {
        return fi;
    }

    public void setFi (int fi) {
        this.fi = fi;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        if(x > 0 && x < MAX_WIDTH)
        this.x = x;
    }
    @Override
    public void setY(int y) {
        this.y = y;
    }
}

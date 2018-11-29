package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/** Cannon class. */
public class Cannon implements Coordinates{
    private static final int MAX_WIDTH = 1360;
    private static final int CANNON_X_SIZE = 100;
    private static final int CANNON_Y_SIZE = 45;
    private static final int HEAD = -2;
    private static final int HEAD_SIZE = 15;
    private static final int MUZZLE_SIZE = 40;
    private static final int DISTANCE = 20;
    private final GraphicsContext gc;
    private final Image cannon;

    private int x;
    private int y;
    private int angle;

    public Cannon(GraphicsContext gc, int x, int y, Image cannon) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.angle = 90;

        this.cannon = cannon;
    }

    /** Left angle. */
    public void angleLeft() {
        if (angle == 360) {
            angle = 0;
        }
        angle++;
    }

    /** Right angle. */
    public void angleRight() {
        if (angle == 0) {
            angle = 360;
        }
        angle--;
    }
    /** Draw Cannon. */
    public void draw() {
        gc.drawImage(cannon, x - CANNON_X_SIZE / 2, y - CANNON_Y_SIZE / 2);

        gc.setLineWidth(5);
        gc.setStroke(Color.rgb(0, 0, 0));
        gc.strokeLine(x+ Math.cos(Math.PI * angle / 180) * HEAD_SIZE, y + HEAD - Math.sin(Math.PI * angle / 180) * HEAD_SIZE,
                x + Math.cos(Math.PI * angle / 180) * MUZZLE_SIZE, y + HEAD - Math.sin(Math.PI * angle / 180) * MUZZLE_SIZE);
    }

    /** Big bullet fire. */
    public Bullet fireBig() {
        return new BulletBig(gc, x + (int)(Math.cos(Math.PI * angle / 180) * (MUZZLE_SIZE + DISTANCE)),
                y + HEAD - (int)(Math.sin(Math.PI * angle / 180) * (MUZZLE_SIZE + DISTANCE)), angle);
    }

    /** Small bullet fire. */
    public Bullet fireSmall() {
        return new BulletSmall(gc, x + (int)(Math.cos(Math.PI * angle / 180) * (MUZZLE_SIZE + DISTANCE)),
                y + HEAD - (int)(Math.sin(Math.PI * angle / 180) * (MUZZLE_SIZE + DISTANCE)), angle);
    }

    /** Get angle. */
    public int getAngle() {
        return angle;
    }

    /** Set angle. */
    public void setAngle(int angle) {
        this.angle = angle;
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
        if (x > 0 && x < MAX_WIDTH)
            this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}

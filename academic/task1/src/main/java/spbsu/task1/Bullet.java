package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** Bullet class. */
public abstract class Bullet implements Coordinates {
    protected static final int MAX_WIDTH = 1360;
    protected static int BULLET_SIZE;
    protected static final double FORCE_CANNON = 1000;
    protected static int MASS_BULLET;
    protected static final double GRAVITATION = 0.1;

    protected GraphicsContext gc;
    protected Image bullet;

    private boolean exist = true;
    private int x;
    private int y;
    private double vx;
    private double vy;

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
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    /** Bullet existence. */
    public boolean exist() {
        return exist;
    }


    /** Draw bullet. */
    public void draw() {
        gc.drawImage(bullet, getX() - BULLET_SIZE / 2, getY() - BULLET_SIZE / 2);
        move();
    }

    protected void begin(int angle) {
        double v0 = FORCE_CANNON / MASS_BULLET;
        vx = v0 * Math.cos(Math.PI * angle / 180);
        vy = v0 * Math.sin(Math.PI * angle / 180);
    }

    protected void remove() {
        exist = false;
    }

    protected double getVx() {
        return vx;
    }

    protected double getVy() {
        return vy;
    }

    protected void setVy(double vy) {
        this.vy = vy;
    }

    protected void move() {
        if (x < 0 || x > MAX_WIDTH || y >= Map.GroundY(x)) {
            remove();
            return;
        }

        setX(getX() + (int) getVx());
        setVy(getVy() - GRAVITATION);
        setY(getY() - (int) getVy());
    }
}


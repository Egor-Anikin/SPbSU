package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Bullet implements Coordinates{
    protected static final int MAX_WIDTH = 1360;
    protected static final int MAX_HEIGHT = 765;
    protected static int BULLET_SIZE = 20;
    protected static final int FORCE_CANNON = 100;
    protected static int MASS_BULLET = 80;
    protected static final float GRAVITATION = 10;

    protected GraphicsContext gc;
    protected Image bullet;

    protected boolean remuv = false;
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;

    /*public Bullet(GraphicsContext gc, int x, int y, int fi) {
        this.gc = gc;
        this.x = x;
        this.y = y;

        begin(fi);
    }*/

    protected void begin(int fi) {
        int v0 = FORCE_CANNON/MASS_BULLET;
        vx = v0 * Math.cos(Math.PI * fi / 180);
        vy = v0 * Math.sin(Math.PI * fi / 180);
    }

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int)y;
    }

    @Override
    public void setX(int x) {
        if(x > 0 && x < MAX_WIDTH )
            this.x = x;
    }
    @Override
    public void setY(int y) {
        this.y = y;
    }

    public void draw() {
        if (x < 0 || x > MAX_WIDTH || y < 0 || y > MAX_HEIGHT ) {
            remuv();
            return;
        }

        gc.drawImage(bullet, (int) x - BULLET_SIZE / 2, (int) y - BULLET_SIZE / 2);

        x += vx;
        vy -= GRAVITATION;
        y += vy;

    }

    public boolean isRemuv(){
        return remuv;
    }

    private void remuv (){
        remuv = true;
    }
}


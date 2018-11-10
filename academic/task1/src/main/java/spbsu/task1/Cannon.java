package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/** Cannonclass. */
public class Cannon implements Coordinates{
    private static final int MAX_WIDTH = 1360;
    private static final int CANNON_SIZE = 40;
    private static final int MUZZEL_SIZE = 40;
    private final GraphicsContext gc;
    private final Image cannon;

    private int x;
    private int y;
    private int fi;

    public Cannon(GraphicsContext gc, int x, int y) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.fi = 90;

        turret = new Image("turret.png");
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
        gc.setLineWidth(5);// Дудо с заданным углом
        gc.setStroke(Color.rgb(195, 195, 195));
        gc.strokeLine(x, y, x + Math.sin(Math.PI * fi / 180) * MUZZEL_SIZE, y + Math.cos(Math.PI * fi / 180) * MUZZEL_SIZE);

        gc.drawImage(turret, x - CANNON_SIZE / 2, y - CANNON_SIZE/ 2);
    }

    public Bullet fire(){
        return new Bullet(gc,x + (int)Math.sin(Math.PI * fi / 180) * MUZZEL_SIZE,
                y +(int)Math.cos(Math.PI * fi / 180) * MUZZEL_SIZE, fi);
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
        if(x > 0 && x < MAX_WIDTH )
        this.x = x;
    }
    @Override
    public void setY(int y) {
        this.y = y;
    }
}

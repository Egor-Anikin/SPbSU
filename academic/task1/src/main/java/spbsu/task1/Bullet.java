package spbsu.task1;

public class Bullet implements Coordinates{
    private static final int MAX_WIDTH = 1360;
    private static final int MAX_HEIGHT = 765;
    private static final int BULLET_SIZE = 20;
    private static final int MASS_BULLET = 82;
    private static final int FORCE_CANNON = 100;
    private static final float GRAVITATION = 10;

    private final GraphicsContext gc;
    private final Image bullet;

    private boolean remuv = false;
    private double x;
    private double y;
    private double vx;
    private double vy;

    public Bullet(GraphicsContext gc, int x, int y, int fi) {
        this.gc = gc;
        this.x = x;
        this.y = y;

        int v0 = FORCE_CANNON/MASS_BULLET;
        vx = v0 * Math.cos(Math.PI * fi / 180);
        vy = v0 * Math.sin(Math.PI * fi / 180);

        bullet = new Image("bullet.png");
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


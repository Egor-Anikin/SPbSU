package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;

public class Converter {
    private final int MUZZEL_SIZE = 40;
    private int x;
    private int y;
    private int fi;
    private int type;

    public void ConvertInfo(int info, Map map ) {
        x = info & (~2047) >> 11;
        fi = (info & 2044) >> 2;
        type = info & 3;
        y = map.GroundY(x);
    }

    public void setCannon(Cannon blue){
            blue.setX(x);
            blue.setY(y);
            blue.setFi(fi);
    }

    public Bullet getBullet(GraphicsContext gc) {
        return type ==0 ? null : (type == 2 ? new BulletBig(gc, x + (int)Math.cos(Math.PI * fi / 180) * MUZZEL_SIZE,
                y + (int)Math.sin(Math.PI * fi / 180) * MUZZEL_SIZE ,fi) : new BulletSmall(gc,
                x + (int)Math.cos(Math.PI * fi / 180) * MUZZEL_SIZE, y + (int)Math.sin(Math.PI * fi / 180)
                * MUZZEL_SIZE ,fi));
    }

    public int getInfo(int x, int fi, int type) {
        return type + (fi << 2) + (x << 11);
    }
}

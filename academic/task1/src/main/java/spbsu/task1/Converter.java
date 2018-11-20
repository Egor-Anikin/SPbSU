package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;

public class Converter {
    private static final int MUZZEL_SIZE = 40;
    private static final int DISTANS = 20;


    public static void setCannon(Cannon blue, long info){
            blue.setX(getX(info));
            blue.setY(Map.GroundY(getX(info)));
            blue.setFi(getFi(info));
    }

    public static Bullet getBullet(GraphicsContext gc, long info) {
        if( getType(info) == 0 ) {
            return null;
        } else {

            Bullet bullet;
            int y0 = Map.GroundY(getX0(info));

            if (getType(info) == 2) {
                 bullet = new BulletBig(gc, getX0(info) + (int) Math.cos(Math.PI * getFi0(info) / 180) * (MUZZEL_SIZE + DISTANS),
                        y0 + (int) Math.sin(Math.PI * getFi0(info) / 180) * (MUZZEL_SIZE + DISTANS), getFi0(info));
            } else {
                 bullet =  new BulletSmall(gc, getX0(info) + (int)Math.cos(Math.PI * getFi0(info) / 180) * (MUZZEL_SIZE + DISTANS),
                        y0 + (int)Math.sin(Math.PI * getFi0(info) / 180) * (MUZZEL_SIZE + DISTANS), getFi0(info));
            }

            for(int i = 0; i < getTime(info); i++);
            {
                bullet.next();
            }

            return bullet;
        }
    }

    public static int getType(long info ) {
        return codoff(info, 2, 0);
    }

    public static int getFi(long info){
        return codoff(info, 9, 2);
    }

    public static int getX(long info){
        return codoff(info, 11, 11);
    }

    public static int getX0(long info ){
        int dx =  codoff(info, 5, 22);

        if(dx > 10) {
            dx = -(dx - 10);
        }

        return getX(info) + dx;
    }

    public static int getFi0(long info ){
        int dfi = codoff(info, 5, 27);

        if(dfi > 10) {
            dfi = -(dfi - 10);
        }

        return getFi(info) + dfi;
    }

    public static int getTime(long info ){
        return codoff(info, 4, 32);
    }


    public static long getInfo(int x, int fi, int type, int dx, int dfi, int time) {
        int n = 0;

        if(dx < 0) {
            dx = -dx + 10;
        }

        if(dfi < 0) {
            dfi = -dfi + 10;
        }

        long info = codon(type, 2, n);
        n += 2;
        info = info | codon(fi, 9, n);
        n += 9;
        info = info | codon(x, 11, n);
        n += 11;

        if (type != 0) {
            info = info | codon(dx, 5, n);
            n += 5;
            info = info | codon(dfi, 5, n);
            n += 5;
            info = info | codon(time, 4, n);
        }

        return  info;
    }

    private static int codoff (long info, int symbol, int order) {
        return (int)((info >> order) & (int)Math.pow(2,symbol) - 1);
    }

    private static int codon (int x, int symbol, int order)
    {
        return (x & (int)Math.pow(2,symbol) - 1) >> order;
    }
}

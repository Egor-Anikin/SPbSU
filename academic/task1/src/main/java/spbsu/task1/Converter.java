package spbsu.task1;

import javafx.scene.canvas.GraphicsContext;

public class Converter {
    private static final int MUZZEL_SIZE = 40;
    private static final int DISTANS = 20;


    public static void setCannon(Cannon blue, int info1){
            blue.setX(getX(info1));
            blue.setY(Map.GroundY(getX(info1)));
            blue.setFi(getFi(info1));
    }

    public static Bullet getBullet(GraphicsContext gc, int info1, int info2) {
        if (getType(info2) == 0) {
            return null;
        } else {

            Bullet bullet;
            int y0 = Map.GroundY(getX0(info1,info2));

            if (getType(info2) == 2) {
                 bullet = new BulletBig(gc, getX0(info1,info2) + (int) Math.cos(Math.PI * getFi0(info1,info2) / 180) * (MUZZEL_SIZE + DISTANS),
                        y0 + (int) Math.sin(Math.PI * getFi0(info1,info2) / 180) * (MUZZEL_SIZE + DISTANS), getFi0(info1,info2));
            } else {
                 bullet =  new BulletSmall(gc, getX0(info1,info2) + (int) Math.cos(Math.PI * getFi0(info1,info2) / 180) * (MUZZEL_SIZE + DISTANS),
                         y0 + (int) Math.sin(Math.PI * getFi0(info1,info2) / 180) * (MUZZEL_SIZE + DISTANS), getFi0(info1,info2));
            }

            for(int i = 0; i < getTime(info2); i++);
            {
                bullet.move();
            }

            return bullet;
        }
    }

    public static int getInfo1(int x, int fi) {
        int n = 0;
        int info = codon(fi, 9, n);
        n += 9;
        info = info | codon(x, 11, n);
        n += 11;
        return  info;
    }

    public static int getInfo2( int type, int dx, int dfi, int time) {
        int n = 0;
        int dx1 = dx;
        int dfi1 = dfi;


        if (type == 0) {
            return 0;
        }


        if(dx < 0) {
             dx1 = -dx + 10;
        }

        if(dfi < 0) {
             dfi1 = -dfi + 10;
        }

        int info = codon(type, 2, n);
        n += 2;
        info = info |codon(dx1, 5, n);
        n += 5;
        info = info | codon(dfi1, 5, n);
        n += 5;
        info = info | codon(time, 4, n);
        return  info;
    }



    private static int getFi(int info){
        return codoff(info, 9, 0);
    }

    private static int getX(int info){
        return codoff(info, 11, 9);
    }

    private static int getType(int info ) {
        return codoff(info, 2, 0);
    }

    private static int getX0(int info1, int info2 ){
        int dx =  codoff(info2, 5, 2);

        if(dx > 10) {
            dx = -(dx - 10);
        }

        return getX(info1) + dx;
    }

    private static int getFi0(int info1, int info2 ){
        int dfi = codoff(info2, 5, 7);

        if(dfi > 10) {
            dfi = -(dfi - 10);
        }

        return getFi(info1) + dfi;
    }

    private static int getTime(int info ){
        return codoff(info, 4, 12);
    }

    private static int codoff (int info, int symbol, int order) {
        return (int)((info >> order) & (int)Math.pow(2, symbol) - 1);
    }

    private static int codon (int x, int symbol, int order) {
        return (x & (int)Math.pow(2, symbol) - 1) >> order;
    }
}

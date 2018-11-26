package spbsu.task1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.canvas.Canvas;

import java.util.LinkedList;

/** The Game class. */
public class  Game extends Application {
    private static final int WIDTH = 1360;
    private static final int HEIGHT = 765;
    private static final int SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    private static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();

    private static final int CHECK_UPDATES = 1;

    private int time = CHECK_UPDATES;
    private int type = 0;
    private boolean faire = true;

    private static Network network = new Network();

    /** Start The Game. */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cannon");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        LinkedList<String> keys = keyboardSettings(scene);

        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.scale((double) SCREEN_WIDTH / WIDTH, (double) SCREEN_HEIGHT / HEIGHT);

        gameMechanics(primaryStage, gc, keys);
        primaryStage.show();
    }

    /** Main function. */
    public static void main(String[] args) {

        launch(args);
        //Converter.test();
    }

    private LinkedList<String> keyboardSettings(Scene scene) {
        LinkedList<String> input = new LinkedList<>();

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();

                        if (!input.contains(code)) {
                            input.add(code);
                        }
                    }
                });

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        String code = e.getCode().toString();
                        input.remove(code);
                    }
                });

        return input;
    }

    private void gameMechanics(final Stage primaryStage, GraphicsContext gc, final LinkedList<String> keys) {
        final Map map = new Map(gc);

        final Cannon red;
        final Cannon blue;
        if(network.isServer()) {
            red = new Cannon(gc, 1000, 0, new Image("gun_1.2.png"));
            blue = new Cannon(gc, 100, 0, new Image("gun_2.2.png"));
        } else {
            blue = new Cannon(gc, 1000, 0, new Image("gun_1.2.png"));
           red = new Cannon(gc, 100, 0, new Image("gun_2.2.png"));
        }

        red.setY(map.GroundY(red.getX()));
        blue.setY(map.GroundY(blue.getX()));

        final LinkedList<Bullet> bullets = new LinkedList<>();


        new AnimationTimer() {

            public void handle(long currentNanoTime) {

                type = 0;

                if (keys.contains("LEFT")) {
                    red.setX(red.getX() - 1);
                    red.setY(map.GroundY(red.getX()));
                }

                if (keys.contains("RIGHT")) {
                    red.setX(red.getX() + 1);
                    red.setY(map.GroundY(red.getX()));
                }

                if (keys.contains("UP")) {
                    red.angleLeft();
                }

                if (keys.contains("DOWN")) {
                    red.angleRight();
                }

                if (keys.contains("ENTER")) {
                    if(faire) {
                        faire = false;
                        type = 2;

                        bullets.add(red.fireSmall());
                        keys.remove("ENTER");
                    }
                }

                /*if (keys.contains("M")) {
                    bullets.add(red.fireBig());
                    keys.remove("M");
                }

                if (keys.contains("A")) {
                    blue.setX(blue.getX() - 1);
                    blue.setY(map.GroundY(blue.getX()));
                }

                if (keys.contains("D")) {
                    blue.setX(blue.getX() + 1);
                    blue.setY(map.GroundY(blue.getX()));
                }

                if (keys.contains("W")) {
                    blue.angleLeft();
                }

                if (keys.contains("S")) {
                    blue.angleRight();
                }

                if (keys.contains("SPACE")) {
                    if(faire) {
                        faire = false;
                        bullets.add(blue.fireSmall());
                        keys.remove("SPACE");
                    }
                }*/

                if (keys.contains("SPACE")) {
                    if(faire) {
                        faire = false;
                        type = 1;
                        bullets.add(red.fireBig());

                        keys.remove("SPACE");
                    }
                }

                if (keys.contains("ESCAPE")) {
                    primaryStage.close();
                }
                map.draw();
                red.draw();
                blue.draw();


                for (Bullet bullet : bullets) {
                    if (bullet.getY() >= map.GroundY(bullet.getX()) || bullet.isRemove()) {
                        bullets.remove(bullet);
                    }else if (checkHit(bullet,red)) {
                        primaryStage.close();
                        System.out.println("Blue win");
                    }  else if (checkHit(bullet,blue)) {
                        primaryStage.close();
                       System.out.println("Red win");
                    } else {
                      bullet.draw();
                    }
                }

                if (time == CHECK_UPDATES) {
                    //int info1 = Converter.getInfo1(red.getX(), red.getFi());
                    //int info2 = Converter.getInfo2(type, x0 - red.getX(), fi0 -red.getFi(),time - time0 );
                    blue.setX( network.synchronization(red.getX()));
                    blue.setY(map.GroundY(blue.getX()));
                    blue.setFi(network.synchronization(red.getFi()));
                    type = network.synchronization(type);
                    if(type == 1){
                        blue.fireSmall();
                    } else if (type == 2) {
                        blue.fireBig();
                    }

                    //info2 = network.synchronization(info2);
                    //Converter.setCannon(blue, info1);
                    //bullets.add(Converter.getBullet(gc, info1, info2));
                    time = 0;
                    faire = true;
                }
                else {
                    time++;
                }

            }
        }.start();
    }

    private boolean checkHit(Bullet bullet, Cannon red)
    {
        if(bullet instanceof BulletBig) {
            return Math.sqrt(Math.pow(bullet.getX() - red.getX(), 2) + Math.pow(bullet.getY() - red.getY(), 2)) <= 40;
        } else {
            return Math.sqrt(Math.pow(bullet.getX() - red.getX(), 2) + Math.pow(bullet.getY() - red.getY(), 2)) <= 30;
        }
    }
}
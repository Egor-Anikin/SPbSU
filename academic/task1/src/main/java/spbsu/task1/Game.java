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
public class Game extends Application {
    private static final int WIDTH = 1360;
    private static final int HEIGHT = 765;
    private static final int SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    private static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();

    private static final int CHECK_UPDATES = 10;

    private int time = CHECK_UPDATES;
    private int time0 = 0;
    private int x0 = 0;
    private int fi0 = 0;
    private int type = 0;
    private boolean faire = true;

    private Network network = new Network();

    /** Start The Game. */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cannon");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);// Заклинание
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
    }

    private LinkedList<String> keyboardSettings(Scene scene) {  // Важные заклинание ничего не менять
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
        final Cannon red = new Cannon(gc, 1000, 0, new Image("gun12.png"));
        red.setY(map.GroundY(red.getX()));

        final Cannon blue = new Cannon(gc, 100, 0, new Image("gun22.png"));
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
                        type = 1;
                        x0 = red.getX();
                        fi0 = red.getFi();
                        time0 = time;
                        bullets.add(red.fireSmall());
                        //int info = converter.getInfo(red.getX(),red.getFi(),2);
                        //info = (network.synchronization(info));
                        //converter.ConvertInfo(info, map);
                        //converter.setCannon(blue);
                        //bullets.add(converter.getBullet(gc));
                        //wait = CHECK_UPDATES;
                        keys.remove("ENTER");
                    }
                }

                if (keys.contains("SPACE")) {
                    if(faire) {
                        faire = false;
                        faire = false;
                        type = 1;
                        x0 = red.getX();
                        fi0 = red.getFi();
                        time0 = time;
                        bullets.add(blue.fireBig());

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
                    if (bullet.getY() >= map.GroundY(bullet.getX()) || bullet.isRemuv()) {
                        bullets.remove(bullet);
                    }else if (checkHit(bullet,red)) {
                        primaryStage.close();
                        System.out.println("Blue win");
                    }  else if (checkHit(bullet,blue)) {
                        primaryStage.close();
                       System.out.println("Yellow win");
                    } else {
                      bullet.draw();
                    }
                }

                if (time == CHECK_UPDATES) {
                    long info = Converter.getInfo(red.getX(), red.getFi(), type, x0 - red.getX(), fi0 -red.getFi(),time - time0 );
                    info = network.synchronization(info);
                    Converter.setCannon(blue, info);
                    bullets.add(Converter.getBullet(gc,info));
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
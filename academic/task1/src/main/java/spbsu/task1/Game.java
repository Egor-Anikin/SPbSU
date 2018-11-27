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

import java.io.IOException;
import java.util.LinkedList;

/** The Game class. */
public class Game extends Application {
    private static final int WIDTH = 1360;
    private static final int HEIGHT = 765;
    private static final int CHECK_UPDATES = 0;
    private static final int SCREEN_WIDTH = (int) Screen.getPrimary().getVisualBounds().getWidth();
    private static final int SCREEN_HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();

    private static Network network = new Network();

    private static int time = CHECK_UPDATES;
    private static int type = 0;

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

    private void gameMechanics(final Stage primaryStage, GraphicsContext gc, final LinkedList<String> keys) throws IOException {
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

        red.setY(Map.GroundY(red.getX()));
        blue.setY(Map.GroundY(blue.getX()));

        final LinkedList<Bullet> bullets = new LinkedList<>();

        new AnimationTimer() {

            public void handle(long currentNanoTime) {

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
                    type = 1;
                    bullets.add(red.fireSmall());
                    keys.remove("ENTER");
                }

                if (keys.contains("SPACE")) {
                    type = 2;
                    bullets.add(red.fireBig());
                    keys.remove("SPACE");
                }

                if (keys.contains("ESCAPE")) {
                    primaryStage.close();
                }
                map.draw();
                red.draw();
                blue.draw();

                if (time == CHECK_UPDATES) {
                    blue.setX( network.message(red.getX()));
                    blue.setY(map.GroundY(blue.getX()));
                    blue.setFi(network.message(red.getFi()));
                    type = network.message(type);
                    if(type == 1){
                        bullets.add(blue.fireSmall());
                    } else if (type == 2) {
                        bullets.add(blue.fireBig());
                    }

                    type = 0;
                    time = 0;
                }
                else {
                    time++;
                }

                for (Bullet bullet : bullets) {
                    if ( bullet.isRemove()) {
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

            }
        }.start();

        network.closeSocket();
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
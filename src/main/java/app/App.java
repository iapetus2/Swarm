package app;

import ClassesOfCharacters.Bullet;
import ClassesOfCharacters.Commander;
import ClassesOfCharacters.Soldier;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import managers.ObjectManager;

import static javafx.scene.input.KeyCode.LEFT;

public class App extends Application {

    private final int width = 1920;
    private final int height = 1080;

    private Pane root;
    private Commander commander;
    private Commander commander2;

    private ObjectManager objectManager = new ObjectManager();
    private AnimationTimer mainTimer;

    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setPrefSize(800, 600);
        scroll.setContent(layout);
        return scroll;
    }

    @Override
    public void init() throws Exception {
        super.init();

        root = new Pane();
        root.setPrefSize(width, height);

        commander = new Commander();
        commander.setVelocity(new Point2D(0.5, 0));
        commander2 = new Commander();
        commander2.setVelocity(new Point2D(0.5, 0));

        objectManager.setPane(root);
        objectManager.addCommander(commander, 300, 300);
        objectManager.addCommander(commander2, 300, 300);

        mainTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                objectManager.onUpdate();
            }
        };
        mainTimer.start();
    }

    public static boolean clickLeft = false;
    public static boolean clickRight = false;
    public static boolean shoot = false;

    public static boolean clickLeft2 = false;
    public static boolean clickRight2 = false;
    public static boolean shoot2 = false;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(root));

        root.getScene().setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT: clickLeft = true; break;
                case RIGHT: clickRight = true; break;
                case CONTROL: shoot = true; break;

                case A: clickLeft2 = true; break;
                case D: clickRight2 = true; break;
                case SPACE: shoot2 = true; break;
            }
        });

        root.getScene().setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT: clickLeft = false; break;
                case RIGHT: clickRight = false; break;
                case CONTROL: shoot = false; break;

                case A: clickLeft2 = false; break;
                case D: clickRight2 = false; break;
                case SPACE: shoot2 = false; break;

                case ALT:
                    objectManager.addSoldier(new Soldier(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
                    break;
            }
        });

        /*if (e.getCode() == LEFT) {
            commander.rotateLeft();
        } else if (e.getCode() == KeyCode.RIGHT) {
            commander.rotateRight();
        } else if (e.getCode() == KeyCode.SPACE) {
            Bullet bullet = new Bullet(commander);
            bullet.setVelocity(commander.getVelocity().normalize().multiply(5));
            objectManager.addBullet(bullet, commander.getView().getTranslateX(),
                    commander.getView().getTranslateY());
        } else if (e.getCode() == KeyCode.ALT) {
            objectManager.addSoldier(new Soldier(), Math.random() * root.getPrefWidth(),
                    Math.random() * root.getPrefHeight());
        }*/

       /* stage.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.A) {
                commander2.rotateLeft();
            } else if (e.getCode() == KeyCode.D) {
                commander2.rotateRight();
            } else if (e.getCode() == KeyCode.CONTROL) {
                Bullet bullet = new Bullet(commander2);
                bullet.setVelocity(commander2.getVelocity().normalize().multiply(5));
                objectManager.addBullet(bullet, commander2.getView().getTranslateX(),
                        commander2.getView().getTranslateY());
            } else if (e.getCode() == KeyCode.ALT) {
                objectManager.addSoldier(new Soldier(), Math.random() * root.getPrefWidth(),
                        Math.random() * root.getPrefHeight());
            }
                });*/
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
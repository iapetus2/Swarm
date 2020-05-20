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
        commander.setVelocity(new Point2D(1, 0));
        commander2 = new Commander();
        commander2.setVelocity(new Point2D(1, 0));

        objectManager.setPane(root);
        objectManager.addMainCommander(commander);
        objectManager.addMainCommander(commander2);

        mainTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                objectManager.onUpdate();
            }
        };
        mainTimer.start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(root));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
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
            }

        });
        stage.getScene().setOnKeyReleased(e -> {
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
                });
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
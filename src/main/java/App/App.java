package App;

import ClassesOfCharacters.Commander;
import ClassesOfCharacters.Bullet;
import ClassesOfCharacters.GameObject;
import ClassesOfCharacters.Soldier;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private Pane root;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> soldiers = new ArrayList<>();

    private GameObject commander;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(1920, 1080);

        commander = new Commander();
        commander.setVelocity(new Point2D(1, 0));
        addGameObject(commander, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addSoldier(GameObject soldier, double x, double y) {
        soldiers.add(soldier);
        addGameObject(soldier, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate() {
        for (GameObject bullet : bullets) {
            for (GameObject soldier : soldiers) {
                if (bullet.isColliding(soldier)) {
                    bullet.setAlive(false);
                    soldier.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(), soldier.getView());
                }
            }
        }

        bullets.removeIf(GameObject::isDead);
        soldiers.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        soldiers.forEach(GameObject::update);

        commander.update();


    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                commander.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                commander.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet();
                bullet.setVelocity(commander.getVelocity());
                addBullet(bullet, commander.getView().getTranslateX(), commander.getView().getTranslateY());
            } else if (e.getCode() == KeyCode.ALT) {
                addSoldier(new Soldier(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            }

        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
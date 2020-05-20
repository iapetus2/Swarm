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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ScrollPane;


public class App extends Application {

    private Pane root;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Soldier> soldiers = new ArrayList<>();

    private Commander commander;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(1400, 720);

        commander = new Commander(root.getPrefWidth(), root.getPrefHeight());
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

    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setPrefSize(800, 600);
        scroll.setContent(layout);
        return scroll;
    }

    private void addBullet(Bullet bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addSoldier(Soldier soldier, double x, double y) {
        soldiers.add(soldier);
        addGameObject(soldier, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate() {
        for (Bullet bullet : bullets) {
            for (Soldier soldier : soldiers) {
                if (bullet.isColliding(soldier)) {
                    bullet.setAlive(false);
                    soldier.removeHealth(bullet.getDamage());

                    if (soldier.isDead()) {
                        root.getChildren().removeAll(bullet.getView(), soldier.getView());
                    } else {
                        root.getChildren().removeAll(bullet.getView());
                    }
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
                Bullet bullet = new Bullet(commander);
                bullet.setVelocity(commander.getVelocity().normalize().multiply(5));
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
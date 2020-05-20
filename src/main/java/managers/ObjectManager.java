package managers;

import ClassesOfCharacters.Bullet;
import ClassesOfCharacters.Commander;
import ClassesOfCharacters.GameObject;
import ClassesOfCharacters.Soldier;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {

    private Pane root;
    private Commander mainCommander;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Soldier> soldiers = new ArrayList<>();
    private List<Commander> commanders = new ArrayList<>();

    public void setPane(Pane pane) {
        root = pane;
    }

    public void addMainCommander(Commander commander) {
        mainCommander = commander;
        addCommander(mainCommander, 300, 300);
    }

    private void addCommander(Commander commander, double x, double y) {
        commanders.add(commander);
        addGameObject(commander, x, y);
    }

    public void addBullet(Bullet bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    public void addSoldier(Soldier soldier, double x, double y) {
        soldiers.add(soldier);
        addGameObject(soldier, x, y);
    }

    public void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    public void onUpdate() {

        bullets.forEach(bullet -> {
            soldiers.forEach(soldier -> {
                if (bullet.isColliding(soldier)) {
                    bullet.setAlive(false);
                    soldier.removeHealth(bullet.getDamage());
                    if (soldier.isDead()) {
                        root.getChildren().removeAll(bullet.getView(), soldier.getView());
                    } else {
                        root.getChildren().removeAll(bullet.getView());
                    }



                }
            });
        });

        commanders.forEach(commander -> {
            Node viewCommander = commander.getView();

            if (viewCommander.getTranslateX() < 0) {
                viewCommander.setTranslateX(root.getWidth());
            } else if (viewCommander.getTranslateX() > root.getWidth()) {
                viewCommander.setTranslateX(0);
            }

            if (viewCommander.getTranslateY() < 0) {
                viewCommander.setTranslateY(root.getHeight());
            } else if (viewCommander.getTranslateY() > root.getHeight()) {
                viewCommander.setTranslateY(0);
            }
        });


        soldiers.removeIf(GameObject::isDead);
        bullets.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        soldiers.forEach(GameObject::update);
        commanders.forEach(GameObject::update);

    }

    private void addKeyListener() {
        root.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                mainCommander.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                mainCommander.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet(mainCommander);
                bullet.setVelocity(mainCommander.getVelocity().normalize().multiply(5));
                addBullet(bullet, mainCommander.getView().getTranslateX(), mainCommander.getView().getTranslateY());
            } else if (e.getCode() == KeyCode.ALT) {
                addSoldier(new Soldier(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            }
        });
    }

}

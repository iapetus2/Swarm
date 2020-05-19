package managers;

import ClassesOfCharacters.GameObject;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
        private List<GameObject> bullets = new ArrayList<>();
        private List<GameObject> soldiers = new ArrayList<>();
        private List<GameObject> commanders = new ArrayList<>();

        private Pane root;

        private GameObject player;

        private void addBullet (GameObject bullet,double x, double y){
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

        private void addSoldier (GameObject soldier,double x, double y){
        soldiers.add(soldier);
        addGameObject(soldier, x, y);
    }

        private void addGameObject (GameObject object,double x, double y){
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

        private void onUpdate () {
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

        player.update();

        if (Math.random() < 0.02) {
            addSoldier(new ClassesOfCharacters.Soldier(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }
    }

}

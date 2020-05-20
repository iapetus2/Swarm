package managers;

import ClassesOfCharacters.Bullet;
import ClassesOfCharacters.Commander;
import ClassesOfCharacters.GameObject;
import ClassesOfCharacters.Soldier;
import app.App;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {

    private Pane root;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Soldier> soldiers = new ArrayList<>();
    private List<Commander> commanders = new ArrayList<>();

    public void setPane(Pane pane) {
        root = pane;

    }

    public void addCommander(Commander commander, double x, double y) {
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
                if (soldier.getCaptain() != bullet.getStriker()) {
                    if (bullet.isColliding(soldier)) {
                        bullet.setAlive(false);
                        soldier.removeHealth(bullet.getDamage());
                        if (soldier.isDead()) {
                            soldier.setAlive(true);
                            if (soldier.getCaptain() != null) {
                                soldier.getCaptain().removeNumberOfSoldiers();
                                //System.out.println(soldier.getCaptain().getNumberOfSoldiers());
                            }
                            soldier.setCaptain(bullet.getStriker());
                            soldier.setFill(bullet.getStrikerPaint());
                            //System.out.println(soldier.getCaptain().getNumberOfSoldiers());
                        }
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


        if(App.shoot) {
            Bullet bullet = new Bullet(commanders.get(0));
            bullet.setVelocity(commanders.get(0).getVelocity().normalize().multiply(5));
            addBullet(bullet, commanders.get(0).getView().getTranslateX(), commanders.get(0).getView().getTranslateY());
        }
        if(App.clickLeft) {
            commanders.get(0).rotateLeft();
        }
        if(App.clickRight) {
            commanders.get(0).rotateRight();
        }


        if(App.shoot2) {
            Bullet bullet = new Bullet(commanders.get(1));
            bullet.setVelocity(commanders.get(1).getVelocity().normalize().multiply(5));
            addBullet(bullet, commanders.get(1).getView().getTranslateX(), commanders.get(1).getView().getTranslateY());
        }
        if(App.clickLeft2) {
            commanders.get(1).rotateLeft();
        }
        if(App.clickRight2) {
            commanders.get(1).rotateRight();
        }



        soldiers.removeIf(GameObject::isDead);
        bullets.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        soldiers.forEach(GameObject::update);
        commanders.forEach(GameObject::update);

    }


}

package managers;

import ClassesOfCharacters.Bullet;
import ClassesOfCharacters.Commander;
import ClassesOfCharacters.GameObject;
import ClassesOfCharacters.Soldier;
import app.App;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ObjectManager {

    private Pane root;

    public static int numberOfEntities = 2;

    private List<Bullet> bullets = new ArrayList<>();
    private List<Soldier> soldiers = new ArrayList<>();
    private List<Commander> commanders = new ArrayList<>();

    private Text text1;
    private Text text2;
    private Text textWin;

    private String initalText1 = "Blue master ";
    private String initalText2 = "Green master ";
    private String blueWin = "Blue doooodle win! ";
    private String greenWin = "Green doooodle win! ";


    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 3);


    public void setPane(Pane pane) {
        root = pane;

        textWin = new Text("");
        textWin.setTranslateX(1980/4);
        textWin.setTranslateY(1080/2);
        textWin.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 100));
        textWin.setFill(Color.BLUE);

        text1 = new Text(initalText1);
        text1.setTranslateX(5);
        text1.setTranslateY(50);
        text1.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
        text1.setFill(Color.BLUE);

        text2 = new Text(initalText2);
        text2.setTranslateX(1480);
        text2.setTranslateY(50);
        text2.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
        text2.setFill(Color.GREEN);

        root.getChildren().addAll(text1, text2, textWin);
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

    private long counter = 0;
    private int currentNumberOfSoldiers = numberOfEntities;

    public void onUpdate() {

        if (!App.pause) {
            counter++;
            commanders.forEach(commander -> {
                commander.getView().toFront();
            });
            text1.toFront();
            text2.toFront();


            if(currentNumberOfSoldiers <= 0 ) {
                if(commanders.get(0).getNumberOfSoldiers() > commanders.get(1).getNumberOfSoldiers()) {
                    textWin.setText(blueWin);
                } else {
                    textWin.setText(greenWin);
                }
                textWin.toFront();
                App.pause = true;
            }


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
                                } else {
                                    currentNumberOfSoldiers -= 1;
                                }
                                soldier.setCaptain(bullet.getStriker());
                                soldier.setFill(bullet.getStrikerPaint());
                            }
                            root.getChildren().removeAll(bullet.getView());
                        }
                    }
                });
            });

            text1.setText(initalText1 + commanders.get(0).getNumberOfSoldiers());
            text2.setText(initalText2 + commanders.get(1).getNumberOfSoldiers());

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


            if (App.shoot && counter > 20) {
                Bullet bullet = new Bullet(commanders.get(0));
                bullet.setVelocity(commanders.get(0).getVelocity().normalize().multiply(5));
                addBullet(bullet, commanders.get(0).getView().getTranslateX(), commanders.get(0).getView().getTranslateY());
            }
            if (App.clickLeft) {
                commanders.get(0).rotateLeft();
            }
            if (App.clickRight) {
                commanders.get(0).rotateRight();
            }


            if (App.shoot2 && counter > 20) {
                Bullet bullet = new Bullet(commanders.get(1));
                bullet.setVelocity(commanders.get(1).getVelocity().normalize().multiply(5));
                addBullet(bullet, commanders.get(1).getView().getTranslateX(), commanders.get(1).getView().getTranslateY());
            }
            if (App.clickLeft2) {
                commanders.get(1).rotateLeft();
            }
            if (App.clickRight2) {
                commanders.get(1).rotateRight();
            }


            if (numberOfEntities > 0 && ThreadLocalRandom.current().nextDouble() < 0.015) {
                addSoldier(new Soldier(), ThreadLocalRandom.current().nextDouble() * root.getPrefWidth(), ThreadLocalRandom.current().nextDouble() * root.getPrefHeight());
                numberOfEntities--;
            }

            soldiers.removeIf(GameObject::isDead);
            bullets.removeIf(GameObject::isDead);

            bullets.forEach(GameObject::update);
            soldiers.forEach(GameObject::update);
            commanders.forEach(GameObject::update);

            if(counter > 20) {
                counter = 0;
            }
        }

    }
}

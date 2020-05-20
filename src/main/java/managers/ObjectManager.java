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
    private double width = 0;
    private double heught = 0;

    public static int numberOfEntities = 3;

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

    public static boolean end = false;

    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 3);

    public ObjectManager (double width, double height) {
        this.width = width;
        this.heught = height;
    }


    public void setPane(Pane pane) {
        root = pane;

        textWin = new Text("");
        textWin.setTranslateX(width/5);
        textWin.setTranslateY(heught/2);
        textWin.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 100));
        textWin.setFill(Color.BLUE);

        text1 = new Text(initalText1);
        text1.setTranslateX(5);
        text1.setTranslateY(50);
        text1.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
        text1.setFill(Color.BLUE);

        text2 = new Text(initalText2);
        text2.setTranslateX(width - 240);
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
                if(commanders.get(0).getNumberOfSoldiers() > commanders.get(1).getNumberOfSoldiers() && !end) {
                    textWin.setText(blueWin);
                    textWin.setFill(Color.BLUE);
                    end = true;
                } else if (!end){
                    textWin.setText(greenWin);
                    textWin.setFill(Color.GREEN);
                    end = true;
                }
                textWin.toFront();
                counter = 40;
                commanders.get(0).rotateLeft();
                commanders.get(1).rotateLeft();

                Bullet bullet1 = new Bullet(commanders.get(0));
                bullet1.setVelocity(commanders.get(0).getVelocity().normalize().multiply(-5));
                addBullet(bullet1, commanders.get(0).getView().getTranslateX(), commanders.get(0).getView().getTranslateY());

                Bullet bullet2 = new Bullet(commanders.get(1));
                bullet2.setVelocity(commanders.get(1).getVelocity().normalize().multiply(-5));
                addBullet(bullet2, commanders.get(1).getView().getTranslateX(), commanders.get(1).getView().getTranslateY());

                bullets.forEach(bullet -> {
                    Node viewBullet = bullet.getView();

                    if (viewBullet.getTranslateX() < 0) {
                        viewBullet.setTranslateX(root.getWidth());
                    } else if (viewBullet.getTranslateX() > root.getWidth()) {
                        viewBullet.setTranslateX(0);
                    }

                    if (viewBullet.getTranslateY() < 0) {
                        viewBullet.setTranslateY(root.getHeight());
                    } else if (viewBullet.getTranslateY() > root.getHeight()) {
                        viewBullet.setTranslateY(0);
                    }
                });

                App.shoot = App.shoot2 = true;
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

            if (!end) {
                text1.setText(initalText1 + commanders.get(0).getNumberOfSoldiers());
                text2.setText(initalText2 + commanders.get(1).getNumberOfSoldiers());
            }

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
                addBullet(bullet,
                          commanders.get(0).getView().getTranslateX(),
                          commanders.get(0).getView().getTranslateY());
            }
            if (App.clickLeft && !end) {
                commanders.get(0).rotateLeft();
            }
            if (App.clickRight && !end) {
                commanders.get(0).rotateRight();
            }


            if (App.shoot2 && counter > 20) {
                Bullet bullet = new Bullet(commanders.get(1));
                bullet.setVelocity(commanders.get(1).getVelocity().normalize().multiply(5));
                addBullet(bullet,
                          commanders.get(1).getView().getTranslateX(),
                          commanders.get(1).getView().getTranslateY());
            }
            if (App.clickLeft2 && !end) {
                commanders.get(1).rotateLeft();
            }
            if (App.clickRight2 && !end) {
                commanders.get(1).rotateRight();
            }


            if (numberOfEntities > 0 && ThreadLocalRandom.current().nextDouble() < 0.015) {
                addSoldier(new Soldier(), ThreadLocalRandom.current().nextDouble() * (root.getScene().getWidth() - 10),
                                          ThreadLocalRandom.current().nextDouble() * (root.getScene().getHeight() - 10));
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

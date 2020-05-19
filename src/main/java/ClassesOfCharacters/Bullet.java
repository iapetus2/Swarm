package ClassesOfCharacters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends GameObject {
    private int damage = 1;
    private Commander myCommander;

    public Bullet(final Commander cur_com) {
        super(new Circle(3, 3, 3, Color.BROWN));
        myCommander = cur_com;
        setDamage();
    }

    public void setDamage () {
        if ((400 < myCommander.getView().getTranslateX() & myCommander.getView().getTranslateX() < 1000) &
        (myCommander.getView().getTranslateY() > 170 & myCommander.getView().getTranslateY() < 550)) {
            damage = 10;
        } else {
            damage = 5;
        }
    }

    public int getDamage () {
        return damage;
    }
}
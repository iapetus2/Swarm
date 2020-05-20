package ClassesOfCharacters;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Bullet extends GameObject {

    private int damage = 1;
    private Commander striker;

    public Bullet(final Commander commander) {
        super(new Circle(3, 3, 3, Color.BROWN));
        striker = commander;
        setDamage();
    }

    public void setDamage () {
        if ((300 < striker.getView().getTranslateX() & striker.getView().getTranslateX() < 300) &
        (striker.getView().getTranslateY() > 150 & striker.getView().getTranslateY() < 150)) {
            damage = 10;
        } else {
            damage = 5;
        }
    }

    public int getDamage () {
        return damage;
    }
}
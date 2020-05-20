package ClassesOfCharacters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends GameObject {
    private int damage = 1;
    private Commander striker;

    public Bullet(final Commander cur_com) {
        super(new Circle(3, 3, 3, Color.BROWN));
        striker = cur_com;
        setDamage();
    }

    public void setDamage () {
        if ((300 < striker.getView().getTranslateX() & striker.getView().getTranslateX() < striker.width - 300) &
        (striker.getView().getTranslateY() > 150 & striker.getView().getTranslateY() < striker.height - 150)) {
            damage = 10;
        } else {
            damage = 5;
        }
    }

    public int getDamage () {
        return damage;
    }
}
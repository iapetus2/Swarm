package ClassesOfCharacters;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Bullet extends GameObject {

    private int damage = 1;
    private Commander striker;
    private Paint strikerColor;

    public Bullet(final Commander commander) {
        super(new Circle(3, 3, 3, Color.BROWN));
        striker = commander;
        strikerColor = commander.getColor();
        setDamage(commander);
    }

    public void setDamage (Commander striker) {
        if ((300 < striker.getView().getTranslateX() & striker.getView().getTranslateX() < 1100) &
        (striker.getView().getTranslateY() > 150 & striker.getView().getTranslateY() < 570)) {
            damage = 10;
        } else {
            damage = 5;
        }
    }

    public Paint getStrikerPaint () {
        return strikerColor;
    }

    public int getDamage () {
        return damage;
    }
}
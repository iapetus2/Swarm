package ClassesOfCharacters;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Soldier extends GameObject {
    private Commander captain = null;

    public Soldier() {
        super(new Circle(10, 10, 10, Color.RED));
        this.setHealth(20);
    }

    @Override
    public void setFill(Paint paint) {
        ((Circle) getView()).setFill(paint);
        setHealth(20);

    }

    public Commander getCaptain () {
        return captain;
    }

    public void setCaptain (final Commander commander) {
        captain = commander;
        commander.increaseNumberOfSoldiers();
    }
}
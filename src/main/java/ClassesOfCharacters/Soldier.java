package ClassesOfCharacters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Soldier extends GameObject {
    public Commander captain;

    public Soldier() {
        super(new Circle(10, 10, 10, Color.RED));
        this.setHealth(20);
    }

    public void follow(Commander commander) {
        captain = commander;
    }

}
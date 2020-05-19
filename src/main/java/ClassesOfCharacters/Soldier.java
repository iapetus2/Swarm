package ClassesOfCharacters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Soldier extends GameObject {
    public Soldier() {
        super(new Circle(15, 15, 15, Color.RED));
    }
}
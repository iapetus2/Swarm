package ClassesOfCharacters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Soldier extends GameObject {
    public Commander cur_com;
    public Soldier() {
        super(new Circle(15, 15, 15, Color.RED));
    }

    public void follow(Commander com){
            cur_com = com;
        }

}
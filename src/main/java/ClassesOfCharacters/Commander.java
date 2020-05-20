package ClassesOfCharacters;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Commander extends GameObject {

    public Commander() {
        super(new Rectangle(30, 15, Color.BLUE));
    }

    @Override
    public void update () {
        super.update();
    }

    public void setFill(Paint paint) {
        ((Rectangle) getView()).setFill(paint);
    }

}

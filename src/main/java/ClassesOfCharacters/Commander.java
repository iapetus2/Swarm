package ClassesOfCharacters;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Commander extends GameObject {
    private int numberOfSoldiers = 0;

    public Commander() {
        super(new Rectangle(30, 15, Color.BLUE));
    }

    public int getNumberOfSoldiers () {
        return numberOfSoldiers;
    }

    @Override
    public void update () {
        super.update();
    }

    @Override
    public void setFill(Paint paint) {
        ((Rectangle) getView()).setFill(paint);
    }

    public Paint getColor () {
        return((Rectangle) getView()).getFill();
    }

    public void increaseNumberOfSoldiers () {
        numberOfSoldiers += 1;
    }

    public void removeNumberOfSoldiers () {
        numberOfSoldiers -= 1;
    }

}

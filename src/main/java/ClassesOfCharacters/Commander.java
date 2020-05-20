package ClassesOfCharacters;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Commander extends GameObject {
    protected double width = 0;
    protected double height = 0;
    public Commander(final double newWidth, final double newHeight) {
        super(new Rectangle(30, 15, Color.BLUE));
        this.width = newWidth;
        this.height = newHeight;
    }

    public void update (final double width, final double height) {
        this.update();
        if (getView().getTranslateX() < 0) {
            getView().setTranslateX(width);
        } else if (getView().getTranslateX() > width) {
            getView().setTranslateX(0);
        }

        if (getView().getTranslateY() < 0) {
            getView().setTranslateY(height);
        } else if (getView().getTranslateY() > height) {
            getView().setTranslateY(0);
        }
    }
}

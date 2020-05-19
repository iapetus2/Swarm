package ClassesOfCharacters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Commander extends GameObject {
    public Commander() {
        super(new Rectangle(30, 15, Color.BLUE));
    }

    @Override
    public void update() {
        super.update();
        if (getView().getTranslateX() < 0) {
            getView().setTranslateX(1400);
        } else if (getView().getTranslateX() > 1400) {
            getView().setTranslateX(0);
        }

        if (getView().getTranslateY() < 0) {
            getView().setTranslateY(720);
        } else if (getView().getTranslateY() > 720) {
            getView().setTranslateY(0);
        }
    }
}

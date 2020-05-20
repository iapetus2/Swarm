package ClassesOfCharacters;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class GameObject {

    private Node view;
    private Point2D velocity = new Point2D(0, 0);
    private int health = 0;

    private boolean alive = true;

    public GameObject(Node view) {
        this.view = view;
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public void rotateRight() {
        view.setRotate(view.getRotate() + 5);
        setVelocity(new Point2D(this.getVelocity().magnitude()*Math.cos(Math.toRadians(getRotate())),
                this.getVelocity().magnitude()*Math.sin(Math.toRadians(getRotate()))));
    }

    public void rotateLeft() {
        view.setRotate(view.getRotate() - 5);
        setVelocity(new Point2D(this.getVelocity().magnitude()*Math.cos(Math.toRadians(getRotate())),
                this.getVelocity().magnitude()*Math.sin(Math.toRadians(getRotate()))));
    }

    public boolean isColliding(GameObject other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    public void removeHealth (final int damage) {
        if (health > damage) {
            health -= damage;
        } else {
            this.setAlive(false);
        }
    }

    public void setFill(Paint paint) {
        ;
    }
}

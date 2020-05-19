package utility;

import javafx.geometry.Point2D;

public class VectorXY {
    private double x = 0;
    private double y = 0;

    public VectorXY () {;}

    public VectorXY (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public VectorXY (final VectorXY vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public VectorXY setX (final double x) {
        this.x = x;
        return this;
    }

    public VectorXY setY (final double y) {
        this.y = y;
        return this;
    }

    public void setVector (final double x, final double y) {
        this.setX(x);
        this.setY(y);
    }

    public void setVector (final VectorXY vector) {
        this.setVector(vector.x, vector.y);
    }

    public VectorXY addVector (final double x, final double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public VectorXY addVector (final VectorXY vector) {
        this.addVector(vector.x, vector.y);
        return this;
    }

    public VectorXY sumVector ( final VectorXY vector1, final VectorXY vector2) {
        return new VectorXY(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    public VectorXY subVector (final double x, final double y) {
        this.addVector(-x, -y);
        return this;
    }

    public VectorXY subVector (final VectorXY vector) {
        this.subVector(vector.x, vector.y);
        return this;
    }

    public VectorXY diffVector (final VectorXY vector1, final VectorXY vector2) {
        return new VectorXY (vector1.x - vector2.x, vector1.y - vector2.y);
    }

    public VectorXY mulVector (final double n) {
        this.x *= n;
        this.y *= n;
        return this;
    }

    public VectorXY mulVector (final VectorXY vector, final double n) {
        return new VectorXY(vector.x*n, vector.y*n);
    }

    public VectorXY divVector (double n) {
        if (n != 0) {
            this.x /= n;
            this.y /= n;
        } else {
            System.out.println("Error! Division by zero!");
            //Либо научиться выбрасывать исключения!
        }
        return this;
    }

    public VectorXY divVector (final VectorXY vector, final double n) {
        VectorXY new_vector = new VectorXY(vector);
        new_vector.divVector(n);
        return new_vector;
    }

    public double lengthVector () {
        return Math.sqrt(x*x + y*y);
    }

    public VectorXY normalize () {
        double l = this.lengthVector();
        if (l != 0 && l != 1) {
            return (divVector(this, l));
        }
        return null;
    }

    public static double dotProduct(VectorXY vector1, VectorXY vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY();
    }

    public static double getAngle(VectorXY vector1, VectorXY vector2) {
        return Math.acos(dotProduct(vector1, vector2) / (vector1.lengthVector() * vector2.lengthVector()));
    }

}

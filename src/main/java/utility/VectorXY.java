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

    public void setX (final double x) {
        this.x = x;
    }

    public void setY (final double y) {
        this.y = y;
    }

    public void setVector (final double x, final double y) {
        this.setX(x);
        this.setY(y);
    }

    public void setVector (final VectorXY vector) {
        this.setVector(vector.x, vector.y);
    }

    public VectorXY addVector (final double x, final double y) {
        return new VectorXY(this.x + x, this.y + y);
    }

    public VectorXY addVector (final VectorXY vector) {
        return this.addVector(vector.x, vector.y);
    }

    public VectorXY sumVector ( final VectorXY vector1, final VectorXY vector2) {
        return new VectorXY(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    public VectorXY subVector (final double x, final double y) {
        return this.addVector(-x, -y);
    }

    public VectorXY subVector (final VectorXY vector) {
        return this.subVector(vector.x, vector.y);
    }

    public VectorXY diffVector (final VectorXY vector1, final VectorXY vector2) {
        return new VectorXY (vector1.x - vector2.x, vector1.y - vector2.y);
    }

    public VectorXY mulVector (final double n) {
        return new VectorXY(this.x * n, this.y * n);
    }

    public VectorXY mulVector (final VectorXY vector, final double n) {
        return new VectorXY(vector.x*n, vector.y*n);
    }

    public VectorXY divVector (double n) {
        if (n != 0) {
            return new VectorXY(this.x/n, this.y/n);
        } else {
            System.out.println("Error! Division by zero!");
            //Либо научиться выбрасывать исключения!
        }
        return null;
    }

    public VectorXY divVector (final VectorXY vector, final double n) {
        return this.divVector(n);
    }

    public double lengthVector () {
        return Math.sqrt(x*x + y*y);
    }

    public VectorXY normalize () {
        double l = this.lengthVector();
        if (l != 0 && l != 1) {
            return this.divVector(l);
        } else if (l == 1){
            return new VectorXY(this.x, this.y);
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

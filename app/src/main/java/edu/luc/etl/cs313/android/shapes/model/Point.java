package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */
public final class Point extends Location {

    public Point(final int x, final int y) {
        super(x, y, new Circle(0));
    }

    @Override
    public <Result> Result accept(final Visitor<Result> v) {
        return v.onLocation(this);
    }


    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }
}
package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    @Override
    public Integer onPolygon(final Polygon p) {
        return ;
    }

    @Override
    public Integer onCircle(final Circle c) {


        return 1;
    }

    @Override
    public Integer onGroup(final Group g) {
        int count =1;
        for(final Shape shape : g.getShapes()){
            count += shape.accept(this);
        }
        return count;
    }

    @Override
    public Integer onRectangle(final Rectangle q) {
        return -1;
    }

    @Override
    public Integer onOutline(final Outline o) {
        return -1;
    }

    @Override
    public Integer onFill(final Fill f) {
        return 1 + c.getShape().accept(this);

    }

    @Override
    public Integer onLocation(final Location l) {
        return 1 + l.getShape().accept(this);
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        return 1 + c.getShape().accept(this);;
    }
}

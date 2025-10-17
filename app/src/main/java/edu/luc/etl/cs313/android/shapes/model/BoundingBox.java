package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();

        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getshape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;


        return ;
    }

    @Override
    public Location onLocation(final Location l) {

        final Location innerBbox = l.getShape().accept(this);
        if (innerBbox == null) {
            return null;
        }
        final int newX = l.getX() + innerBbox.getX();
        final int newY = l.getY() + innerBbox.getY();
        return new Location(0, 0, new Rectangle(r.getWidth(), r.getHeight()));
    }

    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0,0, new Rectangle(r.getWidth(), r.getHeight));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        return null;
    }
}

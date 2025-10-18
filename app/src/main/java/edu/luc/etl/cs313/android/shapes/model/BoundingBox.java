package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Process each shape in the group
        for (Shape s : g.getShapes()) {
            Location loc = s.accept(this);
            if (loc != null) {
                // Calculate bounds of this shape
                int shapeMinX = loc.getX();
                int shapeMinY = loc.getY();
                Rectangle rect = (Rectangle) loc.getShape();
                int shapeMaxX = shapeMinX + rect.getWidth();
                int shapeMaxY = shapeMinY + rect.getHeight();

                // Update overall bounds
                minX = Math.min(minX, shapeMinX);
                minY = Math.min(minY, shapeMinY);
                maxX = Math.max(maxX, shapeMaxX);
                maxY = Math.max(maxY, shapeMaxY);
            }
        }

        // If no valid shapes found
        if (minX == Integer.MAX_VALUE) {
            return null;
        }

        int width = maxX - minX;
        int height = maxY - minY;
        return new Location(minX, minY, new Rectangle(width, height));
    }

    @Override
    public Location onLocation(final Location l) {
        final Location innerBbox = l.getShape().accept(this);
        if (innerBbox == null) {
            return null;
        }

        final int newX = l.getX() + innerBbox.getX();
        final int newY = l.getY() + innerBbox.getY();

        Rectangle innerRect = (Rectangle) innerBbox.getShape();
        return new Location(newX, newY, new Rectangle(innerRect.getWidth(), innerRect.getHeight()));
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0, 0, new Rectangle(r.getWidth(), r.getHeight()));
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
        if (s.getPoints().isEmpty()) {
            return null;
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Point p : s.getPoints()) {
            int x = p.getX();
            int y = p.getY();

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        int width = maxX - minX;
        int height = maxY - minY;
        return new Location(minX, minY, new Rectangle(width, height));
    }
}
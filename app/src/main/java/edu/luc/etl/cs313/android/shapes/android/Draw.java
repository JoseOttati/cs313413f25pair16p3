package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas; // FIXME
        this.paint = paint; // FIXME
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(2);
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {
        final int oldColor = paint.getColor();
        paint.setColor(c.getColor);
        c.getShape().accept(this);
        paint.setColor(oldColor);


        return null;
    }

    @Override
    public Void onFill(final Fill c) {
        final Paint.Style oldStyle= paint.getStyle();
        paint.setStyle(Paint.Style.FIll);
        f.getShape().accept(this);
        paint.setStyle(oldStyle);

        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        for(final Shape shape : g.getShapes()) {
            shape.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        x += l.getX();
        y += l.getY();
        return l.getShape().accept(this);

    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint)
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        final Paint.Style oldStyle = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
        o.getShape().accept(this);
        paint.setStyle(oldstyle)

        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        final List<? extends Point> points = s.getPoints();
        final int size = points.size();

        if(size<2){
            return null;
        }
        final Path path = new Path();
        final Point firstPoint = points.get(0);
        path.moveTo(firstPoint.getX(), firstPoint.getY());
        //Draw line to given points
        for (int i=1; i < size; i++){
            final Point current = points.get (i);
            path.lineTo(current.getX(), current.getY());
        }

        path.close();

        canvas.drawLines(pts, paint);
        return null;
    }
}

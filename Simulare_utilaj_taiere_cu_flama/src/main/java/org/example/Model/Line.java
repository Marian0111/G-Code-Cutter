package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private MyPoint start;
    private MyPoint end;

    public Line(MyPoint start, MyPoint end) {
        this.start = start;
        this.end = end;
    }

    public MyPoint getStart() {
        return start;
    }

    public MyPoint getEnd() {
        return end;
    }

    public List<MyPoint> rasterize() {
        List<MyPoint> points = new ArrayList<>();

        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            points.add(new MyPoint(x1, y1));

            if (x1 == x2 && y1 == y2) {
                break;
            }
            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
        return points;
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start.toString() +
                ", end=" + end.toString() +
                '}';
    }
}

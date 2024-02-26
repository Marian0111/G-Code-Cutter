package org.example.Model;

import java.util.ArrayList;
import java.util.List;

public class CircularArc {
    private MyPoint startPoint;
    private MyPoint endPoint;
    private double centerPointX;
    private double centerPointY;
    private boolean clockWise;

    public CircularArc(MyPoint startPoint, MyPoint endPoint, int dist, boolean clockWise) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.clockWise = clockWise;
        if(clockWise){
            centerPointX = ((double)(startPoint.getX() + endPoint.getX())) / 2.0 - (double)dist;
            centerPointY = ((double)(startPoint.getY() + endPoint.getY())) / 2.0 - (double)dist;
        }else{
            centerPointX = ((double)(startPoint.getX() + endPoint.getX())) / 2.0 + (double)dist;
            centerPointY = ((double)(startPoint.getY() + endPoint.getY())) / 2.0 + (double)dist;
        }
        this.rasterize();
    }

    public List<MyPoint> rasterize() {
        List<MyPoint> pointsOnArc = new ArrayList<>();

        double startAngle = Math.atan2(startPoint.getY() - centerPointY, startPoint.getX() - centerPointX);
        double endAngle = Math.atan2(endPoint.getY() - centerPointY, endPoint.getX() - centerPointX);

        double radius = Math.sqrt(Math.pow(centerPointX-startPoint.getX(),2)+Math.pow(centerPointY-startPoint.getY(),2));

        if (!clockWise && startAngle < endAngle) {
            startAngle += 2 * Math.PI;
        } else if (clockWise && startAngle > endAngle) {
            endAngle += 2 * Math.PI;
        }

        int numPoints = (int)(7 * radius);
        double angleIncrement = (endAngle - startAngle) / numPoints;

        for (int i = 0; i <= numPoints; i++) {
            double currentAngle = startAngle + i * angleIncrement;
            int x = (int)(centerPointX + radius * Math.cos(currentAngle));
            int y = (int)(centerPointY + radius * Math.sin(currentAngle));
            pointsOnArc.add(new MyPoint(x, y));
        }

        this.endPoint = pointsOnArc.get(pointsOnArc.size() - 1);
        return pointsOnArc;
    }

    @Override
    public String toString() {
        return "CircularArc{" +
                "StartPoint=" + startPoint +
                ", EndPoint=" + endPoint +
                ", CenterPoint=" + "(" + (int)centerPointX + ", " + (int)centerPointY + ")" +
                '}';
    }

    public MyPoint getEndPoint() {
        return endPoint;
    }
}
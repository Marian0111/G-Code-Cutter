package org.example.Model;

public class MyPoint {
    private int x;
    private int y;

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean myEqual(MyPoint p){
        if(this.x == p.getX() && this.y == p.getY())
            return true;
        return false;
    }
}

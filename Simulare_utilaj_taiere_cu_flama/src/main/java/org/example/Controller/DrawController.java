package org.example.Controller;

import org.example.Model.*;
import org.example.View.*;

import java.util.*;


public class DrawController {
    private List<Object> lista;
    public DrawController(){
        ReaderController r = new ReaderController();
        r.readFile();
        lista = r.getContainer();
    }

    public void drawFromFile(View view){
        for (Object o : lista){
            if(o instanceof Line){
                Line l = (Line) o;
                ArrayList points = (ArrayList) l.rasterize();
                for (Object p : points){
                    if(p instanceof MyPoint){
                        MyPoint point = (MyPoint) p;
                        if (point.getX() >= 0 && point.getX() <= 289 && point.getY() >= 0 && point.getY() <= 149){
                            view.getMatrixPanel().fillSquare((MyPoint)p);
                        }
                        try {
                            Thread.sleep(9);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else if(o instanceof CircularArc){
                CircularArc c = (CircularArc) o;
                ArrayList points = (ArrayList) c.rasterize();
                for (Object p : points){
                    if(p instanceof MyPoint){
                        MyPoint point = (MyPoint) p;
                        if (point.getX() >= 0 && point.getX() <= 289 && point.getY() >= 0 && point.getY() <= 149){
                            view.getMatrixPanel().fillSquare((MyPoint)p);
                        }
                        try {
                            Thread.sleep(9);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
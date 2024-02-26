package org.example;

import org.example.Controller.*;
import org.example.View.*;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        DrawController dC = new DrawController();
        dC.drawFromFile(view);
    }
}
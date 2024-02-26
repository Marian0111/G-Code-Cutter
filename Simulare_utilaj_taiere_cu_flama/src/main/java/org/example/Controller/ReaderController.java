package org.example.Controller;

import org.example.Model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.*;

public class ReaderController {
    private static final String fileReadPath = "..\\Trajectory1.txt";
    private static final Path fileWritePath = Path.of("..\\Log.txt");
    private List<Object> container;
    private MyPoint startPoint;

    public ReaderController() {
        container = new ArrayList<>();
        startPoint = null;
        try {
            Files.write(fileWritePath, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileReadPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    Pattern pat = Pattern.compile("(G[0-9]*)( X-?[0-9]*)( Y-?[0-9]*)( R-?[0-9]*)?");
                    Matcher match = pat.matcher(line);
                    while (!match.hitEnd()) {
                        match.find();
                        Scanner scanner;
                        int x = 0;
                        int y = 0;
                        if (match.group(1) != null && match.group(2) != null && match.group(3) != null) {
                            scanner = new Scanner(match.group(2)).useDelimiter(" X");
                            if (scanner.hasNextInt()) {
                                x = scanner.nextInt();
                            }
                            scanner = new Scanner(match.group(3)).useDelimiter(" Y");
                            if (scanner.hasNextInt()) {
                                y = scanner.nextInt();
                            }
                            if (match.group(1).equals("G00")) {
                                if (startPoint == null) {
                                    if (x >= 0 && x <= 289 && y >= 0 && y <= 149) {
                                        startPoint = new MyPoint(x, y);
                                        String err = "  StartPoint set as " + new MyPoint(x, y) + "\n";
                                        try {
                                            Files.write(fileWritePath, err.getBytes(), StandardOpenOption.APPEND);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        String err = "! StartPoint out of grid " + new MyPoint(x, y) + "\n";
                                        try {
                                            Files.write(fileWritePath, err.getBytes(), StandardOpenOption.APPEND);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                } else {
                                    x = startPoint.getX() + x;
                                    y = startPoint.getY() + y;
                                    if (x >= 0 && x <= 289 && y >= 0 && y <= 149) {
                                        MyPoint endPoint = new MyPoint(x, y);
                                        Line l = new Line(startPoint, endPoint);
                                        container.add(l);
                                        startPoint = endPoint;
                                        try {
                                            Files.write(fileWritePath, ("  " + l.toString() + '\n').getBytes(), StandardOpenOption.APPEND);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        String err = "! Line out of the grid " + new Line(startPoint, new MyPoint(x, y)) + "\n";
                                        try {
                                            System.out.println(err);
                                            Files.write(fileWritePath, err.getBytes(), StandardOpenOption.APPEND);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }
                            } else if ((match.group(1).equals("G02") || match.group(1).equals("G03")) && match.group(4) != null && startPoint != null) {
                                int d = 0;
                                scanner = new Scanner(match.group(4)).useDelimiter(" R");
                                if (scanner.hasNextInt()) {
                                    d = scanner.nextInt();
                                }
                                    x = startPoint.getX() + x;
                                    y = startPoint.getY() + y;
                                    if (x >= 0 && x <= 289 && y >= 0 && y <= 149) {
                                        MyPoint endPoint = new MyPoint(x, y);
                                        CircularArc c = null;
                                        if(match.group(1).equals("G02")){
                                            c = new CircularArc(startPoint, endPoint, d, true);
                                        }else{
                                            c = new CircularArc(startPoint, endPoint, d, false);
                                        }
                                        ArrayList points = (ArrayList) c.rasterize();
                                        boolean inGrid = true;
                                        for (Object p : points){
                                            if(p instanceof MyPoint){
                                                MyPoint point = (MyPoint) p;
                                                if (point.getX() < 0 || point.getX() > 289 || point.getY() < 0 || point.getY() > 149){
                                                    inGrid = false;
                                                }
                                            }
                                        }
                                        if(inGrid) {
                                            container.add(c);
                                            startPoint = c.getEndPoint();
                                        }else{
                                            String err;
                                            if(match.group(1).equals("G02")) {
                                                err = "! Circular arc out of the grid " + new CircularArc(startPoint, new MyPoint(x, y), d, true) + "\n";
                                            }else{
                                                err = "! Circular arc out of the grid " + new CircularArc(startPoint, new MyPoint(x, y), d, false) + "\n";
                                            }
                                            try {
                                                Files.write(fileWritePath, err.getBytes(), StandardOpenOption.APPEND);
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                        try {
                                            Files.write(fileWritePath, ("  " + c.toString() + '\n').getBytes(), StandardOpenOption.APPEND);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        String err;
                                        if(match.group(1).equals("G02")) {
                                            err = "! Circular arc out of the grid " + new CircularArc(startPoint, new MyPoint(x, y), d, true) + "\n";
                                        }else{
                                            err = "! Circular arc out of the grid " + new CircularArc(startPoint, new MyPoint(x, y), d, false) + "\n";
                                        }
                                        try {
                                            Files.write(fileWritePath, err.getBytes(), StandardOpenOption.APPEND);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                            }
                            scanner.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            String err = "Something went wrong trying to read from file\n";
            try {
                Files.write(fileWritePath, err.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<Object> getContainer() {
        return container;
    }
}

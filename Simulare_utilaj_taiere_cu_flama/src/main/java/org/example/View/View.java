package org.example.View;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


import org.example.Model.*;

public class View extends JFrame {

    private static final int FRAME_WIDTH = 1500;
    private static final int FRAME_HEIGHT = 800;
    private static final int MATRIX_WIDTH = 1450;
    private static final int MATRIX_HEIGHT = 750;
    private static final int SQUARE_SIZE = 5;
    private MatrixPanel matrixPanel;

    public View() {
        setTitle("Pixel Matrix Grid");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        matrixPanel = new MatrixPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        int insetX = (FRAME_WIDTH - MATRIX_WIDTH - 15) / 2;
        int insetY = (FRAME_HEIGHT - MATRIX_HEIGHT - 38) / 2;
        gbc.insets = new Insets(insetY, insetX, insetY, insetX);

        add(matrixPanel, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public MatrixPanel getMatrixPanel() {
        return matrixPanel;
    }

    public class MatrixPanel extends JPanel {
        private List<MyPoint> filledPoints = new ArrayList<>();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGrid(g);
            fillSquares(g);
        }

        private void drawGrid(Graphics g) {
            setBackground(Color.WHITE);

            int gridRows = MATRIX_HEIGHT / SQUARE_SIZE;
            int gridCols = MATRIX_WIDTH / SQUARE_SIZE;

            for (int i = 0; i < gridCols; i++) {
                for (int j = 0; j < gridRows; j++) {
                    int x = i * SQUARE_SIZE;
                    int y = j * SQUARE_SIZE;

                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }

        private void fillSquares(Graphics g) {
            if(!filledPoints.isEmpty()) {
                for (MyPoint point : filledPoints) {
                    fillSquare(g, point);
                }
            }
        }

        public void fillSquare(MyPoint point) {
            filledPoints.add(point);
            repaint();
        }

        private void fillSquare(Graphics g, MyPoint point) {
            int x = point.getX() * SQUARE_SIZE;
            int y = point.getY() * SQUARE_SIZE;

            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
        }
    }
}

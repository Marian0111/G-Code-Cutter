package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class PopupMessageFrame extends JFrame {
    private JLabel messageLabel;

    public PopupMessageFrame(String message, boolean isError) {
        setUndecorated(true);
        setSize(300, 60);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Rounded corners

        messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(isError ? Color.RED : Color.GREEN);
        panel.add(messageLabel);

        getContentPane().setBackground(isError ? Color.RED : Color.GREEN);
        getContentPane().add(panel);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
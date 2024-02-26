package org.example.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CNCCommandView extends JFrame {
    private JComboBox<String> commandSelector;
    private JLabel labelX, labelY, labelR;
    private JTextField textFieldX, textFieldY, textFieldR;
    private JButton generateButton;
    private Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

    public CNCCommandView() {
        setTitle("G-code Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        String[] commands = {"start", "line", "circular arc clockwise", "circular arc anti-clockwise"};
        commandSelector = new JComboBox<>(commands);
        commandSelector.setBorder(border);

        labelX = new JLabel("X:");
        labelY = new JLabel("Y:");
        labelR = new JLabel("R:");
        textFieldX = new JTextField(15);
        textFieldY = new JTextField(15);
        textFieldR = new JTextField(15);

        textFieldX.setBorder(border);
        textFieldY.setBorder(border);
        textFieldR.setBorder(border);

        generateButton = new JButton("Generate");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Command:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(commandSelector, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelX, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(textFieldX, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelY, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(textFieldY, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelR, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(textFieldR, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(generateButton, gbc);

        add(panel);

        updateVisibility();

        commandSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateVisibility();
            }
        });
    }

    private void updateVisibility() {
        String selectedCommand = getSelectedCommand();
        boolean isCircularArc = selectedCommand.equals("circular arc clockwise") ||
                selectedCommand.equals("circular arc anti-clockwise");

        labelR.setVisible(isCircularArc);
        textFieldR.setVisible(isCircularArc);

        pack();
    }

    public String getSelectedCommand() {
        return (String) commandSelector.getSelectedItem();
    }

    public String getXValue() {
        return textFieldX.getText();
    }

    public String getYValue() {
        return textFieldY.getText();
    }

    public String getRValue() {
        return textFieldR.getText();
    }

    public void addGenerateButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
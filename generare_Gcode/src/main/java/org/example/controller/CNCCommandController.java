package org.example.controller;

import org.example.model.CNCCommandModel;
import org.example.view.CNCCommandView;
import org.example.view.PopupMessageFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CNCCommandController {
    private CNCCommandModel model;
    private CNCCommandView view;
    private static final Path fileWritePath = Path.of("..\\Trajectory5.txt");

    public CNCCommandController(CNCCommandModel model, CNCCommandView view) {
        this.model = model;
        this.view = view;

        // Delete everything from file.txt on construction
        try {
            Files.write(fileWritePath, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.addGenerateButtonListener(new GenerateButtonListener());
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCommand = view.getSelectedCommand();
            int x = 0;
            int y = 0;
            int r = 0;

            try {
                x = Integer.parseInt(view.getXValue());
                y = Integer.parseInt(view.getYValue());
                if ("circular arc clockwise".equals(selectedCommand) || "circular arc anti-clockwise".equals(selectedCommand)) {
                    r = Integer.parseInt(view.getRValue());
                }
            } catch (NumberFormatException ex) {
                displayPopupMessage("Error: Please enter valid numbers.", true);
                return; // Stop execution if parsing fails
            }

            String command;

            switch (selectedCommand) {
                case "start":
                case "line":
                    command = String.format("G00 X%d Y%d", x, y);
                    break;
                case "circular arc clockwise":
                    command = String.format("G02 X%d Y%d R%d", x, y, r);
                    break;
                case "circular arc anti-clockwise":
                    command = String.format("G03 X%d Y%d R%d", x, y, r);
                    break;
                default:
                    command = "";
            }

            model.setCommand(command);

            try {
                Files.write(fileWritePath, (command + '\n').getBytes(), StandardOpenOption.APPEND);
                displayPopupMessage("G-code generated successfully!", false);
            } catch (IOException ex) {
                displayPopupMessage("Error writing to file", true);
            }
        }

        private void displayPopupMessage(String message, boolean isError) {
            PopupMessageFrame popupFrame = new PopupMessageFrame(message, isError);
            popupFrame.setVisible(true);
        }
    }
}
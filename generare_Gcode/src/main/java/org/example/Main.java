package org.example;
import org.example.controller.CNCCommandController;
import org.example.model.CNCCommandModel;
import org.example.view.CNCCommandView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CNCCommandModel model = new CNCCommandModel();
        CNCCommandView view = new CNCCommandView();
        CNCCommandController controller = new CNCCommandController(model, view);

        SwingUtilities.invokeLater(() -> view.setVisible(true));
    }
}
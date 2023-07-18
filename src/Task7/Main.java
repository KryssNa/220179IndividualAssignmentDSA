package Task7;

import Task7.View.AllUserFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AllUserFrame frame = new AllUserFrame();
            frame.setVisible(true);
        });
    }
}

package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {
    public GameWindow(GamePanel gamePanel) {


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(gamePanel);
        setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("Focus gained");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().focusLost();
            }
        });
    }
}

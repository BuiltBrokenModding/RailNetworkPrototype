package com.darkguardsman.railnet.ui.components;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/20/18.
 */
public class DialMain {
    public static void main(String... args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Dial dial = new Dial(5,400);
        for (int i = 0; i < 8; i++) {
            dial.addPosition(i * 45);
        }
        panel.add(dial, BorderLayout.NORTH);

        JTextField field = new JTextField();
        dial.addSelectChangeListener((dialComponent, index) -> field.setText(index + ">>" + dialComponent.getSelectedAngle()));
        panel.add(field);

        frame.add(panel, BorderLayout.SOUTH);


        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setTitle("Dial Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
}

package com.darkguardsman.railnet.ui;

import com.darkguardsman.railnet.ui.panels.PanelLineRails;

import javax.swing.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class FrameMain extends JFrame {
    public FrameMain() {

    }

    public void init() {
        //setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = null;

        tabbedPane.addTab("Line Rails", icon, new PanelLineRails(),
                "Visual test of display of rail types");

        add(tabbedPane);
    }
}

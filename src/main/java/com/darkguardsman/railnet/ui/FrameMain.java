package com.darkguardsman.railnet.ui;

import com.darkguardsman.railnet.ui.graphics.*;
import com.darkguardsman.railnet.ui.graphics.render.PlotGridRender;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class FrameMain extends JFrame {
    public FrameMain() {

    }

    public void init() {
        setLayout(new BorderLayout());
        add(createRenderPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.WEST);

    }

    private JPanel createRenderPanel() {
        RenderPanel panel = new RenderPanel();
        panel.addRendersToRun(new PlotGridRender());

        return panel;
    }

    private JPanel createControlPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(20, 2));



        return panel;
    }
}

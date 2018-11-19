package com.darkguardsman.railnet.ui.panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/19/18.
 */
public abstract class PanelAbstractTest extends JPanel {
    public PanelAbstractTest()
    {
        setLayout(new BorderLayout());
        add(createRenderPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.WEST);
    }

    protected abstract JPanel createRenderPanel();

    protected abstract JPanel createControlPanel();

    protected void addSpacer(JPanel panel)
    {
        panel.add(new JPanel());
        panel.add(new JPanel());
    }
}

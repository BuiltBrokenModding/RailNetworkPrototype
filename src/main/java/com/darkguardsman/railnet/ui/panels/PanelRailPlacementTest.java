package com.darkguardsman.railnet.ui.panels;

import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.MouseMotionListenerCurve;
import com.darkguardsman.railnet.ui.graphics.RenderPanel;
import com.darkguardsman.railnet.ui.graphics.rail.RailRenderUtil;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

/**
 * Used to test logic for line rails visually
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PanelRailPlacementTest extends PanelAbstractTest {

    @Override
    protected void addRenderPanelRenders(RenderPanel panel) {
        super.addRenderPanelRenders(panel);
        panel.setViewBoundSize(20);
    }

    @Override
    protected void addRenderPanelListeners(RenderPanel panel)
    {
        //TODO create mouse listener
    }

    @Override
    protected JPanel createControlPanel() {
        JPanel panel = new JPanel();       
        panel.setLayout(new GridLayout(20, 2));
        JButton button;

        addSpacer(panel);
        addSpacer(panel);

        panel.add(new JPanel());
        button = new JButton("Clear");
        button.addActionListener((a) -> renderPanel.clear());
        panel.add(button);


        return panel;
    }
}

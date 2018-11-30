package com.darkguardsman.railnet.ui.panels.curve;

import com.darkguardsman.railnet.ui.graphics.RenderPanel;

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
public class PanelCurveRailAlgMouse extends PanelCurveRailAlg {

    public PanelCurveRailAlgMouse() {
        super();
        purposeTextArea.setText("Version of Curve rail test that includes mouse input for rapid testing.");
    }

    @Override
    protected void addRenderPanelListeners(RenderPanel panel) {
        renderPanel.addMouseMotionListener(new MouseMotionListenerCurveAlg(this));
    }

    @Override
    protected JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(20, 2));

        panel.add(new JLabel("Start:"));
        panel.add(new JPanel());
        panel.add(new JLabel("X:"));
        panel.add(startXField = new JTextField("0"));
        panel.add(new JLabel("Z:"));
        panel.add(startZField = new JTextField("0"));


        panel.add(new JLabel("Start Angle:"));
        panel.add(startAngleField = new JTextField("0"));

        panel.add(new JLabel("End Angle:"));
        panel.add(endAngleField = new JTextField("0"));

        return panel;
    }
}

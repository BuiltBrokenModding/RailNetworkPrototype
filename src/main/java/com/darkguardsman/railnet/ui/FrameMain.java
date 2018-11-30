package com.darkguardsman.railnet.ui;

import com.darkguardsman.railnet.ui.panels.*;
import com.darkguardsman.railnet.ui.panels.curve.PanelCurveRailAlg;
import com.darkguardsman.railnet.ui.panels.curve.PanelCurveRailAlgMouse;
import com.darkguardsman.railnet.ui.panels.segments.PanelCurveRails;
import com.darkguardsman.railnet.ui.panels.segments.PanelLineRails;
import com.darkguardsman.railnet.ui.panels.snap.PanelRailPlacementTest;

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
                "Visual test of line rails");

        tabbedPane.addTab("Curve Rail Alg", icon, new PanelCurveRailAlg(),
                "Visual test of curved rail alg");

        tabbedPane.addTab("Curve Rail Alg Mouse", icon, new PanelCurveRailAlgMouse(),
                "Visual test of curved rail alg");

        tabbedPane.addTab("Curve Rails", icon, new PanelCurveRails(),
                "Visual test of pre-generated curved rails");

        tabbedPane.addTab("Placement", icon, new PanelRailPlacementTest(),
                "Test of rail placement logic");


        //Place everything before, everything after is render tests
        tabbedPane.addTab("Grid Render Test", icon, new PanelGridRenderTest(),
                "Tests that the grid is rendering correctly");

        add(tabbedPane);
    }
}

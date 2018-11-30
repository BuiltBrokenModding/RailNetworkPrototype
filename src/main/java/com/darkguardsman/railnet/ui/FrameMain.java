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

        JTabbedPane mainTabPanel = new JTabbedPane();

        //------------------
        mainTabPanel.addTab("Placement", null, new PanelRailPlacementTest(),
                "Test of rail placement logic");

        //------------------
        JTabbedPane simpleTestPanel = new JTabbedPane();

        simpleTestPanel.addTab("Line Rails", null, new PanelLineRails(),
                "Visual test of line rails");

        simpleTestPanel.addTab("Curve Rails", null, new PanelCurveRails(),
                "Visual test of pre-generated curved rails");

        mainTabPanel.addTab("Simple Tests", null, simpleTestPanel,
                "Simple tests checking single components");

        //------------------

        JTabbedPane testPanel = new JTabbedPane();

        testPanel .addTab("Curve Rail Alg", null, new PanelCurveRailAlg(),
                "Visual test of curved rail alg");

        testPanel .addTab("Curve Rail Alg Mouse", null, new PanelCurveRailAlgMouse(),
                "Visual test of curved rail alg");

        //Place everything before, everything after is render tests
        testPanel .addTab("Grid Render Test", null, new PanelGridRenderTest(),
                "Tests that the grid is rendering correctly");

        mainTabPanel.addTab("Alg Tests", null, testPanel,
                "Tests aimed at checking algs used in other tests");

        //------------------






        add(mainTabPanel);
    }
}

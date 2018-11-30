package com.darkguardsman.railnet.ui.panels;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;
import com.darkguardsman.railnet.ui.graphics.rail.RailRenderUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to test logic for line rails visually
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PanelLineRails extends PanelAbstractTest {

    protected JTextField distanceField;

    public PanelLineRails()
    {
        super("Test path point generation for line rails." +
                "\n\nMeant to act as a low level test without any snap point prediction." +
                "\nAs well a test of possible strait rail directions.");
    }

    @Override
    protected JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(20, 2));
        JButton button;

        panel.add(new JLabel("Distance:"));
        panel.add(distanceField = new JTextField("10"));

        for (RailHeading railHeading : RailHeading.values()) {
            button = new JButton("" + railHeading.name());
            button.addActionListener((a) -> generateLineRail(railHeading));
            panel.add(button);
        }

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        panel.add(new JPanel());
        button = new JButton("Clear");
        button.addActionListener((a) -> renderPanel.clear());
        panel.add(button);


        return panel;
    }

    protected void generateLineRail(RailHeading heading) {
        double distance = Math.floor(Double.parseDouble(distanceField.getText().trim()));
        generateLineRail(heading, distance);
    }

    protected void generateLineRail(RailHeading heading, double distance) {

        //Reset data
        clearTest();

        //Set start position negative of center for best visual layout
        double x = -heading.offsetX * (distance / 2);
        double z = -heading.offsetZ * (distance / 2);

        //Debug info so we can see the math
        log("Generating line rail for render");
        log("\tWith Heading: " + heading);
        log("\tStart: " + x + ", " + z);
        log("\tDistance: " + distance);

        //Generate rail and get dots
        List<PlotPoint> dots = new ArrayList();
        newRail(RailRenderUtil.generateLineRail(dots, heading, x, z, distance));

        //More debug
        log("\tPoints:");
        log("\t\tSize: " + dots.size());

        //Extra line to visual show start to end path, added to debug for issues
        pointRender.addLine(dots.get(0), dots.get(dots.size() - 1), Color.blue, 8);


        //Add dots to render, include lines to trace path easier
        for (int i = 0; i < dots.size(); i++) {

            PlotPoint dot = dots.get(i);

            //Debug data to show the exact data used
            log("\t\t[" + i + "]: " + dot.x + ", " + dot.y);

            //Adds node and sets a line to last node
            pointRender.addPlusLinkLast(dot, Color.CYAN, 2); //TODO consider moving links to data generator
        }

        //Trigger UI to draw new data
        renderPanel.repaint();
    }
}

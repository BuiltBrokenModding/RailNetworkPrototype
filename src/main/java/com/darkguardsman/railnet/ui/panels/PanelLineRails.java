package com.darkguardsman.railnet.ui.panels;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentLine;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;
import com.darkguardsman.railnet.ui.graphics.RenderPanel;
import com.darkguardsman.railnet.ui.graphics.render.PlotCenterRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotGridRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Used to test logic for line rails visually
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PanelLineRails extends JPanel {

    protected RenderPanel renderPanel;
    protected PlotPointRender pointRender;

    protected JTextField distanceField;

    public PanelLineRails() {
        setLayout(new BorderLayout());
        add(createRenderPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.WEST);
    }

    private JPanel createRenderPanel() {
        renderPanel = new RenderPanel();
        renderPanel.upperBound = new Dimension(10, 10);
        renderPanel.lowerBound = new Dimension(-10, -10);

        renderPanel.addRendersToRun(new PlotGridRender(1, 1));
        renderPanel.addRendersToRun(new PlotGridRender(2, 2, Color.BLUE));
        renderPanel.addRendersToRun(new PlotCenterRender());
        renderPanel.addRendersToRun(pointRender = new PlotPointRender(null));
        return renderPanel;
    }

    private JPanel createControlPanel() {
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
        renderPanel.clear();

        try {
            double distance = Math.floor(Double.parseDouble(distanceField.getText().trim()));
            double x = -heading.offsetX * (distance / 2);
            double z = -heading.offsetZ * (distance / 2);

            System.out.println("Generating line rail for render");
            System.out.println("\tWith Heading: " + heading);
            System.out.println("\tStart: " + x + ", " + z);
            System.out.println("\tDistance: " + distance);

            RailSegmentLine segment = new RailSegmentLine(heading, (float) x, 0, (float) z, (int) distance);

            System.out.println("\tPoints: ");
            List<IRailPathPoint> points = segment.getAllPaths().get(0).getPathPoints();
            System.out.println("\t\tSize: " + points.size());

            pointRender.add(new PlotPoint(segment.start.x(), segment.start.z(), Color.CYAN, 14));
            pointRender.add(new PlotPoint(segment.end.x(), segment.end.z(), Color.CYAN, 14));

            for (int i = 0; i < points.size(); i++) {
                IRailPathPoint pp = points.get(i);
                if (i == 0 || i - 1 == points.size()) {
                    pointRender.add(new PlotPoint(pp.x(), pp.z(), Color.BLUE, 10));
                } else {
                    pointRender.addPlusLinkLast(new PlotPoint(pp.x(), pp.z(), Color.BLUE, 10), Color.GREEN, 2);
                }
                System.out.println("\t\t[" + i + "]: " + pp.x() + ", " + pp.z());
            }
            renderPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            renderPanel.clear();
            //TODO display error
        }

    }
}

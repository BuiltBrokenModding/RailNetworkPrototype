package com.darkguardsman.railnet.ui.panels;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.data.rail.segments.RailSegment;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentLine;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;
import com.darkguardsman.railnet.ui.graphics.RenderPanel;
import com.darkguardsman.railnet.ui.graphics.render.PlotCenterRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotGridRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

/**
 * Used to test logic for line rails visually
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PanelCurveRails extends JPanel {

    protected RenderPanel renderPanel;
    protected PlotPointRender pointRender;

    protected JTextField startXField;
    protected JTextField startZField;
    protected JTextField endXField;
    protected JTextField endZField;
    protected JTextField startAngleField;
    protected JTextField endAngleField;

    public PanelCurveRails() {
        setLayout(new BorderLayout());
        add(createRenderPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.WEST);
    }

    private JPanel createRenderPanel() {
        renderPanel = new RenderPanel();
        renderPanel.upperBound = new Dimension(20, 20);
        renderPanel.lowerBound = new Dimension(-20, -20);

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

        panel.add(new JLabel("Start:"));
        panel.add(new JPanel());
        panel.add(new JLabel("X:"));
        panel.add(startXField = new JTextField("-5"));
        panel.add(new JLabel("Z:"));
        panel.add(startZField = new JTextField("-5"));

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        panel.add(new JLabel("End:"));
        panel.add(new JPanel());
        panel.add(new JLabel("X:"));
        panel.add(endXField = new JTextField("5"));
        panel.add(new JLabel("Z:"));
        panel.add(endZField = new JTextField("5"));

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        panel.add(new JLabel("Start Angle:"));
        panel.add(startAngleField = new JTextField("0"));
        panel.add(new JLabel("End Angle:"));
        panel.add(endAngleField = new JTextField("90"));

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        panel.add(new JPanel());
        button = new JButton("Generate");
        button.addActionListener((a) -> generateRail());
        panel.add(button);

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

    protected void generateRail()
    {
        double startX;
        double startZ;
        double endX;
        double endZ;
        double startAngle;
        double endAngle;
        try {
            startX = Double.parseDouble(startXField.getText().trim());
            startZ = Double.parseDouble(startZField.getText().trim());
            endX = Double.parseDouble(endXField.getText().trim());
            endZ = Double.parseDouble(endZField.getText().trim());
            startAngle = Double.parseDouble(startAngleField.getText().trim());
            endAngle = Double.parseDouble(endAngleField.getText().trim());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //TODO display to user that data is invalid
            return;
        }

        generateRail(new Pos(startX, 0, startZ), new Pos(endX, 0, endZ), startAngle, endAngle);
    }

    protected void generateRail(Pos start, Pos end, double startAngle, double endAngle) {
        renderPanel.clear();

        try {

            System.out.println("Generating line rail for render");
            System.out.println("\tStart: " + start);
            System.out.println("\tend: " + end);
            System.out.println("\tAngles: " + startAngle + ", " + endAngle);

            RailSegmentCurve segment = new RailSegmentCurve(start, end, (float)startAngle, (float)endAngle);

            pointRender.add(new PlotPoint(segment.start.x(), segment.start.z(), Color.CYAN, 14));
            pointRender.add(new PlotPoint(segment.end.x(), segment.end.z(), Color.CYAN, 14));

            System.out.println("\tPoints: ");
            List<IRailPathPoint> points = segment.getAllPaths().get(0).getPathPoints();
            System.out.println("\t\tSize: " + points.size());

            for (int i = 0; i < points.size(); i++) {
                IRailPathPoint pp = points.get(i);
                if (i == 0) {
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

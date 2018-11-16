package com.darkguardsman.railnet.ui.panels;

import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.RenderPanel;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;
import com.darkguardsman.railnet.ui.graphics.rail.RailRenderUtil;
import com.darkguardsman.railnet.ui.graphics.render.PlotCenterRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotGridRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
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

    public PanelCurveRails() {
        setLayout(new BorderLayout());
        add(createRenderPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.WEST);
    }

    private JPanel createRenderPanel() {
        renderPanel = new RenderPanel();
        renderPanel.upperBound = new Dimension(5, 5);
        renderPanel.lowerBound = new Dimension(-5, -5);

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

        for(RailTestSet railTestSet : RailTestSet.values())
        {
            button = new JButton(railTestSet.name().replace("_", " "));
            button.addActionListener((a) -> generate(railTestSet));
            panel.add(button);
        }

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

    protected void generate(RailTestSet railTestSet)
    {
        renderPanel.clear();

        renderPanel.upperBound = new Dimension(railTestSet.startX + 2, railTestSet.endX + 2);
        renderPanel.lowerBound = new Dimension(railTestSet.startX - 2, railTestSet.endX - 2);

        RailRenderUtil.generateRail(pointRender,
                new Pos(railTestSet.startX, 0, railTestSet.endX),
                new Pos(railTestSet.startZ, 0, railTestSet.endZ),
                railTestSet.startAngle,
                railTestSet.endAngle, true);

        renderPanel.repaint();
    }

    enum RailTestSet
    {
        UP_2x2__90_TO_0(90, 0, -1, -1, 1, 1),
        UP_2x2__0_TO_90(0, 90, -1, -1, 1, 1),
        DOWN_2x2__0_TO_90(0, 90, -1, -1, 1, 1),
        DOWN_2x2__90_TO_0(90, 0, -1, -1, 1, 1);

        public final double startAngle;
        public final double endAngle;
        public final int startX;
        public final int startZ;
        public final int endX;
        public final int endZ;

        RailTestSet(double startAngle, double endAngle, int startX, int startZ, int endX, int endZ) {
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.startX = startX;
            this.startZ = startZ;
            this.endX = endX;
            this.endZ = endZ;
        }
    }
}

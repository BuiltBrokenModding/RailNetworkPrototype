package com.darkguardsman.railnet.ui.graphics.render;

import com.darkguardsman.railnet.ui.graphics.RenderPanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PlotCenterRender implements IPlotRenderObject {

    public boolean yAxis = true;
    public boolean xAxis = true;
    public boolean centerDot = true;
    public Color lineColor = Color.RED;
    public Color centerDotColor = new Color(125, 59, 18);

    @Override
    public void draw(Graphics2D g2, RenderPanel renderPanel) {

        g2.setColor(lineColor);

        //X axis
        if (xAxis) {
            renderPanel.drawHorizontalLine(g2, lineColor, 0);
        }

        //Y axis
        if (yAxis) {
            renderPanel.drawVerticalLine(g2, lineColor, 0);
        }

        if (centerDot) {
            renderPanel.drawCircle(g2, centerDotColor,  0, 0, 2, true);
        }

    }

    @Override
    public double getMaxY() {
        return 0;
    }

    @Override
    public double getMaxX() {
        return 0;
    }

    @Override
    public double getMinY() {
        return 0;
    }

    @Override
    public double getMinX() {
        return 0;
    }

    @Override
    public void clearData() {

    }

    @Override
    public void refreshData() {

    }
}

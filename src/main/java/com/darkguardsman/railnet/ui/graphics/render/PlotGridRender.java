package com.darkguardsman.railnet.ui.graphics.render;

import com.darkguardsman.railnet.ui.graphics.RenderPanel;

import java.awt.*;
import java.awt.geom.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PlotGridRender implements IPlotRenderObject {

    public double plotLineSpacingX = -1;
    public double plotLineSpacingY = -1;

    @Override
    public void draw(Graphics2D g2, RenderPanel renderPanel)
    {
        if (plotLineSpacingX > 0)
        {
            drawGridX(g2, renderPanel);
        }
        if (plotLineSpacingY > 0)
        {
            drawGridY(g2, renderPanel);
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

    protected void drawGridX(Graphics2D g2, RenderPanel renderPanel)
    {
        double start = 0;
        double end = renderPanel.getDrawMaxX();

        double current = start;
        double xScale = renderPanel.getScaleX();

        while (current < end)
        {
            //Increase
            current += plotLineSpacingX;

            //Get pixel point of x
            int x = renderPanel.PAD + (int) Math.ceil(current * xScale);

            //Draw line
            g2.draw(new Line2D.Double(x, renderPanel.PAD, x, renderPanel.getHeight() - renderPanel.PAD));
        }
    }

    protected void drawGridY(Graphics2D g2, RenderPanel renderPanel)
    {
        double start = 0;
        double end = renderPanel.getDrawMaxY();

        double current = start;
        double xScale = renderPanel.getScaleY();

        while (current < end)
        {
            //Increase
            current += plotLineSpacingY;

            //Get pixel point of x
            int y = renderPanel.PAD + (int) Math.ceil(current * xScale);

            //Draw line
            g2.draw(new Line2D.Double(renderPanel.PAD, y, renderPanel.getWidth() - renderPanel.PAD, y));
        }
    }
}

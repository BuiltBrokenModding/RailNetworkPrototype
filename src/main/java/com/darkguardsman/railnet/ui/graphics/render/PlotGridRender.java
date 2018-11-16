package com.darkguardsman.railnet.ui.graphics.render;

import com.darkguardsman.railnet.ui.graphics.RenderPanel;

import java.awt.*;
import java.awt.geom.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PlotGridRender implements IPlotRenderObject {

    public double plotLineSpacingX;
    public double plotLineSpacingY;
    public Color lineColor = Color.BLACK;

    public PlotGridRender(double x, double y)
    {
        this.plotLineSpacingX = x;
        this.plotLineSpacingY = y;
    }

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
        int neg_x_steps = (int)Math.floor(renderPanel.getDrawMinX() / plotLineSpacingX);
        double current = neg_x_steps * plotLineSpacingX;
        while(current < renderPanel.getDrawMaxX())
        {
            renderPanel.drawVerticalLine(g2, lineColor, current);
            current += plotLineSpacingX;
        }
    }

    protected void drawGridY(Graphics2D g2, RenderPanel renderPanel)
    {
        int neg_x_steps = (int)Math.floor(renderPanel.getDrawMinY() / plotLineSpacingY);
        double current = neg_x_steps * plotLineSpacingY;
        while(current < renderPanel.getDrawMaxY())
        {
            renderPanel.drawHorizontalLine(g2, lineColor, current);
            current += plotLineSpacingY;
        }
    }
}

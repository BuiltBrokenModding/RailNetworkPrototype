package com.darkguardsman.railnet.ui.graphics.render;

import com.darkguardsman.railnet.ui.graphics.PlotPoint;
import com.darkguardsman.railnet.ui.graphics.RenderPanel;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PlotPointRender implements IPlotRenderObject {


    /** Data to display in the panel */
    protected List<PlotPoint> data = new ArrayList();

    @Override
    public void draw(Graphics2D g2, RenderPanel renderPanel)
    {
        if (data != null && !data.isEmpty())
        {
            //Render data points
            for (PlotPoint pos : data)
            {
                renderPanel.drawCircle(g2, pos.color, pos.x, pos.y, pos.size, true);
            }
        }
    }

    @Override
    public double getMaxY() {
        double max = data.get(0).y();
        for (int i = 1; i < data.size(); i++)
        {
            if (data.get(i).y() > max)
            {
                max = data.get(i).y();
            }
        }
        return max;
    }

    @Override
    public double getMaxX() {
        double max = data.get(0).x();
        for (int i = 1; i < data.size(); i++)
        {
            if (data.get(i).x() > max)
            {
                max = data.get(i).x();
            }
        }
        return max;
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

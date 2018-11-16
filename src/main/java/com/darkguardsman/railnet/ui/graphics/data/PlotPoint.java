package com.darkguardsman.railnet.ui.graphics.data;

import java.awt.*;

/**
 * 2D data point with color
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 2/27/2018.
 */
public class PlotPoint
{
    //Data position, not scaled to view
    public final double x;
    public final double y;

    //Render data
    public Color color;
    public int size = 4;

    public Color edgeColor;
    public int edgeSize;


    public PlotPoint(double x, double y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public PlotPoint(double x, double y, Color color, int size)
    {
        this(x, y, color);
        this.size = size;
    }

    public double x()
    {
        return x;
    }

    public double y()
    {
        return y;
    }

    public void addEdge(Color color, int size) {
        this.edgeColor = color;
        this.edgeSize = size;
    }
}
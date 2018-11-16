package com.darkguardsman.railnet.ui.graphics.data;

import java.awt.Color;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/16/18.
 */
public class PlotConnection {
    public PlotPoint a;
    public PlotPoint b;
    public Color lineColor;
    public int lineSize = 2;

    public PlotConnection(PlotPoint a, PlotPoint b, Color lineColor)
    {
        this.a = a;
        this.b = b;
        this.lineColor = lineColor;
    }
}

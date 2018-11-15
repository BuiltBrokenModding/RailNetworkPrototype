package com.darkguardsman.railnet.ui.graphics.render;

import com.darkguardsman.railnet.ui.graphics.RenderPanel;

import java.awt.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public interface IPlotRenderObject {

    /**
     * Called to draw the object on the panel
     *
     * @param g2          - graphics object
     * @param renderPanel - panel to render to
     */
    void draw(Graphics2D g2, RenderPanel renderPanel);

    double getMaxY();

    double getMaxX();

    double getMinY();

    double getMinX();

    /**
     * Called to clear all data used
     * by the renderer.
     */
    void clearData();

    /**
     * Called to pull in any data
     * used by the renderer.
     */
    void refreshData();
}

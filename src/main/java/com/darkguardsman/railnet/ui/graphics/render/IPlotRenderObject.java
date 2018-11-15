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

    default double getMaxY()
    {
        return 0;
    }

    default double getMaxX()
    {
        return 0;
    }

    default double getMinY()
    {
        return 0;
    }

    default double getMinX()
    {
        return 0;
    }

    /**
     * Does the render object have a size or defined
     * shape that would take up a space that can be
     * thought of as a size.
     *
     * By default most components have a size for the render.
     * The exceptions to this are renders that exist to support
     * other renders or data sets. This includes grid renders,
     * line renders, and measurement tools. Components that are
     * placed to  support reading the data. As well auto scale
     * with the data or other components.
     *
     * @return true to define a size;
     */
    default boolean hasSize()
    {
        return getMaxY() != getMinY() && getMaxX() != getMinX();
    }

    /**
     * Called to clear all data used
     * by the renderer.
     */
    default void clearData()
    {

    }


    /**
     * Called to pull in any data
     * used by the renderer.
     */
    default void refreshData()
    {

    }
}

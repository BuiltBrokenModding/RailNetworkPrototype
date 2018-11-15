package com.darkguardsman.railnet.ui.graphics;


import com.darkguardsman.railnet.ui.graphics.render.IPlotRenderObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Simple panel used to draw objects to screen
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert)
 */
public class RenderPanel extends JPanel {
    protected List<IPlotRenderObject> rendersToRun = new ArrayList();
    /**
     * Spacing from each side
     */
    public int PAD = 20;

    int plotSizeX = -1;
    int plotSizeY = -1;

    public RenderPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBorder(g2);
        rendersToRun.forEach(render -> render.draw(g2, this));
    }

    /**
     * Draws a border around the component to define the edge
     *
     * @param g2
     */
    protected void drawBorder(Graphics2D g2) {
        g2.drawRect(1, 1, getWidth() - 2, getHeight() - 2); //TODO why -2?
    }

    public void drawCircle(Graphics2D g2, Color color, double point_x, double point_y, double size, boolean fill) {
        drawEllipse(g2, color, point_x, point_y, size, size, fill);
    }

    public void drawEllipse(Graphics2D g2, Color color, double point_x, double point_y, double size_x, double size_y, boolean fill) {
        //Calculate scale to fit display
        double scaleX = getScaleX();
        double scaleY = getScaleY();

        //Get x & y, render position is based on data point plus offset, scaled to match view, and then offset by padding to avoid edges
        double x = PAD + scaleX * (point_x + getOffsetX());
        double y = getHeight() - PAD - scaleY * (point_y + getOffsetY());

        //Only render if the ellipse will be in view TODO check size not just center
        if (x >= 0 && x <= getWidth() && y <= getHeight()) {
            //Generate circle
            Ellipse2D circle = new Ellipse2D.Double(x - (size_x / 2), y - (size_y / 2), size_x, size_y);

            //Set color
            g2.setPaint(color != null ? color : Color.red);

            //Draw
            if (fill) {
                g2.fill(circle);
            } else {
                g2.draw(circle);
            }
        }
    }

    /**
     * Scale to draw the data on the screen.
     * <p>
     * Modifies the position to correspond to the pixel location
     *
     * @return scale of view ((width - padding) / size)
     */
    public double getScaleX() {
        return (double) (getWidth() - 2 * PAD) / getRenderComponentWidth();
    }

    /**
     * Scale to draw the data on the screen.
     * <p>
     * Modifies the position to correspond to the pixel location
     *
     * @return scale of view ((width - padding) / size)
     */
    public double getScaleY() {
        return (double) (getHeight() - 2 * PAD) / getRenderComponentHeight();
    }

    public double getDrawMaxX() {
        return plotSizeX > 0 ? plotSizeX : getPointMaxX();
    }

    public double getDrawMaxY() {
        return plotSizeY > 0 ? plotSizeY : getPointMaxY();
    }

    /**
     * Offset for the data to ensure
     * all data renders positive x & y
     *
     * @return
     */
    public double getOffsetX() {
        return -getPointMinX();
    }

    /**
     * Offset for the data to ensure
     * all data renders positive x & y
     *
     * @return
     */
    public double getOffsetY() {
        return -getPointMinY();
    }

    /**
     * The distance components span in the view
     * in the X axis. This is not scaled to screen
     * size but to data size.
     *
     * @return
     */
    public double getRenderComponentWidth() {
        return getPointMaxX() - getPointMinX();
    }

    /**
     * The distance components span in the view
     * in the Y axis. This is not scaled to screen
     * size but to data size;
     *
     * @return
     */
    public double getRenderComponentHeight() {
        return getPointMaxY() - getPointMaxY();
    }

    /**
     * Max y value in the data set
     *
     * @return
     */
    public double getPointMaxY() {
        return rendersToRun.stream()
                .filter(a -> a.hasSize())
                .max(Comparator.comparingDouble(IPlotRenderObject::getMaxY))
                .orElseGet(() -> new IPlotRenderObject(){
                    @Override
                    public void draw(Graphics2D g2, RenderPanel renderPanel) {

                    }

                    @Override
                    public double getMaxY() {
                        return 0;
                    }
                }).getMaxY();
    }

    /**
     * Max x value in the data set
     *
     * @return
     */
    public double getPointMaxX() {
        return rendersToRun.stream()
                .filter(a -> a.hasSize())
                .max(Comparator.comparingDouble(IPlotRenderObject::getMaxX))
                .orElseGet(() -> new IPlotRenderObject(){
                    @Override
                    public void draw(Graphics2D g2, RenderPanel renderPanel) {

                    }

                    @Override
                    public double getMaxX() {
                        return 0;
                    }
                }).getMaxX();
    }

    /**
     * Max y value in the data set
     *
     * @return
     */
    public double getPointMinY() {
        return rendersToRun.stream()
                .filter(a -> a.hasSize())
                .min(Comparator.comparingDouble(IPlotRenderObject::getMinY))
                .orElseGet(() -> new IPlotRenderObject(){
                    @Override
                    public void draw(Graphics2D g2, RenderPanel renderPanel) {

                    }

                    @Override
                    public double getMinY() {
                        return 0;
                    }
                }).getMinY();
    }

    /**
     * Max x value in the data set
     *
     * @return
     */
    public double getPointMinX() {
        return rendersToRun.stream()
                .filter(a -> a.hasSize())
                .min(Comparator.comparingDouble(IPlotRenderObject::getMinX))
                .orElseGet(() -> new IPlotRenderObject(){
                    @Override
                    public void draw(Graphics2D g2, RenderPanel renderPanel) {

                    }

                    @Override
                    public double getMinX() {
                        return 0;
                    }
                }).getMinX();
    }

    /**
     * Sets the plot size of the display.
     * <p>
     * By default the display will auto scale to match the data.
     * This can be used to ensure the data scales to a defined value.
     *
     * @param x
     * @param y
     */
    public void setPlotSize(int x, int y) {
        this.plotSizeY = y;
        this.plotSizeX = x;
    }

    public int getPlotSizeX() {
        return plotSizeX;
    }

    public int getPlotSizeY() {
        return plotSizeY;
    }

    public void addRendersToRun(IPlotRenderObject renderFunction) {
        rendersToRun.add(renderFunction);
    }

    public void clear() {
        rendersToRun.forEach(r -> r.clearData());
    }
}
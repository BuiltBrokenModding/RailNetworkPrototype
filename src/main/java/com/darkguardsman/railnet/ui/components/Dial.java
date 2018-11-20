package com.darkguardsman.railnet.ui.components;

import com.darkguardsman.railnet.ui.graphics.G2DHelpers;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

/**
 * Round dial used to select options
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/19/18.
 */
public class Dial extends JPanel implements ComponentListener {

    protected final List<Integer> dialPositions = new ArrayList();

    protected int dialPosition = 0;

    protected int drawAreaSize;
    protected int dialOutsideSize;

    protected int pad = 3;

    protected int centerX;
    protected int centerY;

    protected float dialFaceScale = 0.8f;
    protected float clickDistance;

    private List<BiConsumer<Dial, Integer>> onSelectChangedList = new ArrayList();

    /**
     * @param clickDistance - allow degree distance to accept a click
     */
    public Dial(float clickDistance) {
        this(clickDistance, 100);
    }

    /**
     * @param clickDistance - allow degree distance to accept a click
     * @param size
     */
    public Dial(float clickDistance, int size) {
        this.clickDistance = clickDistance;
        setMinimumSize(new Dimension(size, size));
        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        //TODO create mouse listener for clicks on dial positions to set position
        addMouseListener(new DialMouseListener(this));
        addComponentListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {

        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBoarder(g2);
        drawDialBackGround(g2);
        drawDialFace(g2);
    }

    protected void drawBoarder(Graphics2D g2) {
        g2.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
    }

    protected void drawDialBackGround(Graphics2D g2) {
        //Draw dial background
        G2DHelpers.drawCircle(g2, Color.BLUE, centerX, centerY, dialOutsideSize, true);

        //Draw dial positions
        g2.setColor(Color.GRAY);
        for (int i = 0; i < dialPositions.size(); i++) {
            G2DHelpers.drawLineAtAngle(g2, centerX, centerY, getAngleVisually(i), dialOutsideSize / 2);
        }
    }

    protected void drawDialFace(Graphics2D g2) {
        //Draw dial face
        G2DHelpers.drawCircle(g2, Color.BLACK, centerX, centerY, (int) (dialOutsideSize * dialFaceScale), true);

        //Draw line on dial face
        g2.setColor(Color.GREEN);
        G2DHelpers.drawLineAtAngle(g2, centerX, centerY, getAngleVisually(dialPosition), dialOutsideSize / 2);
    }

    /**
     * Angle at the index visually
     * <p>
     * Allows an offset to be applied if the
     * desire is to have 0 degrees visually
     * show at a different position then normally
     * <p>
     * By Default 0 degrees points right
     *
     * @param index - index of the angle in {@link #dialPositions}
     * @return angle, 0 to 360 normally
     */
    public int getAngleVisually(int index) {
        return dialPositions.get(index);
    }

    /**
     * Gets the selected angle
     *
     * @return angle, 0 to 360 normally
     */
    public int getSelectedAngle() {
        return dialPositions.get(getSelectedIndex());
    }

    /**
     * Gets the selected index
     *
     * @return selected index
     */
    public int getSelectedIndex() {
        return dialPosition;
    }

    /**
     * Sets the dial position
     * <p>
     * Checks for valid range and will print to console if an error happens.
     * <p>
     * Will also print to console the selected angle change.
     * <p>
     * will cause a redraw of the component
     *
     * @param index - index in {@link #dialPositions}
     */
    public void setDialPosition(int index) {
        if (index >= 0 && index < dialPositions.size()) {
            dialPosition = index;
            System.out.println("Dial#setDialPosition(" + index + ") - dial set to " + getSelectedAngle());
            repaint();
            onSelectChangedList.forEach(listener -> listener.accept(this, index));
        } else {
            System.out.println("Dial#setDialPosition(" + index + ") - Error, Index is outside the range of values");
            new RuntimeException("trace").printStackTrace();
        }
    }

    /**
     * Adds a new position to the list of angles.
     * <p>
     * Will cause a redraw
     *
     * @param angle - new angle
     * @return angle
     */
    public Dial addPosition(int angle) {
        if (!dialPositions.contains(angle)) {
            this.dialPositions.add(angle);
            if (dialPosition == -1) {
                dialPosition = 0;
            }
            repaint();
        }
        return this;
    }

    public Dial clearPositions() {
        dialPositions.clear();
        dialPosition = -1;
        repaint();
        return this;
    }

    public void addSelectChangeListener(BiConsumer<Dial, Integer> consumer) {
        this.onSelectChangedList.add(consumer);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        drawAreaSize = getWidth() > getHeight() ? getHeight() : getWidth();

        centerX = drawAreaSize / 2;
        centerY = drawAreaSize / 2;

        dialOutsideSize = drawAreaSize - pad * 2;
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
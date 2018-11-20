package com.darkguardsman.railnet.ui.components;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

public class Dial extends JPanel implements ComponentListener {

    protected final List<Integer> dialPositions = new ArrayList();

    protected int dialPosition = 0;

    protected int drawAreaSize;
    protected int dialOutsideSize;

    protected int pad = 3;

    protected int centerX;
    protected int centerY;

    protected float dialFaceScale = 0.8f;

    public Dial() {
        this(100);
    }

    public Dial(int size) {
        setMinimumSize(new Dimension(size, size));
        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        //TODO create mouse listener for clicks on dial positions to set position
        addMouseListener(new DialMouseListener(this));
        addComponentListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draw boarder
        g2.drawRect(1, 1, getWidth() - 2, getHeight() - 2);

        //Draw dial background
        drawCircle(g2, Color.BLUE, centerX, centerY, dialOutsideSize);

        //Draw dial positions
        g2.setColor(Color.GRAY);
        for (int i = 0; i < dialPositions.size(); i++) {
            drawLineAtAngle(g2, centerX, centerY, getAngleVisually(i), dialOutsideSize / 2);
        }

        //Draw dial face
        drawCircle(g2, Color.BLACK, centerX, centerY, (int) (dialOutsideSize * dialFaceScale));

        //Draw line on dial face
        g2.setColor(Color.GREEN);
        drawLineAtAngle(g2, centerX, centerY, getAngleVisually(dialPosition), dialOutsideSize / 2);
    }

    public int getAngleVisually(int index) {
        return dialPositions.get(index);
    }

    private void drawLineAtAngle(Graphics2D g2, int centerX, int centerY, int angle, int size) {
        int x = getX(size, angle);
        int y = getY(size, angle);

        g2.drawLine(centerX, centerY, centerX + x, centerY + y);
    }

    private void drawCircle(Graphics2D g2, Color color, int centerX, int centerY, int size) {
        g2.setColor(color);
        drawCircle(g2, centerX, centerY, size);
    }

    private void drawCircle(Graphics2D g2, int centerX, int centerY, int size) {
        int radius = size / 2;
        g2.fillOval(centerX - radius, centerY - radius, size, size);
    }

    private int getX(int radius, int angle) {
        return (int) (radius * Math.cos(Math.toRadians(angle)));
    }

    private int getY(int radius, int angle) {
        return (int) -(radius * Math.sin(Math.toRadians(angle)));
    }

    public int getAngle() {
        return dialPositions.get(dialPosition);
    }

    public void setDialPosition(int index) {
        if (index >= 0 && index < dialPositions.size()) {
            dialPosition = index;
            System.out.println("Dial#setDialPosition(" + index + ") - dial set to " + getAngle());
            repaint();
        } else {
            System.out.println("Dial#setDialPosition(" + index + ") - Error, Index is outside the range of values");
            new RuntimeException("trace").printStackTrace();
        }
    }

    public Dial addPosition(int angle) {
        if (!dialPositions.contains(angle))
            this.dialPositions.add(angle);
        repaint();
        return this;
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
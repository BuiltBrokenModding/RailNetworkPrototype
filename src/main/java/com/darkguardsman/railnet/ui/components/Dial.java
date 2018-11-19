package com.darkguardsman.railnet.ui.components;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Dial extends JPanel {

    protected final List<Integer> dialPositions = new ArrayList();

    protected int dialPosition = 0;

    public Dial() {
        this(100);
    }

    public Dial(int size) {
        setMinimumSize(new Dimension(size, size));
        setPreferredSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        //TODO create mouse listener for clicks on dial positions to set position
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draw boarder
        g2.drawRect(1, 1, getWidth() - 2, getHeight() - 2);

        //Get size for dial
        final int drawAreaSize = getWidth() > getHeight() ? getHeight() : getWidth();
        final int pad = 3;

        int centerX = drawAreaSize / 2;
        int centerY = drawAreaSize / 2;

        //Adjust size for padding
        int size = drawAreaSize - pad * 2;

        //Get radius
        int radius = size / 2;

        //Draw dial background
        g2.setColor(Color.BLUE);
        g2.fillOval(centerX - radius, centerY - radius, size, size);

        //Draw dial positions
        g2.setColor(Color.GRAY);
        for (Integer angle : dialPositions) {
            int x = (int) (radius * Math.cos(Math.toRadians(angle)));
            int y = (int) -(radius * Math.sin(Math.toRadians(angle)));

            g2.drawLine(centerX, centerY, centerX + x, centerY + y);
        }

        //Draw dial
        size = (int) (size * 0.8);
        radius = size / 2;

        //Draw dial background
        g2.setColor(Color.BLACK);
        g2.fillOval(centerX - radius, centerY - radius, size, size);

        //Draw line on dial face
        int angle = dialPositions.get(dialPosition);
        g2.setColor(Color.GREEN);
        int x = getX(radius, angle);
        int y = getY(radius, angle);

        g2.drawLine(centerX, centerY, centerX + x, centerY + y);

    }

    private int getX(int radius, int angle)
    {
        return (int) (radius * Math.cos(Math.toRadians(angle)));
    }

    private int getY(int radius, int angle)
    {
        return (int) -(radius * Math.sin(Math.toRadians(angle)));
    }

    public Dial addPosition(int angle) {
        if (!dialPositions.contains(angle))
            this.dialPositions.add(angle);
        repaint();
        return this;
    }
}
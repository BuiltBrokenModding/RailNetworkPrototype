package com.darkguardsman.railnet.ui.graphics;

import com.darkguardsman.railnet.lib.MathHelpers;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/20/18.
 */
public class G2DHelpers {

    /**
     * Draws a line at an angle from the position
     *
     * @param g2    - graphics object to use for drawling the line
     * @param x     - position to start the line
     * @param y     - position to start the line
     * @param angle - angle in degrees
     * @param size  - length of the line
     */
    public static void drawLineAtAngle(Graphics2D g2, int x, int y, int angle, int size) {
        int vectorX = MathHelpers.getX(size, angle);
        int vectorY = MathHelpers.getY(size, angle);

        g2.drawLine(x, y, vectorX + x, vectorY + y);
    }

    /**
     * Draws a circle around the center point
     *
     * @param g2      - graphics object to use for drawling the line
     * @param color   - color to draw the circle
     * @param centerX - center of the circle
     * @param centerY - center of the circle
     * @param size    - size of the circle, diameter (2 * radius)
     */
    public static void drawCircle(Graphics2D g2, Color color, int centerX, int centerY, int size, boolean filled) {
        g2.setColor(color);
        drawCircle(g2, centerX, centerY, size, filled);
    }

    /**
     * Draws a circle around the center point
     *
     * @param g2      - graphics object to use for drawling the line
     * @param centerX - center of the circle
     * @param centerY - center of the circle
     * @param size    - size of the circle, diameter (2 * radius)
     */
    public static void drawCircle(Graphics2D g2, int centerX, int centerY, int size, boolean filled) {
        final int radius = size / 2;
        if (filled) {
            g2.fillOval(centerX - radius, centerY - radius, size, size);
        } else {
            g2.drawOval(centerX - radius, centerY - radius, size, size);
        }
    }
}

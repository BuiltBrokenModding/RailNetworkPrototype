package com.darkguardsman.railnet.ui.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/20/18.
 */
public class DialMouseListener implements MouseListener {
    public final Dial dial;

    public DialMouseListener(Dial dial) {
        this.dial = dial;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() == dial) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();

            //Check that mouse clicked near or over dial face
            if (x >= dial.pad && y >= dial.pad && x < (dial.drawAreaSize - dial.pad) && y < (dial.drawAreaSize - dial.pad)) {

                //Convert position to angle
                int deltaX = x - dial.centerX;
                int deltaY = y - dial.centerY;
                double rad = Math.atan2(deltaY, deltaX);
                double clickAngle = -rad * (180 / Math.PI);

                //Wrap to 360
                while (clickAngle < 0) {
                    clickAngle += 360;
                }
                while (clickAngle > 360) {
                    clickAngle -= 360;
                }

                //Find best match angle
                float distance = 360;
                int dialIndex = 0;
                for (int i = 0; i < dial.dialPositions.size(); i++) {
                    int selectionAngle = dial.getAngleVisually(i);
                    float diff = getAngleDelta(selectionAngle, (int) clickAngle);
                    if (diff < distance) {
                        distance = diff;
                        dialIndex = i;
                    }
                }

                if (distance <= dial.clickDistance) {
                    //Set position
                    dial.setDialPosition(dialIndex);
                }
            }
        }
    }

    private int getAngleDelta(int a, int b) {
        return Math.min((a - b + 360) % 360, (b - a + 360) % 360);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Not used
    }
}

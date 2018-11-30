package com.darkguardsman.railnet.ui.panels.curve;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.panels.curve.PanelCurveRailAlgMouse;

/**
 * Updates the curve to the current mouse position
 *
 * @see <a href=
 *      "https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a>
 *      for what you can and can't do with the code. Created by Shovinus on
 *      11/16/2018.
 */
public class MouseMotionListenerCurveAlg implements MouseMotionListener {
	protected final PanelCurveRailAlgMouse host;

	public MouseMotionListenerCurveAlg(PanelCurveRailAlgMouse host) {
		this.host = host;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		if (mouseEvent.getSource() == host.renderPanel) {

			// Mouse pos
			final int mouseX = mouseEvent.getX();
			final int mouseY = mouseEvent.getY();

			// Data render bounds
			final double minX = host.renderPanel.getDrawMinX();
			final double minY = host.renderPanel.getDrawMinY();
			final double maxX = host.renderPanel.getDrawMaxX();
			final double maxY = host.renderPanel.getDrawMaxY();

			// Size of bounds
			double bWidth = maxX - minX;
			double bHeight = maxY - minY;

			// Convert mouse to data position
			double gx = minX + (((double) mouseX / host.renderPanel.getWidth()) * bWidth); // TODO document
			double gz = maxY - (((double) mouseY / host.renderPanel.getHeight()) * bHeight);


			// Clear data
			host.renderPanel.clear();

			//Generate rail
			host.generateRail(new Pos(gx, 0, gz));

			// Draw
			host.renderPanel.repaint();
		}
	}

}
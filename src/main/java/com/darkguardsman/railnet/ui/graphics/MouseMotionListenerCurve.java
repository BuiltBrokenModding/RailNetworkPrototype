package com.darkguardsman.railnet.ui.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.rail.RailRenderUtil;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

/**
 * Updates the curve to the current mouse position
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Shovinus on 11/16/2018.
 */
public class MouseMotionListenerCurve implements MouseMotionListener
{
	protected final RenderPanel renderPanel;
	protected final PlotPointRender plotPointRender;

	public MouseMotionListenerCurve(RenderPanel renderPanel, PlotPointRender plotPointRender) {
        this.renderPanel = renderPanel;
        this.plotPointRender = plotPointRender;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		if(mouseEvent.getSource() == renderPanel) {

		    //Mouse pos
		    final int mouseX = mouseEvent.getX();
		    final int mouseY = mouseEvent.getY();

		    //Data render bounds
            final double minX = renderPanel.getDrawMinX();
            final double minY = renderPanel.getDrawMinY();
            final double maxX = renderPanel.getDrawMaxX();
            final double maxY = renderPanel.getDrawMaxY();

            //Size of bounds
            double bWidth = maxX - minX;
            double bHeight = maxY - minY;

            //Convert mouse to data position
            double gx = minX + (((double) mouseX / renderPanel.getWidth()) * bWidth); //TODO document
            double gz = maxY - (((double) mouseY / renderPanel.getHeight()) * bHeight);

            //Clear data
            renderPanel.clear();
            try{
            //Generate new rail
            RailRenderUtil.generateRail(plotPointRender, new Pos(0, 0, 0), new Pos(-gx, 0, gz), 180, 0);
            } catch (Exception ex) {
            	
            }
            //Draw
            renderPanel.repaint();
        }
	}

}
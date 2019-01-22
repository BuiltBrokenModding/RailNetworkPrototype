package com.darkguardsman.railnet.ui.panels.snap;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.data.rail.segments.RailSegment;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.lib.SnappedPos;
import com.darkguardsman.railnet.lib.utils.SegmentHelper;
import com.darkguardsman.railnet.ui.graphics.RenderPanel;
import com.darkguardsman.railnet.ui.graphics.rail.RailRenderUtil;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

/**
 * Updates the curve to the current mouse position
 *
 * @see <a href=
 *      "https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a>
 *      for what you can and can't do with the code. Created by Shovinus on
 *      11/16/2018.
 */
public class MouseMotionListenerSnap implements MouseMotionListener, MouseListener {
	protected final RenderPanel renderPanel;
	protected final PlotPointRender plotPointRender;

	public MouseMotionListenerSnap(RenderPanel renderPanel, PlotPointRender plotPointRender) {
		this.renderPanel = renderPanel;
		this.plotPointRender = plotPointRender;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	private SnappedPos start;
	private RailHeading heading;
	private List<RailSegment> storedSegments = new ArrayList<RailSegment>();

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		if (mouseEvent.getSource() == renderPanel) {

			// Mouse pos
			final int mouseX = mouseEvent.getX();
			final int mouseY = mouseEvent.getY();			

			// Data render bounds
			final double minX = renderPanel.getDrawMinX();
			final double minY = renderPanel.getDrawMinY();
			final double maxX = renderPanel.getDrawMaxX();
			final double maxY = renderPanel.getDrawMaxY();
			
			// Size of bounds
			double bWidth = maxX - minX;
			double bHeight = maxY - minY;
			
			// Convert mouse to data position
			float mx = (float) (minX + (((float) mouseX / renderPanel.getWidth()) * bWidth)); // TODO document
			float mz = (float) (maxY - (((float) mouseY / renderPanel.getHeight()) * bHeight));
			// Clear data
			renderPanel.clear();
			
			try {
			if (start != null) {
				SnappedPos end = new SnappedPos(mx, 0f, mz);			
			System.out.println("--------");
			System.out.println(String.format("Mouse Pos: %s,%s", mx,mz));
			System.out.println(String.format("Start: %d,%d,%d", (int)start.x(),(int)start.y(),(int)start.z()));
			System.out.println(String.format("End: %d,%d,%d", (int)end.x(),(int)end.y(),(int)end.z()));
			System.out.println("-");
			if(heading != null) {
				SegmentHelper.generateRail(start, end, heading, mouseEvent.isShiftDown());
			}
				RailSegment[] segments = SegmentHelper.generateRail(start, end, mouseEvent.isShiftDown());
				
			    RailRenderUtil.generateTrack(segments,plotPointRender);
			} else {
				RailSegment[] segments = SegmentHelper.generateRail(new SnappedPos(mx, 0f, mz).clearOrigin(), new SnappedPos(mx, 0f, mz), mouseEvent.isShiftDown());
				RailRenderUtil.generateTrack(segments,plotPointRender);
			}
			
			} catch (Exception ex) {
			}
			// Draw
			renderPanel.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		if (mouseEvent.getSource() == renderPanel) {

			// Mouse pos
			final int mouseX = mouseEvent.getX();
			final int mouseY = mouseEvent.getY();			

			// Data render bounds
			final double minX = renderPanel.getDrawMinX();
			final double minY = renderPanel.getDrawMinY();
			final double maxX = renderPanel.getDrawMaxX();
			final double maxY = renderPanel.getDrawMaxY();
			
			// Size of bounds
			double bWidth = maxX - minX;
			double bHeight = maxY - minY;
			
			// Convert mouse to data position
			float mx = (float) (minX + (((float) mouseX / renderPanel.getWidth()) * bWidth)); // TODO document
			float mz = (float) (maxY - (((float) mouseY / renderPanel.getHeight()) * bHeight));
			 start = new SnappedPos(mx, 0f, mz);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
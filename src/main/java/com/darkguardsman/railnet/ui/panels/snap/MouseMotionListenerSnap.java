package com.darkguardsman.railnet.ui.panels.snap;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

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
	private RailSegment[] lastSegmentsMade;

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
					System.out.println("--------");
					System.out.println(heading);
					System.out.println("--------");
					System.out.println(String.format("Mouse Pos: %s,%s", mx, mz));
					System.out.println(
							String.format("Start: %d,%d,%d", (int) start.x(), (int) start.y(), (int) start.z()));
					System.out.println(String.format("End: %d,%d,%d", (int) end.x(), (int) end.y(), (int) end.z()));
					System.out.println("-");
					RailRenderUtil.generateTrack(
							(RailSegment[]) storedSegments.toArray(new RailSegment[storedSegments.size()]),
							plotPointRender);
					if (heading != null) {
						lastSegmentsMade = SegmentHelper.generateRail(start, end, heading, mouseEvent.isShiftDown());
						RailRenderUtil.generateTrack(lastSegmentsMade, plotPointRender);
					} else {
						lastSegmentsMade = SegmentHelper.generateRail(start, end, mouseEvent.isShiftDown());
						RailRenderUtil.generateTrack(lastSegmentsMade, plotPointRender);
					}

				} else {
					lastSegmentsMade = SegmentHelper.generateRail(new SnappedPos(mx, 0f, mz).clearOrigin(),
							new SnappedPos(mx, 0f, mz), mouseEvent.isShiftDown());
					RailRenderUtil.generateTrack(lastSegmentsMade, plotPointRender);
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

			// We already have a starting point
			if (lastSegmentsMade != null && lastSegmentsMade.length > 0) {
				storedSegments.addAll(new ArrayList<RailSegment>(Arrays.asList(lastSegmentsMade)));
				start = new SnappedPos(storedSegments.get(storedSegments.size() - 1).end());
				heading = RailHeading.fromAngle(storedSegments.get(storedSegments.size() - 1).endAngle -180);
				
			} else {
				start = new SnappedPos(mx, 0f, mz);
			}

			mouseMoved(mouseEvent);
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
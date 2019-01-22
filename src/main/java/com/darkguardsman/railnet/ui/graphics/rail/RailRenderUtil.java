package com.darkguardsman.railnet.ui.graphics.rail;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
import com.darkguardsman.railnet.api.rail.IRailJoint;
import com.darkguardsman.railnet.api.rail.IRailPath;
import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.data.rail.segments.RailSegment;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentLine;
import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.lib.SnappedPos;
import com.darkguardsman.railnet.lib.utils.SegmentHelper;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper to handle generating data for visual renders of rails
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/16/18.
 */
public class RailRenderUtil {
    public static int NODE_SIZE = 10;
    public static int NODE_EDGE_SIZE = 2;
    public static Color NODE_COLOR = Color.YELLOW;
    public static Color NODE_COLOR_ENDS = Color.BLUE;

   

    public static void generateTrack(RailSegment[] segments,PlotPointRender pointRender) {
    	for(int i = 0; i < segments.length;i++) {
    		RailSegment segment = segments[i];
    		generateSegment(segment, pointRender);
    	}
    }
    public static void generateSegment(RailSegment segment,PlotPointRender pointRender) {
    	generateRails(segment, pointRender);
    	generateSleepers(segment, pointRender);
    	generateVisualPath(segment, pointRender);
    }
    /**
     * Generates the actual metal rails for the segment
     * @param segment
     */
    private static void generateRails(RailSegment segment,PlotPointRender pointRender) {
    	for(IRailJoint joint :  segment.getJoints()) {
    		
    	}
    }
    /**
     * Generates the sleepers for the track
     * @param segment
     */
    private static void generateSleepers(RailSegment segment,PlotPointRender pointRender) {
    	
    }
    /**
     * Generates a view of the actual path the train entity would use
     * @param segment
     */
    @SuppressWarnings("rawtypes")
	private static void generateVisualPath(RailSegment segment,PlotPointRender pointRender) {
    	for(IRailPath path  : segment.getAllPaths()) {
    		IPos start = path.getStart();
    		for(IRailPathPoint pathPoint : path.getPathPoints()) {
    		pointRender.addLine(new PlotPoint(start.x(), start.z(),Color.blue),new PlotPoint(pathPoint.x(), pathPoint.z(),Color.blue), Color.getColor("", 0x006600), 2);
    		start = pathPoint;
    		}
    	}
    }
    
    /**
     * Generates a simple {@link RailSegmentLine} for visual testing of the rail object
     *
     * @param pointRender - render to supply visual data to
     * @param heading     - direction of the rail
     * @param x           - start point, can be negative depending on the test
     * @param z           - start point, can be negative depending on the test
     * @param distance    - distance to render the rail
     * @return completed segment, not normally used by the renders
     */
    public static RailSegmentLine generateLineRail(PlotPointRender pointRender, RailHeading heading,
                                                   double x, double z, double distance) {
        final List<PlotPoint> dots = new ArrayList();

        RailSegmentLine segment = generateLineRail(dots, heading, x, z, distance);

        //Generate visual connection lines
        plotLineDots(dots, pointRender, Color.CYAN, 2); //TODO move color to static variable

        return segment;
    }

    /**
     * Generates a simple {@link RailSegmentLine} for visual testing of the rail object
     *
     * @param dots     - list to output dots to for render
     * @param heading  - direction of the rail
     * @param x        - start point, can be negative depending on the test
     * @param z        - start point, can be negative depending on the test
     * @param distance - distance to render the rail
     * @return completed segment, not normally used by the renders
     */
    public static RailSegmentLine generateLineRail(List<PlotPoint> dots, RailHeading heading,
                                                   double x, double z, double distance) {
        RailSegmentLine segment = new RailSegmentLine(heading, (float) x, 0, (float) z, (int) distance);
        populatePlotPoints(segment, dots);
        return segment;
    }

    /**
     * Generates a simple {@link RailSegmentLine} for visual testing of the rail object
     *
     * @param pointRender - render to supply visual data to
     * @param start       - start of the segment
     * @param end         - end of the segment
     * @param startAngle  - start angle
     * @param endAngle    - end angle
     * @return completed segment, not normally used by the renders
     */
    public static RailSegmentCurve generateCurveRail(PlotPointRender pointRender, Pos start, Pos end, int startAngle, int endAngle) {
        final List<PlotPoint> dots = new ArrayList();

        RailSegmentCurve segment = generateCurveRail(dots, start, end, startAngle, endAngle);

        //Generate visual connection lines
        plotLineDots(dots, pointRender, Color.CYAN, 2); //TODO move color to static variable

        return segment;
    }

    /**
     * Generates a simple {@link RailSegmentLine} for visual testing of the rail object
     *
     * @param dots       - list to output dots to for render
     * @param start      - start of the segment
     * @param end        - end of the segment
     * @param startAngle - start angle
     * @param endAngle   - end angle
     * @return completed segment, not normally used by the renders
     */
    public static RailSegmentCurve generateCurveRail(List<PlotPoint> dots, Pos start, Pos end, int startAngle, int endAngle) {
        RailSegmentCurve segment = new RailSegmentCurve(start, end, startAngle, endAngle);
        populatePlotPoints(segment, dots);
        return segment;
    }

    
    /**
     * Helper method to convert rail segment into visual path data. Will
     * generate a dot per path point. With the start and end dot getting
     * a unique edge color to make it easier to see.
     *
     * @param segment - segment to grab path data from
     * @param dots    - list to add dots towards
     */
    public static void populatePlotPoints(RailSegment segment, List<PlotPoint> dots) {

        List<IRailPathPoint> points = segment.getAllPaths().get(0).getPathPoints();
        for (int i = 0; i < points.size(); i++) {
            IRailPathPoint pp = points.get(i);

            //Create point
            PlotPoint point = new PlotPoint(pp.x(), pp.z(),
                    i == 0 || i == (points.size() - 1) ? NODE_COLOR_ENDS : NODE_COLOR, NODE_SIZE);

            //Add edge to make it easier to see
            point.addEdge(Color.CYAN, NODE_EDGE_SIZE);

            dots.add(point);
        }
    }

    /**
     * Converts points into dots and maps lines between them
     *
     * @param points      - 3D points to render, Y is not used for 2D renders
     * @param pointRender - render to add lines and dots to
     * @param lineColor   - color of lines
     * @param lineSize    - size of lines
     */
    public static void plotLinePoints(Collection<IPos> points, PlotPointRender pointRender, Color lineColor, int lineSize) {
        final List<PlotPoint> dots = points.stream().map(p -> new PlotPoint(p.x(), p.z(), lineColor, lineSize)).collect(Collectors.toList());
        plotLineDots(dots, pointRender, lineColor, lineSize);
    }

    /**
     * Creates lines between the dots
     *
     * @param dots        - list of dots
     * @param pointRender - render to add lines and dots to
     * @param lineColor   - color of lines
     * @param lineSize    - size of lines
     */
    public static void plotLineDots(Collection<PlotPoint> dots, PlotPointRender pointRender, Color lineColor, int lineSize) {
        int i = 0;
        for (PlotPoint dot : dots) {

            if (i != 0) {
                //Adds node and sets a line to last node
                pointRender.addPlusLinkLast(dot, lineColor, lineSize); //TODO consider moving links to data generator
            } else {
                pointRender.add(dot);
            }

            i++;
        }
    }
}

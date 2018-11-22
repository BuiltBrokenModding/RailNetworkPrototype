package com.darkguardsman.railnet.ui.graphics.rail;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.math.IPosM;
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
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/16/18.
 */
public class RailRenderUtil {
    public static int NODE_SIZE = 10;
    public static int NODE_EDGE_SIZE = 2;
    public static Color NODE_COLOR = Color.YELLOW;
    public static Color NODE_COLOR_ENDS = Color.BLUE;

    public static RailSegment[] generateRail(PlotPointRender pointRender, SnappedPos start, SnappedPos end) throws Exception
    {
        //Generate rail and get dots
        List<PlotPoint> dots = new ArrayList();
        List<IPosM> rail1 = new ArrayList<IPosM>();
        List<IPosM> rail2 = new ArrayList<IPosM>();
        RailSegment[] segments = generateRail(dots, start, end);
        //Add dots to render, include lines to trace path easier
        for (int i = 0; i < dots.size(); i++) {

            PlotPoint dot = dots.get(i);

            if(i != 0) {
                //Adds node and sets a line to last node
                pointRender.addPlusLinkLast(dot, Color.CYAN, 2); //TODO consider moving links to data generator
            }
            else
            {
                pointRender.add(dot);
            }
        }
        for (int i = 0; i < rail1.size(); i++) {
        	IPos point = rail1.get(i);
            PlotPoint dot = new PlotPoint(point.x(),point.z(),Color.BLACK,2);

            if(i != 0) {
                //Adds node and sets a line to last node
                pointRender.addPlusLinkLast(dot, Color.BLACK, 2); //TODO consider moving links to data generator
            }
            else
            {
                pointRender.add(dot);
            }
        }
        for (int i = 0; i < rail2.size(); i++) {
        	IPos point = rail2.get(i);
            PlotPoint dot = new PlotPoint(point.x(),point.z(),Color.BLACK,2);

            if(i != 0) {
                //Adds node and sets a line to last node
                pointRender.addPlusLinkLast(dot, Color.BLACK, 2); //TODO consider moving links to data generator
            }
            else
            {
                pointRender.add(dot);
            }
        }
        return segments;
    }

    public static RailSegmentLine generateRail(List<PlotPoint> dots, RailHeading heading,
                                               double x, double z, double distance) {
        RailSegmentLine segment = new RailSegmentLine(heading, (float) x, 0, (float) z, (int) distance);
        populatePlotPoints(segment, dots);
        return segment;
    }

    public static RailSegment[] generateRail(List<PlotPoint> dots, SnappedPos start, SnappedPos end) throws Exception
    {
        RailSegment[]segments = SegmentHelper.generateRail(start, end);
        if (segments != null) {
        	for(int i = 0; i < segments.length;i++) {
        		populatePlotPoints(segments[i], dots);
        	}
        	
        }
        return segments;
    }

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
}

package com.darkguardsman.railnet.ui.graphics.rail;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.math.IPos;
import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.data.rail.segments.RailSegment;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentCurve;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentLine;
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

    public static RailSegmentCurve generateRail(PlotPointRender pointRender, IPos start, IPos end, double startAngle, double endAngle, boolean influencePoints)
    {
        //Generate rail and get dots
        List<PlotPoint> dots = new ArrayList();
        RailSegmentCurve segment = generateRail(dots, start, end, startAngle, endAngle);

        //Add influence points for debug
        if(influencePoints) {
            pointRender.add(new PlotPoint(segment.influencePointA.x(), segment.influencePointA.z(), Color.GREEN, 14));
            pointRender.add(new PlotPoint(segment.influencePointB.x(), segment.influencePointB.y(), Color.GREEN, 14));
        }

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

        return segment;
    }

    public static RailSegmentLine generateRail(List<PlotPoint> dots, RailHeading heading,
                                               double x, double z, double distance) {
        RailSegmentLine segment = new RailSegmentLine(heading, (float) x, 0, (float) z, (int) distance);
        populatePlotPoints(segment, dots);
        return segment;
    }

    public static RailSegmentCurve generateRail(List<PlotPoint> dots, IPos start, IPos end, double startAngle, double endAngle)
    {
        RailSegmentCurve segment = new RailSegmentCurve(start, end, (float)startAngle, (float)endAngle);
        populatePlotPoints(segment, dots);
        return segment;
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
            System.out.println("\t\t[" + i + "]: " + pp.x() + ", " + pp.z());
        }
    }
}

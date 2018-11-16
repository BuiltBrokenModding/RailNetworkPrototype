package com.darkguardsman.railnet.ui.graphics.rail;

import com.darkguardsman.railnet.api.RailHeading;
import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.data.rail.segments.RailSegmentLine;
import com.darkguardsman.railnet.ui.graphics.data.PlotPoint;

import java.awt.Color;
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

    public static RailSegmentLine generateLineRail(List<PlotPoint> dots, RailHeading heading,
                                                      double x, double z, double distance) {
        RailSegmentLine segment = new RailSegmentLine(heading, (float) x, 0, (float) z, (int) distance);
        populatePlotPoints(segment, dots);
        return segment;
    }

    public static void populatePlotPoints(RailSegmentLine segment, List<PlotPoint> dots) {

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

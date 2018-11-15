package com.darkguardsman.railnet.ui.graphics.render;

import com.darkguardsman.railnet.ui.graphics.PlotPoint;
import com.darkguardsman.railnet.ui.graphics.RenderPanel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PlotPointRender implements IPlotRenderObject {

    /**
     * Data to display in the panel
     */
    protected List<PlotPoint> data = new ArrayList();

    protected final Consumer<List<PlotPoint>> dataRefreshFunction;

    public PlotPointRender(Consumer<List<PlotPoint>> dataRefreshFunction) {
        this.dataRefreshFunction = dataRefreshFunction;
    }

    @Override
    public void draw(Graphics2D g2, RenderPanel renderPanel) {
        if (data != null && !data.isEmpty()) {
            //Render data points
            for (PlotPoint pos : data) {
                renderPanel.drawCircle(g2, pos.color, pos.x, pos.y, pos.size, true);
            }
        }
    }

    public void add(PlotPoint plotPoint) {
        data.add(plotPoint);
    }

    @Override
    public double getMaxY() {
        if (data.size() == 0) {
            return 0;
        }
        return data.stream().max(Comparator.comparingDouble(PlotPoint::y)).get().y;
    }

    @Override
    public double getMaxX() {
        if (data.size() == 0) {
            return 0;
        }
        return data.stream().max(Comparator.comparingDouble(PlotPoint::x)).get().x;
    }

    @Override
    public double getMinY() {
        if (data.size() == 0) {
            return 0;
        }
        return data.stream().min(Comparator.comparingDouble(PlotPoint::y)).get().y;
    }

    @Override
    public double getMinX() {
        if (data.size() == 0) {
            return 0;
        }
        return data.stream().min(Comparator.comparingDouble(PlotPoint::x)).get().x;
    }

    @Override
    public void clearData() {
        data.clear();
    }

    @Override
    public void refreshData() {
        clearData();
        if (dataRefreshFunction != null) {
            dataRefreshFunction.accept(data);
        }
    }
}

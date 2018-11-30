package com.darkguardsman.railnet.ui.components;

import com.darkguardsman.railnet.api.rail.IRailPath;
import com.darkguardsman.railnet.api.rail.IRailPathPoint;
import com.darkguardsman.railnet.api.rail.IRailSegment;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/30/18.
 */
public class RailTableModel extends AbstractTableModel {

    protected final RailDataTable table;
    protected final ArrayList rowToData = new ArrayList();
    protected final Set<Integer> isHeaderRow = new HashSet();
    protected final Set<Integer> isSpacerRow = new HashSet();


    public RailTableModel(RailDataTable table) {
        this.table = table;
    }

    protected void clear() {
        //Clear old data
        rowToData.clear();
        isHeaderRow.clear();
        isSpacerRow.clear();

        fireTableStructureChanged();
    }

    public void initData() {

        clear();

        //Build new data
        for (int i = 0; i < table.rails.size(); i++) {
            //Loop segments adding sections
            final IRailSegment segment = table.rails.get(i);
            if (segment != null) {
                //Each segment section starts with a header
                headerRow(segment.getRailID(), i);

                //Loop all paths
                for (int j = 0; j < segment.getAllPaths().size(); j++) {
                    //Each Path section starts with a header if there are more than 1
                    if (segment.getAllPaths().size() > 1) {
                        headerRow("Path: " + j, i);
                    }

                    //Add path points
                    for (IRailPathPoint point : segment.getAllPaths().get(j).getPathPoints()) {
                        rowToData.add(point);
                    }

                    //Add spacer
                    if (j != segment.getAllPaths().size() - 1) {
                        setIsSpacerRow(i);
                    }
                }

                //Add spacer
                if (i != table.rails.size() - 1) {
                    setIsSpacerRow(i);
                }
            }
        }

        fireTableStructureChanged();
    }

    protected void headerRow(Object data, int i) {
        rowToData.add(data);
        isHeaderRow.add(i);
    }

    protected void setIsSpacerRow(int i) {
        rowToData.add(null);
        isSpacerRow.add(i);
    }

    @Override
    public int getRowCount() {
        return rowToData.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Rail";
            case 1:
                return "Node";
            case 2:
                return "x";
            case 3:
                return "y";
            case 4:
                return "z";
            case 5:
                return "delta";
            default:
                return "??";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Object dataAtRow = rowToData.get(rowIndex);
        if (isSpacerRow.contains(rowIndex)) {
            return "-";
        } else if (isHeaderRow.contains(rowIndex)) {
            if (columnIndex == 0) {
                if (dataAtRow instanceof String) {
                    return dataAtRow;
                }
                return dataAtRow.toString();
            }
            return "";
        } else if (dataAtRow instanceof IRailPathPoint) {
            switch (columnIndex) {
                case 0:
                    return "";
                case 1:
                    return "" + ((IRailPathPoint) dataAtRow).getIndex();
                case 2:
                    return String.format("%.4f", ((IRailPathPoint) dataAtRow).x());
                case 3:
                    return String.format("%.4f", ((IRailPathPoint) dataAtRow).y());
                case 4:
                    return String.format("%.4f", ((IRailPathPoint) dataAtRow).z());
                case 5: {
                    double distance = 0;
                    if (rowIndex > 0) {
                        Object rowLess = rowToData.get(rowIndex - 1);
                        if (rowLess instanceof IRailPathPoint) {
                            double deltaX = ((IRailPathPoint) dataAtRow).x() - ((IRailPathPoint) rowLess).x();
                            double deltaY = ((IRailPathPoint) dataAtRow).y() - ((IRailPathPoint) rowLess).y();
                            double deltaZ = ((IRailPathPoint) dataAtRow).z() - ((IRailPathPoint) rowLess).z();

                            distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
                        }

                    }
                    return String.format("%.4f", distance);
                }
            }
        }
        return "?";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }
}

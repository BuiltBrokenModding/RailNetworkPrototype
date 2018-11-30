package com.darkguardsman.railnet.ui.components;

import com.darkguardsman.railnet.api.rail.IRailSegment;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/30/18.
 */
public class RailDataTable extends JTable {

    protected final List<IRailSegment> rails = new ArrayList();

    public RailDataTable() {
        setModel(new RailTableModel(this));

        initializeLocalVars();
        updateUI();
    }

    public void clearRails() {
        rails.clear();
        if (dataModel instanceof RailTableModel) {
            ((RailTableModel) dataModel).clear();
        }
    }

    public void addRail(IRailSegment segment) {
        if (segment != null && !rails.contains(segment)) {
            rails.add(segment);

            if (dataModel instanceof RailTableModel) {
                ((RailTableModel) dataModel).initData();
            }
        }
    }
}

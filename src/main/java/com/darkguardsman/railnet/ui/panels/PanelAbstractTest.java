package com.darkguardsman.railnet.ui.panels;

import com.darkguardsman.railnet.ui.graphics.RenderPanel;
import com.darkguardsman.railnet.ui.graphics.render.PlotCenterRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotGridRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/19/18.
 */
public abstract class PanelAbstractTest extends JPanel {

    protected RenderPanel renderPanel;
    protected PlotPointRender pointRender;
    protected TextArea purposeTextArea;

    protected final String testPurpose;

    public PanelAbstractTest(String testPurpose) {
        this.testPurpose = testPurpose;

        setLayout(new BorderLayout());
        if (testPurpose != null)
            add(createFooterPanel(), BorderLayout.SOUTH);
        add(createRenderPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.WEST);
    }

    protected JTabbedPane createFooterPanel() {
        JTabbedPane tabbedPane = new JTabbedPane();

        //Purpose
        JPanel panel = new JPanel();
        purposeTextArea = new TextArea();
        purposeTextArea.setText(testPurpose);
        purposeTextArea.setEditable(false);
        panel.add(purposeTextArea);

        JPanel consolePanel = new JPanel();
        consolePanel.add(new JLabel("TODO"));


        tabbedPane.addTab("Purpose", null, panel);
        tabbedPane.addTab("Console", null, consolePanel);


        return tabbedPane;
    }

    /**
     * Called on component creation to generate
     * the render panel.
     * <p>
     * Make sure to assign {@link #renderPanel} inside
     * the method if you do not call super in your
     * own implementation.
     *
     * @return panel that is or will contain the render panel
     */
    protected JPanel createRenderPanel() {
        //Init
        renderPanel = new RenderPanel();
        renderPanel.setViewBoundSize(10);

        //Renders
        addRenderPanelRenders(renderPanel);

        //Listeners
        addRenderPanelListeners(renderPanel);

        return renderPanel;
    }

    protected void addRenderPanelRenders(RenderPanel panel) {
        renderPanel.addRendersToRun(new PlotGridRender(1, 1));
        renderPanel.addRendersToRun(new PlotGridRender(2, 2, Color.BLUE));
        renderPanel.addRendersToRun(new PlotCenterRender());
        renderPanel.addRendersToRun(pointRender = new PlotPointRender(null));
    }

    protected void addRenderPanelListeners(RenderPanel panel) {

    }

    /**
     * Called to create the control panel.
     * <p>
     * The control panel is used to trigger actions
     * to run tests, generate data, or manuplate the view.
     *
     * @return control panel
     */
    protected abstract JPanel createControlPanel();

    /**
     * Adds spacer to the panel. This is mainly
     * used for {@link java.awt.GridLayout} were
     * it may be needed to add a empty row.
     *
     * @param panel
     */
    protected void addSpacer(JPanel panel) {
        panel.add(new JPanel());
        panel.add(new JPanel());
    }
}

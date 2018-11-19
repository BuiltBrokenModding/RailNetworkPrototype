package com.darkguardsman.railnet.ui.panels;

import com.darkguardsman.railnet.ui.graphics.RenderPanel;
import com.darkguardsman.railnet.ui.graphics.render.PlotCenterRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotGridRender;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

/**
 * Used to test logic for line rails visually
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/15/18.
 */
public class PanelGridRenderTest extends PanelAbstractTest {

    protected JTextField sizeField;
    protected JTextField padField;

    protected JTextField xStartField;
    protected JTextField yStartField;
    protected JTextField xEndField;
    protected JTextField yEndField;

    @Override
    protected JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(20, 2));
        JButton button;


        panel.add(new JLabel("Pad:"));
        panel.add(padField = new JTextField("1"));
        
        panel.add(new JPanel());
        button = new JButton("Set");
        button.addActionListener((a) -> setPad());
        panel.add(button);

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());


        panel.add(new JLabel("Size:"));
        panel.add(sizeField = new JTextField("10"));

        panel.add(new JPanel());
        button = new JButton("Set");
        button.addActionListener((a) -> setSize());
        panel.add(button);

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //Spacer
        panel.add(new JLabel("Grid Size:"));
        panel.add(new JPanel());

        panel.add(new JLabel("Start(x,y):"));
        panel.add(new JPanel());
        panel.add(xStartField = new JTextField("-10"));
        panel.add(yStartField = new JTextField("-10"));


        panel.add(new JLabel("End(x, y):"));
        panel.add(new JPanel());
        panel.add(xEndField = new JTextField("10"));
        panel.add(yEndField = new JTextField("10"));

        panel.add(new JPanel());
        button = new JButton("Set");
        button.addActionListener((a) -> applySize());
        panel.add(button);

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        //Spacer
        panel.add(new JPanel());
        panel.add(new JPanel());

        panel.add(new JPanel());
        button = new JButton("Clear");
        button.addActionListener((a) -> renderPanel.clear());
        panel.add(button);


        return panel;
    }

    protected void setPad()
    {
        int pad = (int)Double.parseDouble(padField.getText().trim());
        renderPanel.data_pad = pad;

        repaint();
    }

    protected void setSize()
    {
        int size = (int)Double.parseDouble(sizeField.getText().trim());
        xStartField.setText("-" + size);
        yStartField.setText("-" + size);
        xEndField.setText("" + size);
        yEndField.setText("" + size);

        renderPanel.setViewBoundSize(size);

        generateDataPoints();

        repaint();
    }

    protected void applySize()
    {
        int startX  = (int)Double.parseDouble(xStartField.getText().trim());
        int startY  = (int)Double.parseDouble(yStartField.getText().trim());
        int endX  = (int)Double.parseDouble(xEndField.getText().trim());
        int endY  = (int)Double.parseDouble(yEndField.getText().trim());

        renderPanel.setViewBoundSize(startX, startY, endX, endY);

        generateDataPoints();

        repaint();
    }

    protected void generateDataPoints()
    {
        pointRender.clearData();

        for(int x = renderPanel.lowerBound.width; x <= renderPanel.upperBound.width; x++)
        {
            for(int y = renderPanel.lowerBound.height; y <= renderPanel.upperBound.height; y++)
            {
                if(x == renderPanel.lowerBound.width && y == renderPanel.lowerBound.height
                || x == renderPanel.lowerBound.width && y == renderPanel.upperBound.height
                || x == renderPanel.upperBound.width && y == renderPanel.lowerBound.height
                || x == renderPanel.upperBound.width && y == renderPanel.upperBound.height)
                {
                    pointRender.add(x, y, Color.cyan, 10).addEdge(Color.BLUE, 2);
                }
                else {
                    pointRender.add(x, y, Color.yellow, 5).addEdge(Color.blue, 2);
                }
            }
        }
    }
}

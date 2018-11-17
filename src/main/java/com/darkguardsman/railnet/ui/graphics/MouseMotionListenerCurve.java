package com.darkguardsman.railnet.ui.graphics;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.darkguardsman.railnet.lib.Pos;
import com.darkguardsman.railnet.ui.graphics.rail.RailRenderUtil;
import com.darkguardsman.railnet.ui.graphics.render.PlotPointRender;

/**
 * Forces a component to keep a size that is 1:1
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/12/2018.
 */
public class MouseMotionListenerCurve implements MouseMotionListener
{

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		RenderPanel panel = (RenderPanel)arg0.getSource();
		
		double lWidth = panel.lowerBound.getWidth();
		double uHeight = panel.upperBound.getHeight();

		double bWidth = panel.upperBound.getWidth() - lWidth;
		double bHeight = uHeight - panel.lowerBound.getHeight();

		double gx = lWidth + (((double)arg0.getX()/panel.getWidth())*bWidth);
		double gz = uHeight - (((double)arg0.getY()/panel.getHeight())*bHeight);	

	
		panel.clear();
		JPanel controls = (JPanel)panel.getParent().getComponent(1);
		JTextField angle = (JTextField) controls.getComponent(19);
		Double cleanAngle = Double.parseDouble(angle.getText().trim());
		RailRenderUtil.generateRail((PlotPointRender)panel.rendersToRun.get(3), new Pos(0,0,0), new Pos(-gx,0,gz), 0, Math.toRadians(cleanAngle), true);
		panel.repaint();
	}

}
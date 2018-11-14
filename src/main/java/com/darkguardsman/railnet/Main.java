package com.darkguardsman.railnet;

import com.darkguardsman.railnet.ui.*;

import javax.swing.*;
import java.awt.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/14/18.
 */
public class Main
{
    public static void main(String... args)
    {
        FrameMain frameMain = new FrameMain();
        frameMain.init();
        frameMain.setSize(800, 800);
        frameMain.setMinimumSize(new Dimension(800, 800));
        frameMain.setLocation(200, 200);
        frameMain.setTitle("Rail Network - Prototype");
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMain.setVisible(true);
    }
}

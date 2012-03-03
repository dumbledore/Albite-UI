/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.screen;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.misc.BackgroundImage;
import org.albite.ui.controls.LayoutControl;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public abstract class Screen extends LayoutControl {

    public Screen(final String title, final Context context) {
        super(null, context);
        initializeBackground();
        //TODO: Add title control
    }

    private static BackgroundImage background;

    private void initializeBackground() {
        if (background == null) {
            background = new BackgroundImage("/res/bg.png");
            background.setWidth(context.getWidth());
            background.setHeight(context.getHeight());
        }
    }

    public int getWidth() {
        return context.getWidth();
    }

    public int getHeight() {
        return context.getHeight();
    }

    public void draw(final Graphics g, final int x, final int y) {
        background.draw(g, x, y);
        super.draw(g, x, y);
    }
}

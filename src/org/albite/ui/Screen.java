/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.ImageControl;
import org.albite.ui.controls.layout.OneChildLayout;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class Screen extends OneChildLayout {

    private final ImageControl background = new ImageControl(this, context);

    public Screen(final String title, final Context context) {
        super(null, context);

        background.setImage(context.getTheme().imageBackground);
        background.setRepeat(true);
        background.setWidth(context.getWidth());
        background.setHeight(context.getHeight());

        //TODO: Add title control
    }

    public int getWidth() {
        return context.getWidth();
    }

    public int getHeight() {
        return context.getHeight();
    }

    public void dump(final int level) {
        super.dump(level);
        background.dump(level + 1);
    }

    public void draw(final Graphics g, final int x, final int y) {
        background.drawRelative(g, x, y);
        control.drawRelative(g, x, y);
    }
}

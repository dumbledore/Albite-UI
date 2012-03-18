/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.ImageControl;
import org.albite.ui.controls.layout.AdapterControl;
import org.albite.ui.controls.layout.LayoutControl;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class Screen extends LayoutControl {

    private final ImageControl background = new ImageControl(this, context);
    private final AdapterControl adapter = new AdapterControl(this, context);

    public Screen(final String title, final Context context) {
        super(null, context);

        background.setImage(context.getTheme().imageBackground);
        background.setRepeat(true);
        background.setWidth(context.getWidth());
        background.setHeight(context.getHeight());

        //TODO: Add title control

        //Add a place for the control
        controls.addElement(adapter);
        adapter.setWidth(getWidth());
        adapter.setHeight(getHeight());
    }

    public int getWidth() {
        return context.getWidth();
    }

    public int getHeight() {
        return context.getHeight();
    }

    public void setControl(final Control control) {
        adapter.setControl(control);
        control.setParent(adapter);
    }

    public void dump(final int level) {
        super.dump(level);
        background.dump(level + 1);
    }

    public void draw(final Graphics g, final int x, final int y) {
        background.drawRelative(g, x, y);
        super.draw(g, x, y);
    }
}

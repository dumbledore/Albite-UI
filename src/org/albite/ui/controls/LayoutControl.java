/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import java.util.Enumeration;
import javax.microedition.lcdui.Graphics;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public abstract class LayoutControl extends Control {

    private Control selected = null;

    public LayoutControl(final LayoutControl control, final Context context) {
        super(null, context);
    }

    public abstract void addControl(Control control);
    protected abstract Enumeration getControls();

    public void draw(final Graphics g, final int x, final int y) {
        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            Control contol = (Control) e.nextElement();
            contol.drawRelative(g, x, y);
        }
    }

    public void pressed(int x, int y) {
        if (enabled) {
            selected = find(x, y);
            if (selected != null) {
                selected.pressed(x - this.x, y - this.y);
            }
        }
    }

    public void dragged(int x, int y) {
        if (enabled && selected != null) {
            selected.dragged(x - this.x, y - this.y);
        }
    }

    public void released(int x, int y) {
        if (enabled && selected != null) {
            selected.released(x - this.x, y - this.y);
            selected = null;
        }
    }

    protected final Control find(int x, int y) {
        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            Control control = (Control) e.nextElement();
            if (control.contains(x, y)) {
                return control;
            }
        }

        return null;
    }

    public final Control getSelected() {
        return selected;
    }

    public void dump(final int level) {
        super.dump(level);

        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            Control control = (Control) e.nextElement();
            control.dump(level + 1);
        }
    }
}

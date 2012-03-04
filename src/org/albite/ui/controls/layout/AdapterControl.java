/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.Control;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class AdapterControl extends Control {

    protected Control control;
    boolean isSelected = false;

    public AdapterControl(final Control parent, final Context context) {
        super(parent, context);
    }

    public final void setControl(final Control control) {
        this.control = control;
    }

    public final Control getControl() {
        return control;
    }

    public void invalidateDown() {
        control.invalidateDown();
    }

    public void recompileMetrics(boolean downTree) {
        control.recompileMetrics(downTree);
    }

    public void draw(final Graphics g, final int x, final int y) {
        control.drawRelative(g, x, y);
    }

    public void pressed(int x, int y) {
        if (enabled) {
            isSelected = contains(x, y);
            if (isSelected) {
                control.pressed(x - this.getX(), y - this.getY());
            }
        }
    }

    public void dragged(int x, int y) {
        if (enabled && isSelected) {
            control.dragged(x - this.getX(), y - this.getY());
        }
    }

    public void released(int x, int y) {
        if (enabled && isSelected) {
            control.released(x - this.getX(), y - this.getY());
            isSelected = false;
        }
    }

    public void dump(final int level) {
        super.dump(level);
        control.dump(level + 1);
    }
}
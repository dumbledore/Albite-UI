/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import java.util.Enumeration;
import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.Control;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public abstract class LayoutControl extends Control {

    private Control selected = null;

    public LayoutControl(final Control parent, final Context context) {
        super(parent, context);
    }

    public void addControl(Control control) {
        if (control == this) {
            throw new IllegalArgumentException(
                    "Must never add a component as it's child");
        }
    }

    public abstract void removeControl(Control control);

    protected abstract Enumeration getControls();

    /**
     * NOTE:
     * invalidateDown actually makes it go through the children twice!
     * First any parent-dependent metrics are calculated,
     * then all children are invalidated and
     * then layout's own metrics are recalculated, which depend on the
     * children's ones.
     *
     * The whole point is that invalidate(), which goes up the tree,
     * would NEVER invalidate other children.
     */
    public final void invalidateDown() {
        recompileMetricsFromParent(true);

        Enumeration elements = getControls();
        while (elements.hasMoreElements()) {
            Control control = (Control) elements.nextElement();
            control.invalidateDown();
        }

        recompileMetricsFromChildren(true);
    }

    public final void recompileMetrics(final boolean downTree) {
        recompileMetricsFromParent(downTree);
        recompileMetricsFromChildren(downTree);
    }

    public void recompileMetricsFromParent(boolean downTree) {}
    public void recompileMetricsFromChildren(boolean downTree) {}

    public void draw(final Graphics g, final int x, final int y) {
        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            Control control = (Control) e.nextElement();
            control.drawRelative(g, x, y);
        }
    }

    public void lostFocus() {
        if (enabled && selected != null) {
            selected.lostFocus();
            selected = null;
        }
    }

    public void pressed(int x, int y) {
        if (enabled) {
            selected = find(x, y);
            if (selected != null) {
                selected.pressed(x - this.getX(), y - this.getY());
            }
        }
    }

    public void dragged(int x, int y) {
        if (enabled && selected != null) {
            selected.dragged(x - this.getX(), y - this.getY());
        }
    }

    public void released(int x, int y) {
        if (enabled && selected != null) {
            selected.released(x - this.getX(), y - this.getY());
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

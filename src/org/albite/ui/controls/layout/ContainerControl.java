/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.Control;

/**
 *
 * @author albus
 */
public abstract class ContainerControl extends Control {

    private Control selected = null;
    protected final Vector controls = new Vector();

    protected void addControl(Control control) {
        if (control == this) {
            throw new IllegalArgumentException(
                    "Must never add a component as it's child");
        }
        control.initialize(this, context);
        controls.addElement(control);
    }

    protected void removeControl(Control control) {
        controls.removeElement(control);
    }

    protected Control getSelected() {
        return selected;
    }

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

        final int size = controls.size();
        for (int i = 0; i < size; i++) {
            Control control = (Control) controls.elementAt(i);
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

    public void draw(final Graphics g,
            final int x, final int y, final int zOrder) {

        final int size = controls.size();

        for (int i = 0; i < size; i++) {
            Control control = (Control) controls.elementAt(i);
            control.drawRelative(g, x, y, zOrder);
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
            int x_ = x - this.getX();
            int y_ = y - this.getY();

            selected = find(x_, y_);
            if (selected != null) {
                selected.pressed(x_, y_);
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
        final int size = controls.size();
        for (int i = 0; i < size; i++) {
            Control control = (Control) controls.elementAt(i);
            if (control.contains(x, y)) {
                return control;
            }
        }
        return null;
    }

    public void setDebugMode(int mode) {
        super.setDebugMode(mode);

        final int size = controls.size();
        for (int i = 0; i < size; i++) {
            Control control = (Control) controls.elementAt(i);
            control.setDebugMode(mode);
        }
    }

    public void dump(final int level) {
        super.dump(level);

        final int size = controls.size();
        for (int i = 0; i < size; i++) {
            Control control = (Control) controls.elementAt(i);
            control.dump(level + 1);
        }
    }
}

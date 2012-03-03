/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import java.util.Enumeration;
import javax.microedition.lcdui.Graphics;
import org.albite.ui.core.Context;

/**
 *
 * @author albus
 */
public abstract class LayoutControl extends AlbiteControl {

    private AlbiteControl selected = null;

    protected int paddingX = 0;
    protected int paddingY = 0;

    public LayoutControl(final LayoutControl parent, final Context context) {
        super(parent, context);
    }

    public abstract boolean isWidthFixed();
    public abstract boolean isHeightFixed();

    public abstract void addControl(AlbiteControl control);
    protected abstract Enumeration getControls();

    public int getPaddingX() {
        return paddingX;
    }

    public void setPaddingX(final int paddingX) {
        this.paddingX = paddingX;
    }

    public int getPaddingY() {
        return paddingY;
    }

    public void setPaddingY(final int paddingY) {
        this.paddingY = paddingY;
    }

    public void draw(final Graphics g, final int x, final int y) {
        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            AlbiteControl contol = (AlbiteControl) e.nextElement();
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

    protected final AlbiteControl find(int x, int y) {
        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            AlbiteControl control = (AlbiteControl) e.nextElement();
            if (control.contains(x, y)) {
                return control;
            }
        }

        return null;
    }

    public final AlbiteControl getSelected() {
        return selected;
    }

    public void dump(final int level) {
        super.dump(level);

        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            AlbiteControl control = (AlbiteControl) e.nextElement();
            control.dump(level + 1);
        }
    }
}

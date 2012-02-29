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

    public LayoutControl(final LayoutControl parent, final Context context) {
        super(parent, context);
    }

    public abstract void addControl(AlbiteControl control);
    protected abstract Enumeration getControls();

    public void invalidate() {
        height = 0;

        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            AlbiteControl control = (AlbiteControl) e.nextElement();
            control.invalidate();
            height += control.getHeight();
        }
    }

    public void draw(Graphics g) {
        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            AlbiteControl contol = (AlbiteControl) e.nextElement();
            contol.draw(g);
        }
    }

    public void pressed(int x, int y) {
        if (enabled) {
            selected = find(x, y);
            if (selected != null) {
                selected.pressed(x - selected.getX(), y - selected.getY());
            }
        }
    }

    public void dragged(int x, int y) {
        if (enabled && selected != null) {
            selected.dragged(x - selected.getX(), y - selected.getY());
        }
    }

    public void released(int x, int y) {
        if (enabled && selected != null) {
            selected.released(x - selected.getX(), y - selected.getY());
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
}

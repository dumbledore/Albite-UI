/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import java.util.Enumeration;
import java.util.Vector;
import org.albite.ui.core.Context;

/**
 *
 * @author albus
 */
public class HorizontalLayout extends LayoutControl {

    private final Vector controls = new Vector();

    public HorizontalLayout(final LayoutControl parent, final Context context) {
        super(parent, context);
        height = parent.getWidth();
    }

    public void addControl(AlbiteControl control) {
        control.setY(0);
        control.setX(width + paddingX);
        control.invalidate();
        width += control.getWidth();
        controls.addElement(control);
    }

    public void invalidate() {
        width = 0;

        Enumeration e = getControls();
        while (e.hasMoreElements()) {
            AlbiteControl control = (AlbiteControl) e.nextElement();
            control.invalidate();
            width += control.getWidth();
        }
    }

    protected Enumeration getControls() {
        return controls.elements();
    }

    public boolean isWidthFixed() {
        return false;
    }

    public boolean isHeightFixed() {
        return true;
    }
}

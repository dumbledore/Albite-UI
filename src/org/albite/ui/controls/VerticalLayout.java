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
public class VerticalLayout extends LayoutControl {

    private final Vector controls = new Vector();

    public VerticalLayout(final LayoutControl parent, final Context context) {
        super(parent, context);
        width = parent.getWidth();
    }

    public void addControl(AlbiteControl control) {
        control.invalidate();
        control.setX(0);
        control.setY(height);
        height += control.getHeight();
        controls.addElement(control);
    }

    protected Enumeration getControls() {
        return controls.elements();
    }
}

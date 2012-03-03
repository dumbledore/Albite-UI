/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.screen.list;

import java.util.Enumeration;
import java.util.Vector;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.screen.Screen;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class List extends Screen {

    private final Vector controls = new Vector();

    public List(final String title, final Context context) {
        super(title, context);
        width = super.getWidth();
    }

    public void addControl(Control control) {
        control.setX(0);
        control.setY(height);
        height += control.getHeight();
        controls.addElement(control);
    }

    protected Enumeration getControls() {
        return controls.elements();
    }

    public boolean isWidthFixed() {
        return true;
    }

    public boolean isHeightFixed() {
        return false;
    }
}

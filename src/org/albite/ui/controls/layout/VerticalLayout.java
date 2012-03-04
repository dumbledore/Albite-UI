/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import java.util.Enumeration;
import java.util.Vector;
import org.albite.ui.controls.Control;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class VerticalLayout extends LayoutControl {

    private final Vector controls = new Vector();

    public VerticalLayout(final Control parent, final Context context) {
        super(parent, context);
    }

    public void addControl(Control control) {
        super.addControl(control);
        controls.addElement(control);
    }

    public void recompileMetricsFromParent() {
        width = parent.getWidth();
    }

    public void recompileMetricsFromChildren() {
        height = 0;

        Enumeration enumeration = getControls();
        while (enumeration.hasMoreElements()) {
            Control control = (Control) enumeration.nextElement();
            control.setX(0);
            control.setY(height);
            height += control.getHeight();
        }
    }

    protected Enumeration getControls() {
        return controls.elements();
    }
}

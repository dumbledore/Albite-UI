/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import org.albite.ui.controls.Control;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class LayoutControl extends ContainerControl {
    public LayoutControl(final Control parent, final Context context) {
        super(parent, context);
    }

    public void addControl(Control control) {
        if (control == this) {
            throw new IllegalArgumentException(
                    "Must never add a component as it's child");
        }
        controls.addElement(control);
    }

    public void removeControl(Control control) {
        controls.removeElement(control);
    }
}

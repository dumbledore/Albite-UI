/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import org.albite.ui.controls.Control;

/**
 * This class exports the add/remove functionality in the
 * public interface and access to the selected element.
 *
 * @author albus
 */
public class LayoutControl extends ContainerControl {
    public void addControl(Control control) {
        super.addControl(control);
    }

    public void removeControl(Control control) {
        super.removeControl(control);
    }

    public final Control getSelected() {
        return super.getSelected();
    }
}

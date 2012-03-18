/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import org.albite.ui.controls.Control;

/**
 * This class exports the add/remove functionality in the
 * public interface.
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
}

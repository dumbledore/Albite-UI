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
public class VerticalLayout extends LayoutControl {

    public VerticalLayout(final Control parent, final Context context) {
        super(parent, context);
    }

    public void recompileMetricsFromParent(final boolean downTree) {
        setWidth(parent.getWidth());
    }

    public void recompileMetricsFromChildren(final boolean downTree) {
        int height = 0;

        final int size = controls.size();
        for (int i = 0; i < size; i++) {
            Control control = (Control) controls.elementAt(i);
            control.setX(0);
            control.setY(height);
            height += control.getHeight();
        }

        setHeight(height);
    }
}

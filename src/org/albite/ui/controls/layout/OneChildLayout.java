/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import java.util.Enumeration;
import org.albite.ui.controls.Control;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class OneChildLayout extends LayoutControl {

    protected Control control;

    public OneChildLayout(final Control parent, final Context context) {
        super(parent, context);
    }

    public final void addControl(Control control) {
        super.addControl(control);
        this.control = control;
    }

    protected final Enumeration getControls() {
        return new Enumeration() {

            private boolean hasMore = true;

            public boolean hasMoreElements() {
                return hasMore;
            }

            public Object nextElement() {
                hasMore = false;
                return control;
            }
        };
    }

    public final Control getControl() {
        return control;
    }
}

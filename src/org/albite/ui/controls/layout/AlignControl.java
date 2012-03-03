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
public class AlignControl extends LayoutControl {

    private Control control;

    public AlignControl(final Control parent, final Context context) {
        super(parent, context);
    }

    public void addControl(Control control) {
        if (width > 0) {
            if (control.getWidth() > width) {
                /*
                 * Wrap content
                 */
                width = control.getWidth();
            } else {
                /*
                 * Fixed width
                 */
                control.setX((width - control.getWidth()) / 2);
            }
        } else {
            /*
             * Match parent
             */
            control.setX((parent.getWidth() - control.getWidth()) / 2);
        }

        if (height > 0) {
            if (control.getHeight() > height) {
                /*
                 * Wrap content
                 */
                height = control.getHeight();
            } else {
                /*
                 * Fixed width
                 */
                control.setY((height - control.getHeight()) / 2);
            }
        } else {
            /*
             * Match parent
             */
            control.setY((parent.getHeight() - control.getHeight()) / 2);
        }

        this.control = control;
    }

    public int getWidth() {
        return width > 0 ? width : parent.getWidth();
    }

    public int getHeight() {
        return height > 0 ? height : parent.getHeight();
    }

    protected Enumeration getControls() {
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
}

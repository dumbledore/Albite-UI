/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.screen.list;

import org.albite.ui.controls.LayoutControl;
import org.albite.ui.controls.VerticalControl;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public abstract class ListControl extends VerticalControl {

    protected ListControl(final LayoutControl parent, final Context context) {
        super(parent, context);
    }
}

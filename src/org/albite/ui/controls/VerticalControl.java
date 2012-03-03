/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public abstract class VerticalControl extends Control {

    protected VerticalControl(final LayoutControl parent, final Context context) {
        super(parent, context);
        width = parent.getWidth();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import org.albite.ui.Theme.Themable;

/**
 *
 * @author albus
 */
public interface Context
        extends DrawingEnvironment, ResourceManager, ActivityManager, Themable {

    public int getWidth();
    public int getHeight();
}

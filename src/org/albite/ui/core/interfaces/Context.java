/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.core.interfaces;

/**
 *
 * @author albus
 */
public interface Context
        extends DrawingEnvironment, ResourceManager {

    public int getWidth();
    public int getHeight();
}
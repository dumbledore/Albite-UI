/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

/**
 *
 * @author Albus Dumbledore
 */
public interface Touchable {
    public void pressed(int x, int y);
    public void dragged(int x, int y);
    public void released(int x, int y);
}

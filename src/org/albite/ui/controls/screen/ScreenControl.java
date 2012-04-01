/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.screen;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;

/**
 *
 * @author Albus Dumbledore
 */
public abstract class ScreenControl extends Control {
    protected final void drawBorder(Graphics g, int x, int y) {
        final Theme theme = context.getTheme();

        g.setColor(theme.colorButtonBorder);
        g.fillRect(
                x, y + getHeight() - theme.listElementBorder,
                getWidth(), theme.listElementBorder);
    }
}

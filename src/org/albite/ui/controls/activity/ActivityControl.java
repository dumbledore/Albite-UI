/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.activity;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;

/**
 *
 * @author Albus Dumbledore
 */
public abstract class ActivityControl extends Control {
    protected final void drawBorder(Graphics g, int x, int y) {
        final Theme theme = context.getTheme();

        g.setColor(theme.colorListElementBorder);
        g.fillRect(
                x, y + getHeight() - theme.listElementBorder,
                getWidth(), theme.listElementBorder);
    }
}

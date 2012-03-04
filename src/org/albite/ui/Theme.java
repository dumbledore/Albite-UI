/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import javax.microedition.lcdui.Image;
import org.albite.ui.Activity;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class Theme {
    public int colorBackground;
    public int colorText;
    public int colorAccent;

    public Image imageBackground;

    public Image iconBook;
    public Image iconNext;

    public static final int BEST_BUTTON_ICON_SIZE = 24;

    public static Theme getDayTheme(final Context context) {
        Theme theme = new Theme();
        theme.colorBackground = 0xFFFFFF;
        theme.colorText = 0x0;
        theme.colorAccent = 0x1483cc;

        theme.imageBackground = context.getImage("ui/background-day.png");

        theme.iconBook = context.getImage("ui/menu/book-day.png");
        theme.iconNext = context.getImage("ui/menu/next-day.png");

        return theme;
    }
}

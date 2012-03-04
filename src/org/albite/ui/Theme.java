/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import org.albite.font.NativeFont;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public abstract class Theme {
    public int colorBackground;
    public int colorText;
    public int colorAccent;

    public Image imageBackground;

    public Image iconBook;
    public Image iconNext;

    public org.albite.font.Font fontCaption;
    public org.albite.font.Font fontSubcaption;

    public static final int BEST_BUTTON_ICON_SIZE = 24;
    protected abstract void setup();

    public static Theme getDayTheme(final Context context) {
        return new Theme() {

            protected void setup() {
                colorBackground = 0xFFFFFF;
                colorText = 0x0;
                colorAccent = 0x1483cc;

                imageBackground = context.getImage("ui/background-day.png");

                iconBook = context.getImage("ui/menu/book-day.png");
                iconNext = context.getImage("ui/menu/next-day.png");

                fontCaption = new NativeFont(Font.getFont(
                        Font.FACE_PROPORTIONAL,
                        Font.STYLE_PLAIN,
                        Font.SIZE_MEDIUM));

                fontSubcaption = new NativeFont(Font.getFont(
                        Font.FACE_PROPORTIONAL,
                        Font.STYLE_PLAIN,
                        Font.SIZE_SMALL));
            }
        };
    }
}

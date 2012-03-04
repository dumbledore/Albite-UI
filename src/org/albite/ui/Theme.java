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
public class Theme {
    public int colorBackground;
    public int colorText;
    public int colorAccent;

    public Image imageBackground;

    public Image iconBook;
    public Image iconNext;

    public org.albite.font.Font fontCaption;
    public org.albite.font.Font fontSubcaption;

    public int listElementMinimumHeight;
    public int listElementPadding;
    public int buttonMinimumHeight;
    public int buttonIconWidth;
    public int buttonArrowWidth;

    public static final int DEFAULT_LIST_ELEMENT_MINIMUM_HEIGHT = 26;
    public static final int DEFAULT_LIST_ELEMENT_PADDING = 10;
    public static final int DEFAULT_BUTTON_MINIMUM_HEIGHT = 30;
    public static final int DEFAULT_BUTTON_ICON_WIDTH = 44;
    public static final int DEFAULT_BUTTON_ARROW_WIDTH = 44;

    protected void setup() {}

    private static Theme DAY_THEME;
    private static Theme NIGHT_THEME;

    public static Theme getDayTheme(final Context context) {
        if (DAY_THEME == null) {
            DAY_THEME = new Theme() {
                protected void setup() {
                    colorBackground = 0xFFFFFF;
                    colorText = 0x0;
                    colorAccent = 0x1483CC;

                    imageBackground = context.getImage("ui/background-day.png");

                    iconBook = context.getImage("ui/menu/book-day.png");
                    iconNext = context.getImage("ui/menu/next-day.png");

                    fontCaption = new NativeFont(Font.getFont(
                            Font.FACE_PROPORTIONAL,
                            Font.STYLE_BOLD,
                            Font.SIZE_MEDIUM));

                    fontSubcaption = new NativeFont(Font.getFont(
                            Font.FACE_PROPORTIONAL,
                            Font.STYLE_PLAIN,
                            Font.SIZE_SMALL));

                    listElementMinimumHeight
                            = DEFAULT_LIST_ELEMENT_MINIMUM_HEIGHT;
                    listElementPadding  = DEFAULT_LIST_ELEMENT_PADDING;
                    buttonMinimumHeight = DEFAULT_BUTTON_MINIMUM_HEIGHT;
                    buttonIconWidth     = DEFAULT_BUTTON_ICON_WIDTH;
                    buttonArrowWidth    = DEFAULT_BUTTON_ARROW_WIDTH;
                }
            };
            DAY_THEME.setup();
        }
        return DAY_THEME;
    }
}

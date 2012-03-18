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
    /*
     * These are precomputed for a specific DPI.
     */
    public static final int DEFAULT_LIST_ELEMENT_MINIMUM_HEIGHT = 26;
    public static final int DEFAULT_LIST_ELEMENT_PADDING = 10;

    public static final int DEFAULT_BUTTON_MINIMUM_HEIGHT = 30;
    public static final int DEFAULT_BUTTON_SHINE_HEIGHT = 5;
    public static final int DEFAULT_BUTTON_BORDER_HEIGHT = 1;
    public static final int DEFAULT_BUTTON_ICON_WIDTH = 44;
    public static final int DEFAULT_BUTTON_ARROW_WIDTH = 44;

    public static final int DEFAULT_SECTION_LINE_HEIGHT = 10;

    public static final int DEFAULT_MENU_BUTTONS_MINIMUM_HEIGHT = 20;
    public static final int DEFAULT_MENU_BUTTONS_PADDING = 10;

    public int colorBackground;
    public int colorText;
    public int colorAccent;

    public int colorButtonNormal;
    public int colorButtonNormalShine;
    public int colorButtonPressed;
    public int colorButtonBorder;

    public int colorMenuButtonNormal;
    public int colorMenuButtonPressed;

    public Image imageBackground;

    public Image imageMenuBarShadow;

    public Image iconMenuBack;
    public Image iconMenuMore;
    public Image iconMenuSearch;

    public Image iconBook;
    public Image iconNext;

    public org.albite.font.Font fontCaption;
    public org.albite.font.Font fontSubcaption;

    public int listElementMinimumHeight = DEFAULT_LIST_ELEMENT_MINIMUM_HEIGHT;
    public int listElementPadding = DEFAULT_LIST_ELEMENT_PADDING;

    public int buttonMinimumHeight = DEFAULT_BUTTON_MINIMUM_HEIGHT;
    public int buttonShineHeight = DEFAULT_BUTTON_SHINE_HEIGHT;
    public int buttonBorderHeight = DEFAULT_BUTTON_BORDER_HEIGHT;
    public int buttonIconWidth = DEFAULT_BUTTON_ICON_WIDTH;
    public int buttonArrowWidth = DEFAULT_BUTTON_ARROW_WIDTH;

    public int sectionLineHeight = DEFAULT_SECTION_LINE_HEIGHT;

    public int menuButtonsMinimumHeight = DEFAULT_MENU_BUTTONS_MINIMUM_HEIGHT;
    public int menuButtonsPadding = DEFAULT_MENU_BUTTONS_PADDING;

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

                    colorButtonNormal = 0xF5F3F3;
                    colorButtonNormalShine = 0xFFFFFF;
                    colorButtonPressed = 0xA9D2EC;
                    colorButtonBorder = 0xADAAAD;

                    colorMenuButtonNormal = 0x454545;
                    colorMenuButtonPressed = colorButtonPressed;

                    imageBackground = context.getImage("ui/background-day.png");
                    imageMenuBarShadow = context.getImage("ui/menu/shadow-day.png");

                    iconMenuBack = context.getImage("ui/menu/back-day.png");
                    iconMenuMore = context.getImage("ui/menu/more-day.png");
                    iconMenuSearch = context.getImage("ui/menu/search-day.png");

                    iconBook = context.getImage("ui/list/book-day.png");
                    iconNext = context.getImage("ui/list/next-day.png");

                    fontCaption = new NativeFont(Font.getFont(
                            Font.FACE_PROPORTIONAL,
                            Font.STYLE_BOLD,
                            Font.SIZE_MEDIUM));

                    fontSubcaption = new NativeFont(Font.getFont(
                            Font.FACE_PROPORTIONAL,
                            Font.STYLE_PLAIN,
                            Font.SIZE_SMALL));
                }
            };
            DAY_THEME.setup();
        }
        return DAY_THEME;
    }
}

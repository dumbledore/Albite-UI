/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.controls.screen.Screen;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public final class Activity
        extends Canvas
        implements Context {

    private Vector screenStack = new Vector(30);
    private final Theme theme;

    public Activity(Theme theme) {
        this.theme = theme;
        setFullScreenMode(true);
    }

    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new NullPointerException("screen is null!");
        }

        /*
         * Remove chain
         */
        final int index = screenStack.indexOf(screen);
        for (int i = 0; i <= index; i++) {
            screenStack.removeElementAt(0);
        }

        screenStack.addElement(screen);
    }

    public void goBack() {
        if (screenStack.size() < 2) {
            return;
        }

        screenStack.removeElementAt(screenStack.size() - 1);
    }

    protected void paint(Graphics g) {
        getCurrentScreen().drawRelative(g, 0, 0);
    }

    protected void pointerPressed(final int x, final int y) {
        getCurrentScreen().pressed(x, y);
    }

    protected void pointerDragged(final int x, final int y) {
        getCurrentScreen().dragged(x, y);
    }

    protected void pointerReleased(final int x, final int y) {
        getCurrentScreen().released(x, y);
    }

    public Screen getCurrentScreen() {
        return (Screen) screenStack.lastElement();
    }

    public void redraw(boolean force) {
        repaint();
        if (force) {
            serviceRepaints();
        }
    }

    private static final String DPI = "low";

    private static final String RESOURCES_PATH = "/res/";

    public final InputStream getStream(final String path) {
        return getClass().getResourceAsStream(RESOURCES_PATH + path);
    }

    private static final String IMAGES_PATH = RESOURCES_PATH + "images/";
    private static final String IMAGES_SPECIAL_RESOLUTION_PATH =
            IMAGES_PATH + "/" + DPI + "/";
    private static final String IMAGES_COMMON_PATH =
            IMAGES_PATH + "/all/";

    public final Image getImage(final String path) {
        try {
            return Image.createImage(IMAGES_SPECIAL_RESOLUTION_PATH + path);
        } catch (IOException e) {}

        try {
            return Image.createImage(IMAGES_COMMON_PATH + path);
        } catch (IOException e) {}

        return null;
    }

    private static final String DEFAULT_LOCALE = "EN";
    private Hashtable localStrings = new Hashtable(128);

    public final String getLocalisedString(final String key) {
        return (String) localStrings.get(key);
    }

    public final Theme getTheme() {
        return theme;
    }
}

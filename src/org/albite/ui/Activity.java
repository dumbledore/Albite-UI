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
import javax.microedition.midlet.MIDlet;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public final class Activity
        extends Canvas
        implements Context {

    private static final float DRAGGING_FACTOR = 0.01f;

    private final MIDlet midlet;

    private Vector screenStack = new Vector(30);
    private Theme theme;

    private int minimumDragging;
    private boolean draggingStarted;

    private int dx;
    private int dy;

    public Activity(final MIDlet midlet) {
        this.midlet = midlet;

        setFullScreenMode(true);

        minimumDragging = (int) (getWidth() * getHeight()
                * DRAGGING_FACTOR * DRAGGING_FACTOR);
    }

    public int getMinimumDragging() {
        return minimumDragging;
    }

    public void setMinimumDragging(int minimumDragging) {
        if (minimumDragging < 0) {
            throw new IllegalArgumentException();
        }

        this.minimumDragging = minimumDragging;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(final Theme theme) {
        this.theme = theme;
    }

    public void setScreen(Screen screen) {
        if (screen == null) {
            throw new NullPointerException("screen is null!");
        }

        /*
         * If the new screen is already in the stack, remove it and all
         * screens after it.
         */
        final int index = screenStack.indexOf(screen);
        if (index >= 0) {
            screenStack.setSize(index);
        }

        screenStack.addElement(screen);
    }

    public void setPreviousScreen() {
        if (screenStack.size() < 2) {
            /*
             * Exit the application
             */
            midlet.notifyDestroyed();
        }

        screenStack.removeElementAt(screenStack.size() - 1);
    }

    protected void paint(Graphics g) {
        g.setClip(0, 0, getWidth(), getHeight());

        /*
         * Draw the first layer
         */
        getCurrentScreen().drawRelative(g, 0, 0, 0);
    }

    protected void pointerPressed(final int x, final int y) {
        dx = x;
        dy = y;

        getCurrentScreen().pressed(x, y);
    }

    protected void pointerDragged(final int x, final int y) {
        if (draggingStarted
                || Math.abs(x - dx) >= minimumDragging
                || Math.abs(y - dy) >= minimumDragging) {

            draggingStarted = true;
            dx = x;
            dy = y;

            getCurrentScreen().dragged(x, y);
        }
    }

    protected void pointerReleased(final int x, final int y) {
        draggingStarted = false;

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
            IMAGES_PATH + DPI + "/";
    private static final String IMAGES_COMMON_PATH =
            IMAGES_PATH + "/all/";

    public final Image getImage(final String path) {
        try {
            return Image.createImage(IMAGES_SPECIAL_RESOLUTION_PATH + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return Image.createImage(IMAGES_COMMON_PATH + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private static final String DEFAULT_LOCALE = "EN";
    private Hashtable localStrings = new Hashtable(128);

    public final String getLocalisedString(final String key) {
        return (String) localStrings.get(key);
    }
}

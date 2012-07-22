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

/**
 *
 * @author albus
 */
public final class ActivityStack
        extends Canvas
        implements Context {

    private static final float DRAGGING_FACTOR = 0.01f;

    private final ExitCallback exitCallback;

    private Vector activityStack = new Vector(30);
    private Theme theme;

    private int minimumDragging;
    private boolean draggingStarted;

    private int dx;
    private int dy;

    public ActivityStack(final ExitCallback exitCallback) {
        this.exitCallback = exitCallback;

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

    public void setActivity(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is null!");
        }

        /*
         * If the new activity is already in the stack, remove all
         * activities after it and return.
         */
        final int index = activityStack.indexOf(activity);
        if (index > 0) {
            /*
             * Destroy the following activities
             */
            for (int i = index + 1; i < activityStack.size(); i++) {
                Activity a = (Activity) activityStack.elementAt(i);
                a.onDestroy();
            }

            /*
             * Update the vector size
             */
            activityStack.setSize(index + 1);
            return;
        }

        /*
         * Call onCreate() on the new Activity and add it on the stack.
         */
        activity.onCreate();
        activityStack.addElement(activity);
    }

    public void goToPreviousActivity() {
        if (activityStack.size() < 2) {
            /*
             * Exit the application
             */
            exitCallback.onExit();
            return;

            /*
             * What to do with the activity? If it's removed, then
             * other functions will fail, if it stays,
             * it will cause problems if the stack is reused.
             */
        }

        activityStack.removeElementAt(activityStack.size() - 1);
    }

    protected void paint(Graphics g) {
        g.setClip(0, 0, getWidth(), getHeight());

        /*
         * Draw the first layer
         */
        getCurrentActivity().drawRelative(g, 0, 0, 0);

        /*
         * Draw the second layer
         */
        getCurrentActivity().drawRelative(g, 0, 0, 1);
    }

    protected void pointerPressed(final int x, final int y) {
        dx = x;
        dy = y;

        getCurrentActivity().pressed(x, y);
    }

    protected void pointerDragged(final int x, final int y) {
        if (draggingStarted
                || Math.abs(x - dx) >= minimumDragging
                || Math.abs(y - dy) >= minimumDragging) {

            draggingStarted = true;
            dx = x;
            dy = y;

            getCurrentActivity().dragged(x, y);
        }
    }

    protected void pointerReleased(final int x, final int y) {
        draggingStarted = false;

        getCurrentActivity().released(x, y);
    }

    public Activity getCurrentActivity() {
        return (Activity) activityStack.lastElement();
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

    public interface ExitCallback {
        void onExit();
    }
}

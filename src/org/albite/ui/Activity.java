/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
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

    public Activity() {
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
        getCurrentScreen().draw(g, 0, 0);
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
}

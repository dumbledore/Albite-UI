/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import java.util.Enumeration;
import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.AlbiteControl;
import org.albite.ui.controls.BackgroundImage;
import org.albite.ui.controls.LayoutControl;
import org.albite.ui.core.Context;

/**
 *
 * @author albus
 */
public final class AlbiteScreen extends LayoutControl {

    private boolean shown = false; //TODO?

    private LayoutControl layout;

    /*
     * Menu buttons
     */

    public AlbiteScreen(final String title, final Context context) {
        super(null, context);
        initializeBackground();
        //TODO: Add title control
    }

    private static BackgroundImage background;

    private void initializeBackground() {
        if (background == null) {
            background = new BackgroundImage("/res/bg.png", this, context);
            background.setWidth(context.getWidth());
            background.setHeight(context.getHeight());
        }
    }

    public void setContentLayout(final LayoutControl layout) {
        this.layout = layout;
    }

    void setShown(final boolean shown) {
        this.shown = shown;
    }

    public void addControl(AlbiteControl control) {
        layout.addControl(control);
    }

    public void invalidate() {
        layout.invalidate();
    }

    public void draw(Graphics g, final int x, final int y) {
        background.drawRelative(g, x, y);
        layout.drawRelative(g, x, y);
    }

    public int getWidth() {
        return context.getWidth();
    }

    public int getHeight() {
        return context.getHeight();
    }

    public void setWidth(int width) {}
    public void setHeight(int height) {}
    public void setX(int x) {}
    public void setY(int y) {}

    protected Enumeration getControls() {
        return new Enumeration() {

            private boolean hasMore = true;

            public boolean hasMoreElements() {
                return hasMore;
            }

            public Object nextElement() {
                hasMore = false;
                return layout;
            }
        };
    }

    public boolean isWidthFixed() {
        return true;
    }

    public boolean isHeightFixed() {
        return true;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.core.Context;
import org.albite.ui.core.Touchable;

/**
 *
 * @author albus
 */
public abstract class AlbiteControl
        implements Touchable {

    protected int x = 0;
    protected int y = 0;
    protected int width = 0;
    protected int height = 0;

    protected boolean enabled = true;

    protected final LayoutControl parent;
    protected final Context context;

    public AlbiteControl(final LayoutControl parent, final Context context) {
        this.parent = parent;
        this.context = context;
    }

    public abstract void invalidate();

    public final LayoutControl getParent() {
        return parent;
    }

    public Context getContext() {
        return context;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean contains(final int x, final int y) {
        return this.x <= x && x <= this.x + width &&
                this.y <= y && y <= this.y + height;
    }

    public final void drawRelative(final Graphics g, final int x, final int y) {
        draw(g, this.x + x, this.y + y);
    }

    protected abstract void draw(Graphics g, int x, int y);

    public static Image loadImage(final String url) {
        try {
            return Image.createImage(url);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    protected final void requestDraw(final boolean forced) {
        context.redraw(forced);
    }

    protected void dump(final int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }

        System.out.println(this + ": ((" + x + ", " + y
                + "), (" + width + ", " + height + ")");
    }

    public final void dump() {
        dump(0);
    }
}

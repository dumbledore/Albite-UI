/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.core.interfaces.Context;
import org.albite.ui.core.interfaces.Touchable;

/**
 *
 * @author albus
 */
public abstract class Control
        implements Touchable {

    protected int x = 0;
    protected int y = 0;
    protected int width = 0;
    protected int height = 0;

    protected boolean enabled = true;

    protected final Control parent;
    protected final Context context;

    public Control(final Control parent, final Context context) {
        this.parent = parent;
        this.context = context;
    }

    public final Control getParent() {
        return parent;
    }

    public Context getContext() {
        return context;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void invalidate() {
        parent.invalidate();
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

    public void dump(final int level) {
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

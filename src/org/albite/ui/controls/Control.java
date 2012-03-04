/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.core.interfaces.Context;
import org.albite.ui.core.interfaces.Touchable;

/**
 *
 * @author albus
 */
public abstract class Control
        implements Touchable {

    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

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

    /*
     * This invalidates it up the tree.
     * This is used if only a single control has changed it's appearance.
     */
    public final void invalidate() {
        recompileMetrics(false);
        if (parent != null) {
            parent.invalidate();
        }
    }

    /*
     * This invalidates it down the tree.
     * This is used if the whole sub-tree needs to be composed.
     */
    public void invalidateDown() {
        recompileMetrics(true);
    }

    /**
     * recompiles this controls metrics
     * @param downTree if going downtree and need to update in depth
     */
    public void recompileMetrics(boolean downTree) {}

    public boolean contains(final int x, final int y) {
        final int x_ = getX();
        final int y_ = getY();
        final int w_ = getWidth();
        final int h_ = getHeight();

        return x_ <= x && x <= x_ + w_ &&
                y_ <= y && y <= y_ + h_;
    }

    public void drawRelative(final Graphics g, final int x, final int y) {
        draw(g, this.x + x, this.y + y);
    }

    protected abstract void draw(Graphics g, int x, int y);

    protected final void requestDraw(final boolean forced) {
        context.redraw(forced);
    }

    public void dump(final int level) {
        if (level > 0) {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("| ");
            }
            System.out.print("|-");
        }

        System.out.println(this + ": ((" + getX() + ", " + getY()
                + "), (" + getWidth() + ", " + getHeight() + ")");
    }

    public final void dump() {
        dump(0);
    }
}

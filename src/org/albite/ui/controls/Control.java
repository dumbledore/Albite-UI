/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.Context;

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
    protected boolean visible = true;

    protected Control parent;
    protected Context context;

    public final Control getParent() {
        return parent;
    }

    public final Context getContext() {
        return context;
    }

    public void initialize(final Control parent, final Context context) {
        this.parent = parent;
        this.context = context;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visibility) {
        this.visible = visibility;
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
    public final void invalidateUp() {
        if (isVisible()) {
            recompileMetrics(false);
        }

        if (parent != null) {
            parent.invalidateUp();
        }
    }

    /*
     * This invalidates it down the tree.
     * This is used if the whole sub-tree needs to be composed.
     */
    public void invalidateDown() {
        if (isVisible()) {
            recompileMetrics(true);
        }
    }

    /**
     * recompiles this controls metrics
     * @param downTree if going downtree and need to update in depth
     */
    public void recompileMetrics(boolean downTree) {}

    public boolean isFocusable() {
        return false;
    }

    public void gainedFocus() {}
    public void lostFocus() {}

    public void pressed(int x, int y) {}
    public void dragged(int x, int y) {}
    public void released(int x, int y) {}

    public boolean contains(final int x, final int y) {
        if (!visible) {
            return false;
        }

        final int x_ = getX();
        final int y_ = getY();
        final int w_ = getWidth();
        final int h_ = getHeight();

        return x_ <= x && x <= x_ + w_ &&
                y_ <= y && y <= y_ + h_;
    }

    public void drawRelative(final Graphics g,
            final int x, final int y, final int zOrder) {

        if (visible) {
            final int x_ = this.x + x;
            final int y_ = this.y + y;
            //g.setClip(x_, y_, getWidth(), getHeight());
            if (getWidth() < 1 || getHeight() < 1) {
                System.out.println("This is wrong: " + getClass().getName() + ", width:  " + getWidth() + ", height: " + getHeight());
                if (parent != null)
                    System.out.println("Parent is: " + parent.getClass().getName());
            }
            draw(g, this.x + x, this.y + y, zOrder);
        }
    }

    protected abstract void draw(Graphics g, int x, int y, int zOrder);

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

    public interface ClickCallback {
        public void clicked(Control control);
    }
}

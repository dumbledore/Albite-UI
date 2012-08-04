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

    private boolean debug = false;
    private static final int DEBUG_COLOR = 0xFF0000;
    private static final int DEBUG_STROKE = Graphics.SOLID;

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

            /*
             * Set the drawing area
             */
            g.setClip(x_, y_, getWidth(), getHeight());

            /*
             * Draw debug outlines if they have been enabled for this control
             */
            debugDraw(g, x_, y_);

            draw(g, this.x + x, this.y + y, zOrder);
        }
    }

    private void debugDraw(final Graphics g, final int x, final int y) {
        if (debug) {
            int w = getWidth() - 1;
            int h = getHeight() - 1;

            w = w > 0 ? w : 1;
            h = h > 0 ? h : 1;

            int color = g.getColor();
            int stroke = g.getStrokeStyle();

            g.setColor(DEBUG_COLOR);
            g.setStrokeStyle(DEBUG_STROKE);

            g.drawRect(x, y, w, h);

            g.setColor(color);
            g.setStrokeStyle(stroke);
        }
    }

    protected abstract void draw(Graphics g, int x, int y, int zOrder);

    protected final void requestDraw(final boolean forced) {
        context.redraw(forced);
    }

    public interface ClickCallback {
        public void clicked(Control control);
    }

    /*
     * Debug functionality
     */

    public void setDebugMode(boolean enabled) {
        debug = enabled;

        /*
         * The containers would need to call their children from here.
         */
        setDebugModeImpl(enabled);
    }

    protected void setDebugModeImpl(boolean enabled) {}

    public final boolean getDebugMode() {
        return debug;
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

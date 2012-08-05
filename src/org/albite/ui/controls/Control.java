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

    private int debug = DEBUG_NONE;

    public static final int DEBUG_NONE              = 0;
    public static final int DEBUG_DIMENSIONS        = 1;
    public static final int DEBUG_CROP              = 2;

    private static final int DEBUG_DIMENSIONS_COLOR = 0x0080FF;
    private static final int DEBUG_CROP_0_COLOR     = 0x00FF00;
    private static final int DEBUG_CROP_1_COLOR     = 0xFF0000;
    private static final int DEBUG_STROKE           = Graphics.SOLID;

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
     * These are called when the clip is set. They may differ from the
     * x, y, width and height which are actually the content dimensions,
     * i.e. the space occupied in the layout.
     *
     * Also they might be different for each render pass.
     */
    public int getClipX(int zOrder) {
        return getX();
    }

    public int getClipY(int zOrder) {
        return getY();
    }

    public int getClipWidth(int zOrder) {
        return getWidth();
    }

    public int getClipHeight(int zOrder) {
        return getHeight();
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
     * @param downTree if going down-tree and need to update in depth
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

        /*
         * Skip rendering for one-pass only controls.
         */
        if (zOrder > 0 && drawOnlyFirstLayer()) {
            return;
        }

        if (visible) {
            final int x_ = getClipX(zOrder) + x;
            final int y_ = getClipY(zOrder) + y;

            /*
             * Store the current clipping box, which is that of the parent.
             */
            final int parentClipX = g.getClipX();
            final int parentClipY = g.getClipY();
            final int parentClipW = g.getClipWidth();
            final int parentClipH = g.getClipHeight();

            /*
             * Compute the clipping box for this control
             */
            final int clipX = x_;
            final int clipY = y_;
            final int clipW = getClipWidth(zOrder);
            final int clipH = getClipHeight(zOrder);

            /*
             * Intersect both clipboxes
             *
             * Note: The fact that the clip box is specified as (x, y) x (w, h),
             * instead of (left, top, right, bottom), would somewhat
             * obscure the actual math.
             */

            final int actualClipX = Math.max(parentClipX, clipX);
            final int actualClipY = Math.max(parentClipY, clipY);

            final int parentClipR = parentClipX + parentClipW;
            final int clipR = clipX + clipW;

            final int actualClipW =
                    (parentClipR < clipR ? parentClipR : clipR) - actualClipX;

            final int parentClipB = parentClipY + parentClipH;
            final int clipB = clipY + clipH;

            final int actualClipH =
                    (parentClipB < clipB ? parentClipB : clipB) - actualClipY;

            /*
             * Set the drawing area
             */
            g.setClip(actualClipX, actualClipY, actualClipW, actualClipH);

            /*
             * Print debug clipping info if enabled
             */
            debugClip(g, zOrder);

            final int xPos = this.x + x;
            final int yPos = this.y + y;

            draw(g, xPos, yPos, zOrder);

            /*
             * Draw debug outlines if enabled
             */
            debugDimensions(g, xPos, yPos);

            /*
             * Restore the original clip
             */
            g.setClip(parentClipX, parentClipY, parentClipW, parentClipH);
        }
    }

    protected boolean drawOnlyFirstLayer() {
        return false;
    }

    private void debugDimensions(final Graphics g, final int x, final int y) {
        if ((debug & DEBUG_DIMENSIONS) != 0) {
            int w = getWidth() - 1;
            int h = getHeight() - 1;

            w = w > 0 ? w : 1;
            h = h > 0 ? h : 1;

            int color = g.getColor();
            int stroke = g.getStrokeStyle();

            g.setColor(DEBUG_DIMENSIONS_COLOR);
            g.setStrokeStyle(DEBUG_STROKE);

            g.drawRect(x, y, w, h);

            g.setColor(color);
            g.setStrokeStyle(stroke);
        }
    }

    private void debugClip(final Graphics g, final int zOrder) {
        if ((debug & DEBUG_CROP) != 0) {
            int w = g.getClipWidth() - 1;
            int h = g.getClipHeight() - 1;

            w = w > 0 ? w : 1;
            h = h > 0 ? h : 1;

            int color = g.getColor();
            int stroke = g.getStrokeStyle();

            g.setColor(zOrder < 1 ? DEBUG_CROP_0_COLOR : DEBUG_CROP_1_COLOR);
            g.setStrokeStyle(DEBUG_STROKE);

            g.drawRect(g.getClipX(), g.getClipY(), w, h);

            g.setColor(color);
            g.setStrokeStyle(stroke);
        }
    }

    protected abstract void draw(Graphics g, int x, int y, int zOrder);

    protected final void requestDraw(final boolean forced) {
        context.redraw(forced);
    }

    protected final void drawBottomBorder(
            Graphics g, int y, int height, int color) {

        final int y_ = y + this.height - height;

        g.setColor(color);
        g.fillRect(x, y_, width, height);
    }

    public interface ClickCallback {
        public void clicked(Control control);
    }

    /*
     * Debug functionality
     */

    public void setDebugMode(int mode) {
        debug = mode;
    }

    public final int getDebugMode() {
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

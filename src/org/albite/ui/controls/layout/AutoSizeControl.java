/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

/**
 *
 * @author albus
 */
public final class AutoSizeControl extends AdapterControl {

    private boolean matchParentWidth = false;
    private boolean matchParentHeight = false;

    private int minWidth = 0;
    private int minHeight = 0;

    private int maxWidth = Integer.MAX_VALUE;
    private int maxHeight = Integer.MAX_VALUE;

    public static final int CENTER  = 0;
    public static final int LEFT    = 1;
    public static final int RIGHT   = 2;
    public static final int TOP     = 4;
    public static final int BOTTOM  = 8;

    private int horizontalGravity = CENTER;
    private int verticalGravity = CENTER;

    /**
     * If true, width will match parent's. If false, it will match
     * the width of the content.
     */
    public final void setMatchParentWidth(final boolean matchParentWidth) {
        this.matchParentWidth = matchParentWidth;
    }

    /**
     * If true, height will match parent's. If false, it will match
     * the height of the content.
     */
    public final void setMatchParentHeight(final boolean matchParentHeight) {
        this.matchParentHeight = matchParentHeight;
    }

    public final void setMinWidth(final int minWidth) {
        this.minWidth = minWidth;
    }

    public final void setMinHeight(final int minHeight) {
        this.minHeight = minHeight;
    }

    public final void setMaxWidth(final int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public final void setMaxHeight(final int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public final void setHorizontalGravity(final int horizontalGravity) {
        this.horizontalGravity = horizontalGravity;
    }

    public final void setVerticalGravity(final int verticalGravity) {
        this.verticalGravity = verticalGravity;
    }

    public void invalidateDown() {
        recompileMetrics(true);
    }

    public void recompileMetrics(final boolean downTree) {
        /*
         * Compute width / height if they depend on the parent;
         */
        int width = matchParentWidth ? parent.getWidth() : getWidth();
        width = width < minWidth ? minWidth : width;
        width = width > maxWidth ? maxWidth : width;

        int height = matchParentHeight ? parent.getHeight() : getHeight();
        height = height < minHeight ? minHeight : height;
        height = height > maxHeight ? maxHeight : height;

        setWidth(width);
        setHeight(height);

        if (downTree) {
            /*
             * Let the child compute it's dimensions
             */
            control.invalidateDown();
        }

        /*
         * Recompute width and height yet again.
         */
        width = control.getWidth() > width  ? control.getWidth() : width;
        width = width < minWidth ? minWidth : width;
        width = width > maxWidth ? maxWidth : width;

        height = control.getHeight() > height  ? control.getHeight() : height;
        height = height < minHeight ? minHeight : height;
        height = height > maxHeight ? maxHeight : height;

        setWidth(width);
        setHeight(height);

        /*
         * Compute gravity positions
         */
        switch(horizontalGravity) {
            case LEFT:
            {
                control.setX(0);
                break;
            }

            case CENTER:
            {
                control.setX((width - control.getWidth()) / 2);
                break;
            }

            case RIGHT:
            {
                control.setX(width - control.getWidth());
                break;
            }
        }

        switch(verticalGravity) {
            case TOP:
            {
                control.setY(0);
                break;
            }

            case CENTER:
            {
                control.setY((height - control.getHeight()) / 2);
                break;
            }

            case BOTTOM:
            {
                control.setY(height - control.getHeight());
                break;
            }
        }
    }
}

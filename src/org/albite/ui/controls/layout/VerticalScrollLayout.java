/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.Control;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class VerticalScrollLayout extends AdapterControl {

    private static final float MARGIN_AREA_RATIO = 0.05f;
    private final int marginArea;
    private int dy;
    private VerticalScrollBar bar;

    public VerticalScrollLayout(final Control parent, final Context context) {
        super(parent, context);
        bar = new VerticalScrollBar(this, context);
        marginArea = (int) (MARGIN_AREA_RATIO * context.getHeight());
    }

    public int getWidth() {
        return parent.getWidth();
    }

    public int getHeight() {
        return parent.getHeight();
    }

    private void update(final int margin) {
        if (control.getY() > margin) {
            control.setY(margin);
        } else {
            int h = control.getHeight();
            int minY;
            if (h < parent.getHeight()) {
                minY = -margin;
            } else {
                minY = parent.getHeight() - h - margin;
            }

            if (control.getY() < minY) {
                control.setY(minY);
            }
        }
        requestDraw(false);
    }

    public void invalidateDown() {
        control.invalidateDown();
        recompileMetrics(true);
    }

    public void recompileMetrics(final boolean downTree) {
        bar.recompileMetrics(downTree);
        update(0);
    }

    public void pressed(int x, int y) {
        dy = y;

        if (enabled) {
            int x_ = x - this.getX();
            int y_ = y - this.getY();

            isSelected = contains(x_, y_);
            if (isSelected) {
                control.pressed(x_, y_);
            }
        }
    }

    public void dragged(int x, int y) {
        if (isSelected) {
            control.lostFocus();
            isSelected = false;
        }
        control.setY(control.getY() + y - dy);
        dy = y;
        update(marginArea);
    }

    public void released(int x, int y) {
        if (enabled && isSelected) {
            control.released(x - this.getX(), y - this.getY());
            isSelected = false;
        }
        update(0);
    }

    public void draw(final Graphics g, final int x, final int y) {
        control.drawRelative(g, x, y);
        bar.drawRelative(g, x, y);
    }

    private class VerticalScrollBar extends Control {

        private static final float SCROLL_BAR_WIDTH_FACTOR = 0.01f;
        private static final float SCROLL_BAR_MIN_HEIGHT_FACTOR = 0.05f;
        private static final float SCROLL_BAR_MAX_HEIGHT_FACTOR = 0.3f;
        private static final float CONTROL_MIN_HEIGHT_FACTOR = 0.5f;
        private static final float CONTROL_MAX_HEIGHT_FACTOR = 2.0f;

        private final int barMinHeight;
        private final int barMaxHeight;

        private final float heightInterpolateA, heightInterpolateB;
        private float positionInterpolate;
        private int positionMax;
        private boolean isActive = false;

        public VerticalScrollBar(final Control parent, final Context context) {
            super(parent, context);

            setWidth(Math.max((int) (parent.getWidth() * SCROLL_BAR_WIDTH_FACTOR), 2));

            final int h = context.getHeight();

            barMinHeight = Math.max(1,
                    (int) (h * SCROLL_BAR_MIN_HEIGHT_FACTOR));
            barMaxHeight = Math.max(1,
                    (int) (h * SCROLL_BAR_MAX_HEIGHT_FACTOR));

            final float controlMinHeight =
                    Math.max(1.0f, (h * CONTROL_MIN_HEIGHT_FACTOR));
            final float controlMaxHeight =
                    Math.max(1.0f, (h * CONTROL_MAX_HEIGHT_FACTOR));

            heightInterpolateA = (barMinHeight - barMaxHeight)
                    / (controlMaxHeight - controlMinHeight);
            heightInterpolateB = barMaxHeight
                    - heightInterpolateA * controlMinHeight;
        }

        public void recompileMetrics(boolean downTree) {
            setX(parent.getWidth() - getWidth());

            int h = (int) (heightInterpolateA * control.getHeight()
                    + heightInterpolateB);
            setHeight(Math.min(Math.max(h, barMinHeight), barMaxHeight));

            int bottom = parent.getHeight() - control.getHeight();
            if (bottom < 0) {
                positionMax = parent.getHeight() - getHeight();
                positionInterpolate = ((float) positionMax / bottom);
                isActive = true;
            } else {
                isActive = false;
            }
        }

        protected void draw(Graphics g, int x, int y) {
            if (isActive) {
                int y_ = (int) (positionInterpolate * control.getY());
                y_ = Math.max(y_, 0);
                y_ = Math.min(y_, positionMax);

                g.setColor(context.getTheme().colorAccent);
                g.fillRect(getX(), y_, getWidth(), getHeight());
            }
        }
    }
}

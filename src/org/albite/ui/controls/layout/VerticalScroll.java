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
public class VerticalScroll extends AdapterControl {

    private static final float MARGIN_AREA_RATIO = 0.05f;
    private final int marginArea;
    private int yy;

    public VerticalScroll(final Control parent, final Context context) {
        super(parent, context);
        marginArea = (int) (MARGIN_AREA_RATIO * context.getHeight());
    }

    public int getWidth() {
        return parent.getWidth();
    }

    public int getHeight() {
        return control.getHeight();
    }

    private void update(final int margin) {
        if (getY() > margin) {
            setY(margin);
        } else {
            int h = getHeight();
            int minY;
            if (h < parent.getHeight()) {
                minY = -margin;
            } else {
                minY = parent.getHeight() - h - margin;
            }
            
            if (getY() < minY) {
                setY(minY);
            }
        }
        requestDraw(false);
    }

    public void recompileMetrics(boolean downTree) {
        control.recompileMetrics(downTree);
        update(0);
    }

    public void pressed(int x, int y) {
        yy = y;

        if (enabled) {
            isSelected = contains(x, y);
            if (isSelected) {
                control.pressed(x - this.getX(), y - this.getY());
            }
        }
    }

    public void dragged(int x, int y) {
        if (isSelected) {
            control.lostFocus();
            isSelected = false;
        }
        setY(getY() + y - yy);
        yy = y;
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
    }
}

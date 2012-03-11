/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class ImageControl extends Control {

    private Image image;
    private boolean repeat = false;

    public ImageControl(final Control parent, final Context context) {
        super(parent, context);
    }

    public final void setImage(final Image image) {
        if (image == null) {
            return;
        }

        this.image = image;
        setWidth(image.getWidth());
        setHeight(image.getHeight());
    }

    public final void setRepeat(final boolean repeat) {
        this.repeat = repeat;
    }

    protected void draw(Graphics g, int x, int y) {
        if (image == null) {
            return;
        }

        if (!repeat) {
            g.drawImage(image, x, y, Graphics.TOP | Graphics.LEFT);
        } else {
            final int w = image.getWidth();
            final int h = image.getHeight();
            final int xh = x + getHeight();
            final int yw = y + getWidth();

            for (int yy = y; yy < xh; yy += h) {
                for (int xx = x; xx < yw; xx += w) {
                    g.drawImage(image, xx, yy, Graphics.TOP | Graphics.LEFT);
                }
            }
        }
    }

    public void pressed(int x, int y) {}

    public void dragged(int x, int y) {}

    public void released(int x, int y) {}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author albus
 */
public class ImageControl extends Control {

    private Image image;
    private boolean repeat = false;

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

    protected void draw(Graphics g, int x, int y, int zOrder) {
        if (zOrder > 0 || image == null) {
            return;
        }

        if (!repeat) {
            g.drawImage(image, x, y, Graphics.TOP | Graphics.LEFT);
        } else {
            final int w = image.getWidth();
            final int h = image.getHeight();
            final int xw = x + getWidth();
            final int yh = y + getHeight();

            for (int yy = y; yy < yh; yy += h) {
                for (int xx = x; xx < xw; xx += w) {
                    g.drawImage(image, xx, yy, Graphics.TOP | Graphics.LEFT);
                }
            }
        }
    }
}

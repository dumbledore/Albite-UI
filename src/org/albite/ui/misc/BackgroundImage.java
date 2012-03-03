/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.misc;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.controls.Control;

/**
 *
 * @author albus
 */
public class BackgroundImage {

    final Image image;

    private int width = 0;
    private int height = 0;

    public BackgroundImage(final String url) {
        image = Control.loadImage(url);
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void draw(final Graphics g, final int x, final int y) {
        if (image != null) {
            final int w = image.getWidth();
            final int h = image.getHeight();
            final int xh = x + height;
            final int yw = y + width;

            for (int yy = y; yy < xh; yy += h) {
                for (int xx = x; xx < yw; xx += w) {
                    g.drawImage(image, xx, yy, Graphics.TOP | Graphics.LEFT);
                }
            }
        }
    }
}

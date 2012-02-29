/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.core.Context;

/**
 *
 * @author albus
 */
public class BackgroundImage extends AlbiteControl {

    final Image image;

    public BackgroundImage(final String url, final LayoutControl parent, final Context context) {
        super(parent, context);
        image = loadImage(url);
    }

    public void invalidate() {}

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void draw(Graphics g) {
        if (image != null) {
            final int w = image.getWidth();
            final int h = image.getHeight();

            for (int y = 0; y < height; y += h) {
                for (int x = 0; x < width; x += w) {
                    g.drawImage(image, x, y, Graphics.TOP | Graphics.LEFT);
                }
            }
        }
    }

    public boolean contains(final int x, final int y) {
        return false;
    }

    public void pressed(int x, int y) {}

    public void dragged(int x, int y) {}

    public void released(int x, int y) {}

}

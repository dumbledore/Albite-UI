/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.font;

import java.util.Hashtable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author albus
 */
public class NativeFont extends org.albite.font.Font {

    private final Font font;
    private final Hashtable metrics = new Hashtable(1024);

    public NativeFont(final Font font) {
        this.font = font;
    }

    public int getLineHeight() {
        return font.getHeight();
    }

    public int charWidth(char c) {
        final Integer cachedWidth = (Integer) metrics.get(new Character(c));
        if (cachedWidth != null) {
            return cachedWidth.intValue();
        }

        final int width = font.charWidth(c);
        metrics.put(new Character(c), new Integer(width));
        return width;
    }

    public void drawChars(
            Graphics g, int color,
            int x, int y,
            char[] buffer, int offset, int length) {

        g.setColor(color);
        g.setFont(font);

        char c;
        final int end = offset + length;
        for (int i = offset; i < end; i++) {
            c = buffer[i];
            g.drawChar(c, x, y, Graphics.TOP | Graphics.LEFT);
            x += charWidth(c);
        }
    }

    public void drawChar(Graphics g, int color, char c, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        g.drawChar(c, x, y, Graphics.TOP | Graphics.LEFT);
    }
}

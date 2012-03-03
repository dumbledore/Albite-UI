/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.font;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Albus Dumbledore
 */
public abstract class Font {
    
    public abstract int getLineHeight();

    public int getLineSpacing() {
        return 0;
    }

    public abstract int charWidth(char c);

    public final int charsWidth(
            final char[] c, final int offset, final int length) {

        int width = 0;
        final int end = offset + length;

        for (int i = offset; i < end; i++) {
            width += charWidth(c[i]);
        }

        return width;
    }

    public final int charsWidth(final char[] c) {
        return charsWidth(c, 0, c.length);
    }

    public abstract void drawChar(
            Graphics g, int color, char c, int x, final int y);

    public abstract void drawChars(
            Graphics g, int color,
            int x, final int y,
            char[] buffer, int offset, int length);

    public final void drawChars(
            Graphics g, int color,
            final int x, final int y,
            char[] buffer) {

        drawChars(g, color, x, y, buffer, 0, buffer.length);
    }
}
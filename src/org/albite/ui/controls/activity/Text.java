/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.activity;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.albite.font.NativeFont;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.TextControl;
import org.albite.ui.Context;

/**
 *
 * @author Albus Dumbledore
 */
public final class Text extends ActivityControl {

    private static final org.albite.font.Font FONT =
            new NativeFont(Font.getFont(
            Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));

    private final TextControl text = new TextControl();

    public void initialize(final Control parent, final Context context) {
        super.initialize(parent, context);
        text.initialize(this, context);
        text.setFont(FONT);
    }

    public final void recompileMetrics(final boolean downTree) {
        final Theme theme = context.getTheme();
        final int padding = 2 * theme.listElementPadding;

        final int width = parent.getWidth() - padding;

        if (downTree) {
            setWidth(width);
            text.invalidateDown();
        }

        final int height = text.getHeight() + padding;

        setWidth(parent.getWidth());
        setHeight(height + theme.listElementBorder);
        
        text.setX((parent.getWidth() - width) / 2);
        text.setY((height - text.getHeight()) / 2);
        text.setColor(theme.colorText);
    }

    protected void draw(Graphics g, int x, int y, int zOrder) {
        if (zOrder > 0) {
            return;
        }

        g.setColor(context.getTheme().colorBackground);
        g.fillRect(x, y, getWidth(), getHeight());

        text.drawRelative(g, x, y, zOrder);

        drawBorder(g, x, y);
    }

    public final void setText(final String text) {
        this.text.setText(text);
    }

    public final void setText(final char[] text) {
        this.text.setText(text);
    }
}

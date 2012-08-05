/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.activity;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.albite.core.character.AlbiteCharacter;
import org.albite.font.NativeFont;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.ImageControl;
import org.albite.ui.controls.TextControl;
import org.albite.ui.Context;

/**
 *
 * @author albus
 */
public class Section extends Control {

    private static final org.albite.font.Font FONT =
            new NativeFont(Font.getFont(
            Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE));

    private final ImageControl shadow = new ImageControl();
    private final TextControl text = new TextControl();

    public void initialize(final Control parent, final Context context) {
        super.initialize(parent, context);

        text.initialize(this, context);
        text.setFont(FONT);

        shadow.initialize(this, context);
        shadow.setImage(context.getTheme().imageActivitySectionShadow);
        shadow.setRepeat(true);
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
        setHeight(height + theme.listElementBorder + theme.sectionStripeHeight);

        shadow.setWidth(getWidth());
        shadow.setY(getHeight());

        text.setX((parent.getWidth() - width) / 2);
        text.setY((height - text.getHeight()) / 2);
        text.setColor(theme.colorAccent);
    }

    protected void draw(Graphics g, int x, int y, int zOrder) {
        switch (zOrder) {
            case 0:
            {
                /*
                 * Draw the background
                 */
                g.setColor(context.getTheme().colorBackground);
                g.fillRect(x, y, getWidth(), getHeight());

                /*
                 * Now draw the text
                 */
                text.drawRelative(g, x, y, zOrder);

                /*
                 * Draw the stripe
                 */
                final Theme theme = context.getTheme();
                final int y_ = y + getHeight() - theme.sectionStripeHeight;
                final int w_ = getWidth();
                final int h_ = theme.sectionStripeHeight;

                g.setColor(theme.colorAccent);
                g.fillRect(x, y_, w_, h_);
                break;
            }

            case 1:
            {
                shadow.drawRelative(g, x, y, zOrder);
                break;
            }
        }
    }

    public int getClipHeight(int zOrder) {
        return zOrder < 1
                ? getHeight()
                : getHeight() + shadow.getHeight();
    }

    public final void setText(final String text) {
        setText(text == null ? null : text.toCharArray());
    }

    public final void setText(char[] text) {
        if (text != null) {
            text = AlbiteCharacter.toUpperCase(text);
        }

        this.text.setText(text);
    }

    public void setDebugMode(boolean enabled) {
        super.setDebugMode(enabled);
        text.setDebugMode(enabled);
        shadow.setDebugMode(enabled);
    }

    public void dump(int level) {
        super.dump(level);
        text.dump(level + 1);
        shadow.dump(level + 1);
    }
}

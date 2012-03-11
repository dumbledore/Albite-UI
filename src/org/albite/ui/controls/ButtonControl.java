/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.Theme;
import org.albite.ui.controls.layout.AutoSizeControl;
import org.albite.ui.controls.layout.VerticalLayout;
import org.albite.ui.core.callbacks.ClickCallback;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class ButtonControl extends Control {

    private AutoSizeControl text = new AutoSizeControl(this, context);
    private VerticalLayout  textLayout = new VerticalLayout(text, context);
    private TextControl     caption = new TextControl(textLayout, context);
    private TextControl     subCaption = new TextControl(textLayout, context);

    private AutoSizeControl icon = new AutoSizeControl(this, context);
    private ImageControl    iconImage = new ImageControl(icon, context);

    private AutoSizeControl arrow = new AutoSizeControl(this, context);
    private ImageControl    arrowImage = new ImageControl(arrow, context);

    private boolean pressed = false;
    private ClickCallback callback;

    public ButtonControl(final Control parent, final Context context) {
        super(parent, context);

        final Theme theme = context.getTheme();

        caption.setFont(theme.fontCaption);
        subCaption.setFont(theme.fontSubcaption);

        textLayout.addControl(caption);
        textLayout.addControl(subCaption);

        text.setMatchParentHeight(true);
        text.setControl(textLayout);

        icon.setMatchParentHeight(true);
        icon.setControl(iconImage);

        arrow.setMatchParentHeight(true);
        arrowImage.setImage(theme.iconNext);
        arrow.setControl(arrowImage);
    }

    public final void recompileMetrics(final boolean downTree) {
        setWidth(parent.getWidth());

        final Theme theme = context.getTheme();
        final int minHeight = theme.buttonMinimumHeight;

        final int iconWidth = theme.buttonIconWidth;
        final int arrowWidth = theme.buttonArrowWidth;
        final int textWidth = getWidth() - (iconWidth + arrowWidth);

        icon.setMinWidth(iconWidth);
        icon.setMaxWidth(iconWidth);
        icon.setX(0);

        text.setMinWidth(textWidth);
        text.setMaxWidth(textWidth);
        text.setX(iconWidth);

        arrow.setMinWidth(arrowWidth);
        arrow.setMaxWidth(arrowWidth);
        arrow.setX(iconWidth + textWidth);

        icon.invalidateDown();
        text.invalidateDown();
        arrow.invalidateDown();

        int height = Math.max(
                Math.max(iconImage.getHeight(), arrowImage.getHeight()),
                textLayout.getHeight());

        height = Math.max(minHeight, height);
        height += 2 * theme.listElementPadding + 2; /* padding + border */

        setHeight(height);

        /*
         * Recompile them a second time, this time to get the height
         */
        icon.recompileMetrics(false);
        text.recompileMetrics(false);
        arrow.recompileMetrics(false);
    }

    public final void setCaption(final String text) {
        caption.setText(text);
    }

    public final void setCaption(final char[] text) {
        caption.setText(text);
    }

    public final void setSubCaption(final String text) {
        subCaption.setText(text);
    }

    public final void setSubCaption(final char[] text) {
        subCaption.setText(text);
    }

    public final void setIcon(final Image icon) {
        iconImage.setImage(icon);
    }

    public void setCallback(final ClickCallback callback) {
        this.callback = callback;
    }

    public void draw(Graphics g, final int x, final int y) {
        g.setColor(pressed ? 0x1483CC : 0xF5F3F3);
        g.fillRect(x, y, getWidth(), getHeight());
        
        icon.drawRelative(g, x, y);
        text.drawRelative(g, x, y);
        arrow.drawRelative(g, x, y);

        g.setColor(0xADAAAD);
        g.fillRect(x, y + getHeight() - 1, getWidth(), 1);
    }

    public void lostFocus() {
        pressed = false;
        requestDraw(false);
    }

    public void pressed(int x, int y) {
        pressed = true;
        requestDraw(false);
    }

    public void dragged(int x, int y) {
        final boolean pressedNew = contains(x, y) ? true : false;
        if (pressedNew != pressed) {
            pressed = pressedNew;
            requestDraw(false);
        }
    }

    public void released(int x, int y) {
        if (pressed && callback != null) {
            callback.clicked(this);
        }

        pressed = false;
        requestDraw(false);
    }

    public void dump(final int level) {
        super.dump(level);
        icon.dump(level + 1);
        text.dump(level + 1);
        arrow.dump(level + 1);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.activity;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.ImageControl;
import org.albite.ui.controls.TextControl;
import org.albite.ui.controls.layout.AutoSizeControl;
import org.albite.ui.controls.layout.VerticalLayout;
import org.albite.ui.Context;

/**
 *
 * @author albus
 */
public final class Button extends Control {

    private AutoSizeControl text = new AutoSizeControl();
    private VerticalLayout  textLayout = new VerticalLayout();
    private TextControl     caption = new TextControl();
    private TextControl     subCaption = new TextControl();

    private AutoSizeControl icon = new AutoSizeControl();
    private ImageControl    iconImage = new ImageControl();

    private AutoSizeControl arrow = new AutoSizeControl();
    private ImageControl    arrowImage = new ImageControl();

    private boolean pressed = false;
    private ClickCallback callback;

    public void initialize(final Control parent, final Context context) {
        super.initialize(parent, context);

        text.initialize(this, context);
        icon.initialize(this, context);
        arrow.initialize(this, context);

        final Theme theme = context.getTheme();

        caption.setFont(theme.fontCaption);
        subCaption.setFont(theme.fontSubcaption);

        text.setMatchParentHeight(true);
        text.setControl(textLayout);

        icon.setMatchParentHeight(true);
        icon.setControl(iconImage);

        textLayout.addControl(caption);
        textLayout.addControl(subCaption);

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

        icon.setWidth(iconWidth);
        icon.setX(0);

        text.setWidth(textWidth);
        text.setX(iconWidth);

        arrow.setWidth(arrowWidth);
        arrow.setX(iconWidth + textWidth);

        icon.invalidateDown();
        text.invalidateDown();
        arrow.invalidateDown();

        int height = Math.max(
                Math.max(iconImage.getHeight(), arrowImage.getHeight()),
                textLayout.getHeight());

        height = Math.max(minHeight, height);
        height += 2 * theme.listElementPadding + theme.listElementBorder;

        setHeight(height);

        /*
         * Recompile them a second time, this time to get the height
         */
        icon.recompileMetrics(false);
        text.recompileMetrics(false);
        arrow.recompileMetrics(false);

        /*
         * Don't forget to set the color of the text elements.
         */
        caption.setColor(theme.colorText);
        subCaption.setColor(theme.colorText);
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

    public void draw(Graphics g,
            final int x, final int y, final int zOrder) {

        if (zOrder > 0) {
            return;
        }

        final Theme theme = context.getTheme();
        final int width = getWidth();
        final int height = getHeight();

        g.setColor(pressed ? theme.colorButtonPressed : theme.colorButtonNormal);
        g.fillRect(x, y, width, height);

        if (!pressed) {
            g.setColor(theme.colorButtonNormalShine);
            g.fillRect(x, y, width, theme.buttonShineHeight);
        }
        
        icon.drawRelative(g, x, y, 0);
        text.drawRelative(g, x, y, 0);
        arrow.drawRelative(g, x, y, 0);

        drawBottomBorder(
                g, y, theme.listElementBorder, theme.colorListElementBorder);
    }

    public boolean isFocusable() {
        return true;
    }

    public void gainedFocus() {
        pressed = true;
        requestDraw(false);
    }

    public void lostFocus() {
        pressed = false;
        requestDraw(false);
    }

    public void pressed(int x, int y) {
        gainedFocus();
    }

    public void dragged(int x, int y) {
        final boolean pressedNew = contains(x, y) ? true : false;
        if (pressedNew != pressed) {
            // This is done in order to avoid
            // redundant calls to requestDraw()
            pressed = pressedNew;
            requestDraw(false);
        }
    }

    public void released(int x, int y) {
        if (pressed && callback != null) {
            callback.clicked(this);
        }

        lostFocus();
    }

    public void setDebugMode(boolean enabled) {
        super.setDebugMode(enabled);

        icon.setDebugMode(enabled);
        text.setDebugMode(enabled);
        arrow.setDebugMode(enabled);
    }

    public void dump(final int level) {
        super.dump(level);
        icon.dump(level + 1);
        text.dump(level + 1);
        arrow.dump(level + 1);
    }
}

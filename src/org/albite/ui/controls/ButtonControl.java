/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.Theme;
import org.albite.ui.controls.layout.AlignControl;
import org.albite.ui.controls.layout.VerticalLayout;
import org.albite.ui.core.callbacks.ClickCallback;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class ButtonControl extends Control {

    private AlignControl    text = new AlignControl(this, context);
    private VerticalLayout  textLayout = new VerticalLayout(text, context);
    private TextControl     caption = new TextControl(textLayout, context);
    private TextControl     subCaption = new TextControl(textLayout, context);

    private AlignControl    icon = new AlignControl(this, context);
    private ImageControl    iconImage = new ImageControl(icon, context);

    private AlignControl    arrow = new AlignControl(this, context);
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
        text.addControl(textLayout);

        icon.addControl(iconImage);
        arrowImage.setImage(theme.iconNext);
        arrow.addControl(arrowImage);
    }

    public final void recompileMetrics() {
        width = parent.getWidth();
        final Theme theme = context.getTheme();
        height = theme.listElementHeight;
        padding = theme.listElementPadding;

        /*
         * 
         */

//        text.setWidth(width - 100);
//        text.setHeight(height);
//        text.setX(50);
//
//        height = this.text.getHeight();
    }

    public final void setCaption(final String text) {
        setCaption(text.toCharArray());
    }

    public final void setCaption(final char[] text) {
        caption.setText(text);
    }

    public void setCallback(final ClickCallback callback) {
        this.callback = callback;
    }

    public void draw(Graphics g, final int x, final int y) {
        g.setColor(pressed ? 0xFF0000 : 0x0000FF);
        g.fillRect(x, y, width, height);
//        g.drawImage(
//                pressed ? imagePressed : image,
//                x, y, Graphics.TOP | Graphics.LEFT);
        text.drawRelative(g, x, y);
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
}

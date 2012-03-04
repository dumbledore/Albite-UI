/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.screen.list;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.TextControl;
import org.albite.ui.controls.layout.AlignControl;
import org.albite.ui.controls.layout.LayoutControl;
import org.albite.ui.core.callbacks.ClickCallback;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class ButtonControl extends ListControl {

    private TextControl caption = new TextControl(this, context);

    private AlignControl text = new AlignControl(this, context);
    private AlignControl icon = new AlignControl(this, context);
    private AlignControl arrow = new AlignControl(this, context);

    private boolean pressed = false;
    private ClickCallback callback;

    public ButtonControl(final Control parent, final Context context) {
        super(parent, context);
    }

    public final void recompileMetrics() {
        height = Theme.BEST_BUTTON_ICON_SIZE;

        text.setWidth(width - 100);
        text.setHeight(height);
        text.setX(50);
//        final TextControl textC = new TextControl("Button", font, this.text, context);
        text.addControl(text);

        height = this.text.getHeight();
    }

    public final void setCaption(final String text) {
        setCaption(text.toCharArray());
    }

    public final void setCaption(final char[] text) {
        ((TextControl) this.text.getControl()).setText(text);
    }

    public void setCallback(final ClickCallback callback) {
        this.callback = callback;
    }

    public void draw(Graphics g, final int x, final int y) {
        g.setClip(x, y, width, height);
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

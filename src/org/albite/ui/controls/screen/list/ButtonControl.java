/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.screen.list;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.font.NativeFont;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.TextControl;
import org.albite.ui.controls.layout.AlignControl;
import org.albite.ui.core.callbacks.ClickCallback;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class ButtonControl extends ListControl {

    private final Image image;
    private final Image imagePressed;
    private final AlignControl text;

    private boolean pressed = false;
    private ClickCallback callback;

    private final org.albite.font.Font font =
            new NativeFont(Font.getDefaultFont());

    public ButtonControl(final Control parent, final Context context) {
        super(parent, context);

        image = loadImage("/res/button.png");
        imagePressed = loadImage("/res/button-pressed.png");

        height = image.getHeight();

        this.text = new AlignControl(this, context);
        this.text.setWidth(width - 100);
        this.text.setHeight(height);
        this.text.setX(50);
        final TextControl text = new TextControl("Button", font, this.text, context);
        this.text.addControl(text);

        height = this.text.getHeight();
    }

    public void setCallback(final ClickCallback callback) {
        this.callback = callback;
    }

    public void draw(Graphics g, final int x, final int y) {
        g.setClip(x, y, width, height);
        g.drawImage(
                pressed ? imagePressed : image,
                x, y, Graphics.TOP | Graphics.LEFT);
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

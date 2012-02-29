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
public class ButtonControl extends AlbiteControl {

    private final Image image;
    private final Image imagePressed;

    private boolean pressed = false;
    private ButtonCallback callback;

    public ButtonControl(final LayoutControl parent, final Context context) {
        super(parent, context);

        image = loadImage("/res/button.png");
        imagePressed = loadImage("/res/button-pressed.png");

        width = image.getWidth();
        height = image.getHeight();
    }

    public void setCallback(final ButtonCallback callback) {
        this.callback = callback;
    }

    public void invalidate() {
        //TODO
    }

    public void draw(Graphics g) {
        g.drawImage(
                pressed ? imagePressed : image,
                x, y, Graphics.TOP | Graphics.LEFT);
    }

    public void pressed(int x, int y) {
        pressed = true;
        requestDraw(false);
    }

    public void dragged(int x, int y) {
        final boolean pressedNew = contains(x + this.x, y + this.y) ? true : false;
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

    public interface ButtonCallback {
        public void clicked(ButtonControl control);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls.layout;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.ImageControl;
import org.albite.ui.core.callbacks.ClickCallback;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class ScreenMenu extends ContainerControl {

    public void recompileMetricsFromParent(final boolean downTree) {
        if (controls.isEmpty()) {
            return;
        }

        final int size = controls.size();
        final int buttonWidth = getWidth() / controls.size();
        final int buttonWidthRemainder = getWidth() % controls.size();

        Control control;
        for (int i = 0, x = 0; i < size - 1; i++, x += buttonWidth) {
            control = (MenuButton) controls.elementAt(i);
            control.setWidth(buttonWidth);
            control.setX(x);
        }

        /*
         * Add the remaining width to the last control
         */
        control = (Control) controls.lastElement();
        control.setWidth(buttonWidth + buttonWidthRemainder);
    }

    public void recompileMetricsFromChildren(final boolean downTree) {
        final int size = controls.size();
        int height = -1;

        Control control;

        for (int i = 0; i < size; i++) {
            control = (Control) controls.elementAt(i);
            height = Math.max(height, control.getHeight());
        }

        final Theme theme = context.getTheme();
        final int minHeight = theme.menuButtonsHeight;

        height = Math.max(minHeight, height);
        height += 2 * theme.listElementMinimumHeight;

        setHeight(height);

        /*
         * Recompile them a second time, this time to get the height
         */
        for (int i = 0; i < size; i++) {
            control = (Control) controls.elementAt(i);
            control.recompileMetrics(false);
        }
    }

    public static final class MenuButton extends Control {
        final AutoSizeControl control = new AutoSizeControl();
        final ImageControl iconImage = new ImageControl();

        boolean pressed = false;
        private ClickCallback callback;

        public void initialize(final Control parent, final Context context) {
            super.initialize(parent, context);

            control.initialize(this, context);
            control.setControl(iconImage);
        }

        public final void setIcon(final Image icon) {
            iconImage.setImage(icon);
        }
        
        public void setCallback(final ClickCallback callback) {
            this.callback = callback;
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

        public void draw(Graphics g, final int x, final int y) {
            final Theme theme = context.getTheme();

            g.setColor(pressed ? theme.colorButtonPressed : theme.colorButtonNormal);
            g.fillRect(x, y, getWidth(), getHeight());

            if (!pressed) {
                g.setColor(theme.colorButtonNormalShine);
                g.fillRect(x, y, getWidth(), 5);
            }

            control.drawRelative(g, x, y);

            g.setColor(theme.colorButtonBorder);
            g.fillRect(x, y + getHeight() - 1, getWidth(), 1);
        }

        public int getX() {
            return control.getX();
        }

        public void setX(int x) {
            control.setX(x);
        }

        public int getY() {
            return control.getY();
        }

        public void setY(int y) {
            control.setY(y);
        }

        public int getWidth() {
            return control.getWidth();
        }

        public void setWidth(int width) {
            control.setMinWidth(width);
            control.setMaxWidth(width);
        }

        public int getHeight() {
            return control.getHeight();
        }

        public void setHeight(int height) {
            control.setMinHeight(height);
            control.setMaxHeight(height);
        }

        public void invalidateDown() {
            control.recompileMetrics(true);
        }

        public void recompileMetrics(boolean downTree) {
            control.recompileMetrics(downTree);
        }

        public boolean contains(final int x, final int y) {
            return control.contains(x, y);
        }

        public void dump(final int level) {
            super.dump(level);
            control.dump(level + 1);
        }
    }
}

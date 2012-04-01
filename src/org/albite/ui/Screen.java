/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

import javax.microedition.lcdui.Graphics;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.ImageControl;
import org.albite.ui.controls.layout.AdapterControl;
import org.albite.ui.controls.layout.ContainerControl;
import org.albite.ui.controls.screen.ScreenMenu;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class Screen extends ContainerControl {

    private final ImageControl background = new ImageControl();
    private final AdapterControl adapter = new AdapterControl();
    private final ScreenMenu menu = new ScreenMenu();

    public Screen(final String title, final Context context) {
        this.context = context;

        background.initialize(this, context);
        background.setImage(context.getTheme().imageBackground);
        background.setRepeat(true);
        background.setWidth(context.getWidth());
        background.setHeight(context.getHeight());

        /*
         * Title: TODO
         */
        
        /*
         * Content
         */
        addControl(adapter);

        /*
         * Menu
         */
        addControl(menu);
    }

    public int getWidth() {
        return context.getWidth();
    }

    public int getHeight() {
        return context.getHeight();
    }

    public void recompileMetricsFromParent(boolean downTree) {
        if (downTree) {
            menu.invalidateDown();
        }

        /*
         * Set Title
         */

        /*
         * Set content
         */
        adapter.setWidth(getWidth());
        adapter.setHeight(getHeight() - menu.getHeight());

        /*
         * Set menu
         */
        menu.setY(adapter.getY() + adapter.getHeight());
    }

    public void setControl(final Control control) {
        adapter.setControl(control);
    }

    public ScreenMenu getMenu() {
        return menu;
    }

    public void dump(final int level) {
        super.dump(level);
        background.dump(level + 1);
    }

    public void draw(final Graphics g, 
            final int x, final int y, final int zOrder) {
        
        background.drawRelative(g, x, y, zOrder);
        super.draw(g, x, y, zOrder);
    }
}

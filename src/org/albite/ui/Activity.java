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
import org.albite.ui.controls.activity.Menu;

/**
 *
 * @author albus
 */
public abstract class Activity extends ContainerControl {

    private final ImageControl background = new ImageControl();
    private final AdapterControl adapter = new AdapterControl();
    private final Menu menu = new Menu();

    public Activity(final String title, final Context context) {
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

    /*
     * Activity life-cycle
     */
    private int cachedWidth = 0;
    private int cachedHeight = 0;

    /**
     * Called, when an activity is shown for the first time.
     * Implementing classes should call super.onCreate() last.
     *
     * TODO: Add persistence.
     */
    protected void onCreate() {
        invalidateDown();
    }

    /**
     * Called every time an activity is shown.
     */
    protected void onShow() {
        if (cachedWidth != getWidth() || cachedHeight != getHeight()) {
            invalidateDown();
            cachedWidth = getWidth();
            cachedHeight = getHeight();
        }
    }

    /**
     * Called when an activity goes out of the stack.
     *
     * TODO: Add persistence.
     */
    protected void onDestroy() {}

    /*
     * Control implementation
     */
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

        if (menu.isVisible()) {
            adapter.setHeight(getHeight() - menu.getHeight());
            adapter.invalidateDown();

            /*
             * Set menu
             */
            menu.setY(adapter.getY() + adapter.getHeight());
        } else {
            adapter.setHeight(getHeight());
            adapter.invalidateDown();
        }
    }

    public void setControl(final Control control) {
        adapter.setControl(control);
    }

    public Menu getMenu() {
        return menu;
    }

    public void dump(final int level) {
        super.dump(level);
        background.dump(level + 1);
    }

    public void draw(final Graphics g, 
            final int x, final int y, final int zOrder) {

        /*
         * Draw the background before other controls,
         * and don't draw it on later passes.
         */
        if (zOrder < 1) {
            background.drawRelative(g, x, y, zOrder);
        }

        super.draw(g, x, y, zOrder);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.org.albite.ui;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.albite.ui.Activity;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.Screen;
import org.albite.ui.controls.ButtonControl;
import org.albite.ui.controls.layout.VerticalLayout;
import org.albite.ui.core.callbacks.ClickCallback;

/**
 *
 * @author albus
 */
public class AlbiteUIMIDlet extends MIDlet {

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {}

    protected void pauseApp() {}

    protected void startApp() throws MIDletStateChangeException {

        final Activity activity = new Activity();
        activity.setTheme(Theme.getDayTheme(activity));

        final Screen screen = new Screen("Albite READER", activity);

        final VerticalLayout list = new VerticalLayout(screen, activity);
        screen.addControl(list);

        final ButtonControl button1 = new ButtonControl(list, activity);
        final ButtonControl button2 = new ButtonControl(list, activity);
        final ButtonControl button3 = new ButtonControl(list, activity);
        final ButtonControl button4 = new ButtonControl(list, activity);

        list.addControl(button1);
        list.addControl(button2);
        list.addControl(button3);
        list.addControl(button4);

        final ClickCallback onClick = new ClickCallback() {
            public void clicked(Control control) {
                System.out.println("Clicked " + control);
            }
        };

        button1.setCallback(onClick);
        button2.setCallback(onClick);
        button3.setCallback(onClick);
        button4.setCallback(onClick);

        screen.invalidateDown();
        screen.dump();

        activity.setScreen(screen);

        Display.getDisplay(this).setCurrent(activity);
    }
}

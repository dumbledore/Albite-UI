/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.org.albite.ui;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.albite.ui.AlbiteActivity;
import org.albite.ui.AlbiteScreen;
import org.albite.ui.controls.ButtonControl;
import org.albite.ui.controls.VerticalLayout;

/**
 *
 * @author albus
 */
public class AlbiteUIMIDlet extends MIDlet {

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        
    }

    protected void pauseApp() {
        
    }

    protected void startApp() throws MIDletStateChangeException {
        final AlbiteActivity activity = new AlbiteActivity();
        final AlbiteScreen screen = new AlbiteScreen("Albite READER", activity);
        final VerticalLayout layout = new VerticalLayout(screen, activity);

        final ButtonControl button1 = new ButtonControl(layout, activity);
        final ButtonControl button2 = new ButtonControl(layout, activity);
        final ButtonControl button3 = new ButtonControl(layout, activity);
        final ButtonControl button4 = new ButtonControl(layout, activity);

        final ButtonControl.ButtonCallback onClick = new ButtonControl.ButtonCallback() {

            public void clicked(ButtonControl control) {
                System.out.println("Clicked " + control);
            }
        };

        activity.setScreen(screen);

        screen.setContentLayout(layout);

        screen.addControl(button1);
        screen.addControl(button2);
        screen.addControl(button3);
        screen.addControl(button4);

        button1.setCallback(onClick);
        button2.setCallback(onClick);
        button3.setCallback(onClick);
        button4.setCallback(onClick);

        Display.getDisplay(this).setCurrent(activity);
    }
}

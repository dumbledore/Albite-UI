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
import org.albite.ui.controls.HorizontalLayout;
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

        final HorizontalLayout hLayout = new HorizontalLayout(screen, activity);
        final ButtonControl button5 = new ButtonControl(layout, activity);
        final ButtonControl button6 = new ButtonControl(layout, activity);
        final ButtonControl button7 = new ButtonControl(layout, activity);

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

        hLayout.addControl(button5);
        hLayout.addControl(button6);
        hLayout.addControl(button7);

        screen.addControl(hLayout);

        button1.setCallback(onClick);
        button2.setCallback(onClick);
        button3.setCallback(onClick);
        button4.setCallback(onClick);

        button5.setCallback(onClick);
        button6.setCallback(onClick);
        button7.setCallback(onClick);

        screen.dump();

        Display.getDisplay(this).setCurrent(activity);
    }
}

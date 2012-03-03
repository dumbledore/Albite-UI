/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.org.albite.ui;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.albite.font.NativeFont;
import org.albite.ui.Activity;
import org.albite.ui.controls.Control;
import org.albite.ui.controls.TextControl;
import org.albite.ui.controls.screen.Screen;
import org.albite.ui.controls.screen.list.ButtonControl;
import org.albite.ui.controls.screen.list.List;
import org.albite.ui.core.callbacks.ClickCallback;

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
        final Activity activity = new Activity();
        final Screen screen = new List("Albite READER", activity);

        final ButtonControl button1 = new ButtonControl(screen, activity);
        final ButtonControl button2 = new ButtonControl(screen, activity);
        final ButtonControl button3 = new ButtonControl(screen, activity);
        final ButtonControl button4 = new ButtonControl(screen, activity);

        final ClickCallback onClick = new ClickCallback() {
            public void clicked(Control control) {
                System.out.println("Clicked " + control);
            }
        };

        final NativeFont font = new NativeFont(Font.getDefaultFont());
        final TextControl text = new TextControl(
                "    Some nice bit of text  asdklasjdkljaskjdkjaskjdljasldjasjQ\r\r\nQkldjklasjdl kasjdkljkljlkjkljaskl\n\nj s",
                font, screen, activity);

        activity.setScreen(screen);

        screen.addControl(button1);
        screen.addControl(button2);
        screen.addControl(button3);
        screen.addControl(button4);
        screen.addControl(text);

        button1.setCallback(onClick);
        button2.setCallback(onClick);
        button3.setCallback(onClick);
        button4.setCallback(onClick);

        screen.dump();

        Display.getDisplay(this).setCurrent(activity);
    }
}

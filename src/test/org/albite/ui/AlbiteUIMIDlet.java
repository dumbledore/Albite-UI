/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.org.albite.ui;

import java.util.Vector;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.albite.ui.Activity;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.Screen;
import org.albite.ui.controls.ButtonControl;
import org.albite.ui.controls.layout.VerticalLayout;
import org.albite.ui.controls.layout.VerticalScrollLayout;
import org.albite.ui.core.callbacks.ClickCallback;

/**
 *
 * @author albus
 */
public class AlbiteUIMIDlet extends MIDlet {

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {}

    protected void pauseApp() {}

    Activity activity;
    Screen screen;
    VerticalScrollLayout scroll;
    VerticalLayout list;
    Vector buttons = new Vector();

    protected void startApp() throws MIDletStateChangeException {

        activity = new Activity();
        activity.setTheme(Theme.getDayTheme(activity));

        screen = new Screen("Albite READER", activity);

        scroll = new VerticalScrollLayout(screen, activity);
        screen.setControl(scroll);

        list = new VerticalLayout(scroll, activity);
        scroll.setControl(list);

        final Theme theme = activity.getTheme();

        addButton("Books", "Search for books", theme.iconBook);
        addButton("Authors", "Search for authors", theme.iconBook);
        addButton("Albite READER",
                "Download an e-book reader for Java Mobile", theme.iconBook);
        addButton("Beatrix Potter", null, theme.iconBook);
        addButton("Sir Arthur Conan Doyle", "Return to authors", theme.iconBook);
        addButton("The Voyage of Doctor Dolittle", "Return to Hugh Lofting", theme.iconBook);
        addButton(null, null, null);
        addButton(null, "Subcaption", null);
        addButton("Caption", "Subcaption", null);

        addButton("Beatrix Potter", null, theme.iconBook);
        addButton("Sir Arthur Conan Doyle", "Return to authors", theme.iconBook);
        addButton("The Voyage of Doctor Dolittle", "Return to Hugh Lofting", theme.iconBook);
        addButton(null, null, null);
        addButton(null, "Subcaption", null);
        addButton("Caption", "Subcaption", null);

        screen.invalidateDown();
//        screen.dump();

        activity.setScreen(screen);

        Display.getDisplay(this).setCurrent(activity);
    }

    final ClickCallback onClick = new ClickCallback() {
        public void clicked(Control control) {
            ButtonControl button = (ButtonControl) control;
            button.setSubCaption((char[]) null);
            button.invalidate();
        }
    };

    private void addButton(String caption, String subCaption, Image icon) {
        ButtonControl button = new ButtonControl(list, activity);
        button.setCaption(caption);
        button.setSubCaption(subCaption);
        button.setIcon(icon);
        button.setCallback(onClick);
        list.addControl(button);
        buttons.addElement(button);
    }
}

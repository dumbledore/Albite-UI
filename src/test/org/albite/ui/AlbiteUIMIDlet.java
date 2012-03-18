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
import org.albite.ui.controls.layout.ScreenMenu.MenuButton;
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
    
    VerticalScrollLayout scroll = new VerticalScrollLayout();
    VerticalLayout list = new VerticalLayout();
    Vector buttons = new Vector();

    protected void startApp() throws MIDletStateChangeException {

        activity = new Activity();

        final Theme theme = Theme.getDayTheme(activity);

        activity.setTheme(theme);

        screen = new Screen("Albite READER", activity);

        /*
         * Add menu buttons
         */
        addMenuButton(theme.iconMenuBack);
        addMenuButton(theme.iconMenuSearch);
        addMenuButton(theme.iconMenuMore);

        /*
         * Always call setControl / addControl from parent to children
         * as it initializes the connections.
         */
        screen.setControl(scroll);
        scroll.setControl(list);

        addListButton("Books", "Search for books", theme.iconBook);
        addListButton("Authors", "Search for authors", theme.iconBook);
        addListButton("Albite READER",
                "Download an e-book reader for Java Mobile", theme.iconBook);
        addListButton("Beatrix Potter", null, theme.iconBook);
        addListButton("Sir Arthur Conan Doyle", "Return to authors", theme.iconBook);
        addListButton("The Voyage of Doctor Dolittle", "Return to Hugh Lofting", theme.iconBook);
        addListButton(null, null, null);
        addListButton(null, "Subcaption", null);
        addListButton("Caption", "Subcaption", null);

        addListButton("Beatrix Potter", null, theme.iconBook);
        addListButton("Sir Arthur Conan Doyle", "Return to authors", theme.iconBook);
        addListButton("The Voyage of Doctor Dolittle", "Return to Hugh Lofting", theme.iconBook);
        addListButton(null, null, null);
        addListButton(null, "Subcaption", null);
        addListButton("Caption", "Subcaption", null);

        screen.invalidateDown();
//        screen.dump();

        activity.setScreen(screen);

        Display.getDisplay(this).setCurrent(activity);
    }

    final private ClickCallback listButtonOnClick = new ClickCallback() {
        public void clicked(Control control) {
            ButtonControl button = (ButtonControl) control;
            button.setSubCaption((char[]) null);
            button.invalidateUp();
        }
    };

    final private ClickCallback menuButtonOnClick = new ClickCallback() {
        public void clicked(Control control) {
            System.out.println("Clicked @ " + control);
        }
    };

    private void addListButton(String caption, String subCaption, Image icon) {
        ButtonControl button = new ButtonControl();
        list.addControl(button);

        /*
         * Don't call any methods on a control if it hasn't been added
         * to a parent of some kind or it's parent and context fields
         * wouldn't be initialized.
         */
        button.setCaption(caption);
        button.setSubCaption(subCaption);
        button.setIcon(icon);
        button.setCallback(listButtonOnClick);
        buttons.addElement(button);
    }

    private void addMenuButton(Image icon) {
        MenuButton button = new MenuButton();
        screen.getMenu().addControl(button);

        button.setCallback(menuButtonOnClick);
        button.setIcon(icon);
    }
}

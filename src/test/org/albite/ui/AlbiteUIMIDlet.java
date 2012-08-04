/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.org.albite.ui;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.albite.ui.ActivityStack;
import org.albite.ui.Theme;
import org.albite.ui.controls.Control;
import org.albite.ui.Activity;
import org.albite.ui.controls.Control.ClickCallback;
import org.albite.ui.controls.activity.Menu.MenuButton;
import org.albite.ui.controls.layout.VerticalLayout;
import org.albite.ui.controls.layout.VerticalScrollLayout;
import org.albite.ui.controls.activity.Button;
import org.albite.ui.controls.activity.Section;
import org.albite.ui.controls.activity.Text;

/**
 *
 * @author albus
 */
public class AlbiteUIMIDlet extends MIDlet
        implements ActivityStack.ExitCallback {

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {}

    protected void pauseApp() {}

    protected void startApp() throws MIDletStateChangeException {

        /*
         * Create the stack
         */
        final ActivityStack stack = new ActivityStack(this);

        /*
         * Set the day-light theme as the current one.
         */
        stack.setTheme(Theme.getDayTheme(stack));

        /*
         * Extend the Activity class with a concrete implementation
         * in onCreate() and onDestroy().
         */
        final Activity activity = new Activity("Albite READER", stack) {

            final private VerticalScrollLayout scroll = new VerticalScrollLayout();

            final private VerticalLayout list = new VerticalLayout();

            /*
             * When clicked, the button's subcaption would disappear.
             */
            final private ClickCallback clearSubcaptionTextOnClick = new ClickCallback() {
                public void clicked(Control control) {
                    Button button = (Button) control;

                    button.setSubCaption((char[]) null);
                    button.invalidateUp();
                }
            };

            final private ClickCallback hideControlOnClick = new ClickCallback() {
                public void clicked(Control control) {
                    control.setVisible(false);
                    control.invalidateUp();
                }
            };

            final private ClickCallback toggleDebugModeOnClick = new ClickCallback() {
                public void clicked(Control control) {
                    control.setDebugMode(!control.getDebugMode());
                    context.redraw(false);
                }
            };

            final private ClickCallback menuButtonOnClick = new ClickCallback() {
                public void clicked(Control control) {
                    System.out.println("Clicked @ " + control);

                    /*
                     * Hide the menu
                     */
                    getMenu().setVisible(false);
                    invalidateUp();
                }
            };

            protected void onCreate() {
                /*
                 * Get the used theme.
                 */
                final Theme theme = getContext().getTheme();

                /*
                 * Add the menu buttons
                 */
                getMenu().addBackMenuButon();
                addMenuButton(theme.iconMenuSearch);
                addMenuButton(theme.iconMenuMore);

                /*
                 * Always call setControl / addControl from parent to children
                 * as it initializes the connections.
                 */
                setControl(scroll);
                scroll.setControl(list);

                addSection("Albite Books").setDebugMode(true);
                addListButton("Books", "Search for books", theme.iconBook);
                addListButton("Authors", "Search for authors", theme.iconBook);

                addSection("E-book reader for Java Mobile?");
                addText("If you don't have an e-book reader on your "
                        + "Java Mobile phone, click on the download button "
                        + "and get one!");
                addListButton("Albite READER",
                        "Download an e-book reader for Java Mobile", theme.iconBook);
                addListButton("Beatrix Potter", null, theme.iconBook);

                addSection("Free e-books?");
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

                /*
                 * Call parent's on create last so that it would
                 * cause an invalidation of the layout.
                 */
                super.onCreate();
            }

            private MenuButton addMenuButton(Image icon) {
                MenuButton button = new MenuButton();
                getMenu().addControl(button);
                button.setCallback(menuButtonOnClick);
                button.setIcon(icon);

                return button;
            }

            private Button addListButton(String caption, String subCaption, Image icon) {
                Button button = new Button();
                list.addControl(button);

                /*
                 * Don't call any methods on a control if it hasn't been added
                 * to a parent of some kind or it's parent and context fields
                 * wouldn't be initialized.
                 */
                button.setCaption(caption);
                button.setSubCaption(subCaption);
                button.setIcon(icon);
                button.setCallback(toggleDebugModeOnClick);

                return button;
            }

            private Text addText(String text) {
                Text control = new Text();
                list.addControl(control);
                control.setText(text);
                return control;
            }

            private Section addSection(String text) {
                Section control = new Section();
                list.addControl(control);
                control.setText(text);
                return control;
            }
        };

        /*
         * Set this as the current activity of the stack.
         */
        stack.setActivity(activity);

        Display.getDisplay(this).setCurrent(stack);
    }

    public void onExit() {
        notifyDestroyed();
    }
}

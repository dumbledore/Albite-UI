/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui;

/**
 *
 * @author albus
 */
public interface ActivityManager {
    Activity getCurrentActivity();
    void setActivity(Activity activity);
    void goToPreviousActivity();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.core;

import java.io.InputStream;
import javax.microedition.lcdui.Image;

/**
 *
 * @author albus
 */
public interface ResourceManager {
    Image getImage(String path);
    InputStream getStream(String path);
    String getLocalisedString(String key);
}

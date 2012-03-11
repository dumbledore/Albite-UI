/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.ui.controls;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import org.albite.font.Font;
import org.albite.ui.core.interfaces.Context;

/**
 *
 * @author albus
 */
public class TextControl extends Control {

    char[] text;
    Line[] lines;
    Font font;
    int color = 0x0;

    public TextControl(final Control parent, final Context context) {
        super(parent, context);
    }

    public final void recompileMetrics(final boolean downTree) {
        if (text == null) {
            lines = null;
            return;
        }

        setWidth(parent.getWidth());
        final LineBuilder builder = new LineBuilder(text, font, getWidth());
        builder.build();
        lines = builder.getLines();
        setHeight(lines.length * font.getLineHeight());
    }

    public final void setColor(final int color) {
        this.color = color;
    }

    public final char[] getText() {
        return text;
    }

    public final void setText(final String text) {
        if (text == null) {
            return;
        }

        setText(text.toCharArray());
    }

    public final void setText(final char[] text) {
        if (text == null) {
            return;
        }

        this.text = text;
        lines = new Line[] {new Line(0, text.length)}; //force it to 1 line
        setHeight(font.getLineHeight());
    }

    public final Font getFont() {
        return font;
    }

    public final void setFont(Font font) {
        this.font = font;
    }

    private static final int MINIMUM_WIDTH = 20;

    private class LineBuilder {
        private final char[] text;
        private final Font font;
        private final int width;

        private boolean processed = false;
        private Line[] lines;

        private int currentPosition = 0;
        private int startPosition = 0;
        private int breakPosition = 0;
        private int lineWidth = 0;
        private boolean previousWasSpace = false;
        private boolean textStarted = false;

        LineBuilder(final char[] text, final Font font, final int width) {
            if (width < MINIMUM_WIDTH) {
                throw new IllegalArgumentException("Width's so small no text "
                        + "would fit there: " + width);
            }

            this.text = text;
            this.font = font;
            this.width = width;
        }

        Line[] getLines() {
            return lines;
        }

        private void newLine() {
            lineWidth = 0;
            breakPosition = currentPosition;
            startPosition = currentPosition;
            previousWasSpace = false;
            textStarted = false;
        }

        void build() {
            if (processed) {
                return;
            }
            
            /* temporary */
            final char[] textLocal = text;
            final int textLocalLength = text.length;
            final int textLocalLength1 = textLocalLength - 1;

            Vector bucket = new Vector();
            char c;

            while(currentPosition < textLocalLength) {
                c = textLocal[currentPosition];

                switch (c) {
                    case '\r':
                    {
                        if (currentPosition < textLocalLength1
                                && textLocal[currentPosition + 1] == '\n') {
                            currentPosition++;
                        }
                    }
                    case '\n':
                    {
                        bucket.addElement(new Line(
                                startPosition, currentPosition - startPosition));
                        currentPosition++;
                        newLine();
                        break;
                    }

                    case ' ':
                    case '\t':
                    {
                        if (!textStarted) {
                            startPosition++;
                            breakPosition++;
                        } else {
                            if (!previousWasSpace) {
                                breakPosition = currentPosition;
                            }

                            lineWidth += font.charWidth(c);
                        }

                        previousWasSpace = true;
                        currentPosition++;
                        break;
                    }

                    default:
                    {
                        textStarted = true;

                        lineWidth += font.charWidth(c);
                        if (lineWidth > width) {
                            /*
                             * Need to break
                             */
                            if (startPosition == breakPosition) {
                                /*
                                 * That's too long a word. Break it right here
                                 */
                                bucket.addElement(
                                        new Line(startPosition,
                                        currentPosition - startPosition));
                                currentPosition++;
                            } else {
                                bucket.addElement(
                                        new Line(startPosition,
                                        breakPosition - startPosition));
                                
                                currentPosition = breakPosition;
                            }

                            newLine();
                        } else {
                            currentPosition++;
                        }
                        previousWasSpace = false;
                        break;
                    }
                }
            }

            if (startPosition != textLocalLength - 1)  {
                bucket.addElement(
                        new Line(startPosition,
                        textLocalLength - startPosition));
            }

            lines = new Line[bucket.size()];
            bucket.copyInto(lines);

            processed = true;
        }
    }

    protected void draw(Graphics g, int x, int y) {
        if (lines == null) {
            return;
        }

        final int fontHeight = font.getLineHeight();
        
        Line line;

        for (int i = 0; i < lines.length; i++) {
            line = lines[i];
            font.drawChars(g, color, x, y, text, line.position, line.length);
            y += fontHeight;
        }
    }

    public void pressed(int x, int y) {}

    public void dragged(int x, int y) {}

    public void released(int x, int y) {}

    class Line {
        int position;
        int length;

        Line(int position, int length) {
            this.position = position;
            this.length = length;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.albite.core.character;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author albus
 */
public class AlbiteCharacter {

    private static final String CHARMAP_PATH =
            "/res/raw/char/charmap.bin";

    public static char toLowerCase(final char ch) {
        final int val = A[Y[(X[ch>>6]<<5)|((ch>>1)&0x1F)]|(ch&0x1)];

        if ((val & 0x00200000) != 0) {
          return (char)(ch + (val >> 22));
        } else {
          return ch;
        }
    }

    public static char[] toLowerCase(final char[] ch) {
        final char[] res = new char[ch.length];
        for (int i = 0; i < ch.length; i++) {
            res[i] = toLowerCase(ch[i]);
        }
        return res;
    }

    public static String toLowerCase(final String s) {
        return new String(toLowerCase(s.toCharArray()));
    }

    public static char toUpperCase(char ch) {
        int val = A[Y[(X[ch>>6]<<5)|((ch>>1)&0x1F)]|(ch&0x1)];
        if ((val & 0x00100000) != 0) {
            return (char)(ch - (val >> 22));
        }  else {
            return ch;
        }
    }

    public static char[] toUpperCase(final char[] ch) {
        final char[] res = new char[ch.length];
        for (int i = 0; i < ch.length; i++) {
            res[i] = toUpperCase(ch[i]);
        }
        return res;
    }

    public static String toUpperCase(final String s) {
        return new String(toUpperCase(s.toCharArray()));
    }

    public static boolean isLetterOrDigit(final char ch) {
        return (((((1 << UPPERCASE_LETTER) |
                   (1 << LOWERCASE_LETTER) |
                   (1 << TITLECASE_LETTER) |
                   (1 << MODIFIER_LETTER) |
                   (1 << OTHER_LETTER) |
                   (1 << DECIMAL_DIGIT_NUMBER))
                  >> (A[Y[(X[ch>>6]<<5)|((ch>>1)&0x1F)]|(ch&0x1)] & 0x1F)) & 1) != 0);
    }

    public static boolean isLetter(final char ch) {
        return (((((1 << UPPERCASE_LETTER) |
                   (1 << LOWERCASE_LETTER) |
                   (1 << TITLECASE_LETTER) |
                   (1 << MODIFIER_LETTER) |
                   (1 << OTHER_LETTER))
                  >> (A[Y[(X[ch>>6]<<5)|((ch>>1)&0x1F)]|(ch&0x1)] & 0x1F)) & 1) != 0);
    }

    public static boolean isDigit(final char ch) {
        return (A[Y[(X[ch>>6]<<5)|((ch>>1)&0x1F)]|(ch&0x1)] & 0x1F) == DECIMAL_DIGIT_NUMBER;
    }

    public static int getType(final char ch) {
        return A[Y[(X[ch>>6]<<5)|((ch>>1)&0x1F)]|(ch&0x1)] & 0x1F;
    }

    public static int compareCharArrays(
            final char[] c1, final int c1Offset, final int c1Len,
            final char[] c2, final int c2Offset, final int c2Len) {

        /* we need the smallest range */
        int search_range = Math.min(c1Len, c2Len);

        for (int i = 0; i < search_range; i++) {
            char c1x = c1[i+c1Offset];
            char c2x = c2[i+c2Offset];

            if (c1x == c2x) {
                /* the two words still match */
                continue;
            }

            if (c1x < c2x) {
                 /* c1 is before */
                return -1;
            }

            /* c1 is after */
            return 1;
        }

        /*
         * Scanned all common chars
         */
        if (c1Len == c2Len) {
            /*  the same */
            return 0;
        }

        if (c1Len < c2Len) {
            /* c1 is before */
            return -1;
        }

        /* c1 is after */
        return 1;
    }

    public static boolean equalsCharArrays(
            final char[] c1, final int c1Offset, final int c1Len,
            final char[] c2, final int c2Offset, final int c2Len) {

        for (int i = 0; i < c1Len; i++) {
            if (c1[i + c1Offset] != c2[i + c2Offset]) {
                /* the two words still match */
                return false;
            }
        }

        return true;
    }

    private static final byte[]     X = new byte[1024];
    private static final short[]    Y = new short[4032];
    private static final int[]      A = new int[632];

    static {
        /*
         * Load data from external file
         */
        InputStream is = AlbiteCharacter.class
                .getResourceAsStream(CHARMAP_PATH);

        if (is != null) {
            DataInputStream in = new DataInputStream(is);
            try {
                try {
                    for (int i = 0; i < 1024; i++) {
                        X[i] = in.readByte();
                    }

                    for (int i = 0; i < 4032; i++) {
                        Y[i] = in.readShort();
                    }

                    for (int i = 0; i < 632; i++) {
                        A[i] = in.readInt();
                    }
                } finally {
                    in.close();
                }
            } catch (IOException e) {}
        }
    }

    private static final byte
	UNASSIGNED		= 0,
	UPPERCASE_LETTER	= 1,
	LOWERCASE_LETTER	= 2,
	TITLECASE_LETTER	= 3,
	MODIFIER_LETTER		= 4,
	OTHER_LETTER		= 5,
	NON_SPACING_MARK	= 6,
	ENCLOSING_MARK		= 7,
	COMBINING_SPACING_MARK	= 8,
	DECIMAL_DIGIT_NUMBER	= 9,
	LETTER_NUMBER		= 10,
	OTHER_NUMBER		= 11,
	SPACE_SEPARATOR		= 12,
	LINE_SEPARATOR		= 13,
	PARAGRAPH_SEPARATOR	= 14,
	CONTROL			= 15,
	FORMAT			= 16,
	PRIVATE_USE		= 18,
	SURROGATE		= 19,
	DASH_PUNCTUATION	= 20,
	START_PUNCTUATION	= 21,
	END_PUNCTUATION		= 22,
	CONNECTOR_PUNCTUATION	= 23,
	OTHER_PUNCTUATION	= 24,
	MATH_SYMBOL		= 25,
	CURRENCY_SYMBOL		= 26,
	MODIFIER_SYMBOL		= 27,
	OTHER_SYMBOL		= 28;
}

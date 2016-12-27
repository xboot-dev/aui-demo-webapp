/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util;

import java.util.regex.Pattern;

/**
 * String Utils
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class StringUtils {

    public static final String STRING_EMPTY = "";

    public static boolean isNullOrEmpty(final String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean containsWhitespace(final String s) {
        return Pattern.compile("\\s").matcher(s).find();
    }
}
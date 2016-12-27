/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template;

/**
 * Template Rendering exception
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@SuppressWarnings("serial")
public class RenderingException extends RuntimeException {

	public RenderingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RenderingException(final String message) {
        super(message);
    }

    public RenderingException(final Throwable cause) {
        super(cause);
    }
}
/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Template Renderer interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface TemplateRenderer {

    /**
     * Renders the template to the writer, using the given context.
     *
     * @param templateName file name of the template to render
     * @param context Map of objects to make available in the template rendering process
     * @param writer where to write the rendered template
     * @throws RenderingException thrown if there is an internal exception when rendering the template
     * @throws IOException thrown if there is a problem reading the template file or writing to the writer
     */
    void render(
    		final String templateName,
    		final Map<String, Object> context,
    		final Writer writer) throws RenderingException, IOException;

}
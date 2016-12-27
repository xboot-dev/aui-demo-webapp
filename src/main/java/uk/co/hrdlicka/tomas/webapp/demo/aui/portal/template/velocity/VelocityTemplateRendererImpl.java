/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.RenderingException;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.TemplateRenderer;

/**
 * Velocity Template Renderer class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class VelocityTemplateRendererImpl implements TemplateRenderer {
	private static final Logger log = Logger.getLogger(VelocityTemplateRendererImpl.class);

	private final VelocityEngine velocity;

	public VelocityTemplateRendererImpl(
			final ServletContext servletContext,
			final Map<String, String> properties) {

		velocity = new VelocityEngine();

		/*
		 * You will also need to put the ServletContext into your VelocityEngine's application attributes
		 * before initializing that Engine. This is how the WebappResourceLoader knows how to find templates.
		 * http://velocity.apache.org/engine/devel/webapps.html
		 */
		velocity.setApplicationAttribute("javax.servlet.ServletContext", servletContext);

		/*
		 * Atlassian Template Renderer Plugin
		 * https://bitbucket.org/atlassian/atlassian-template-renderer
		 */

		overrideProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, org.apache.velocity.runtime.log.CommonsLogLogChute.class.getName());
		overrideProperty(Velocity.RESOURCE_LOADER, "classpath, webapp");
        overrideProperty("classpath.resource.loader.class", org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader.class.getName());
        overrideProperty("classpath.resource.loader.cache", Boolean.toString(!Boolean.getBoolean("portal.dev.mode")));
        overrideProperty("webapp.resource.loader.class", org.apache.velocity.tools.view.WebappResourceLoader.class.getName());
        overrideProperty("webapp.resource.loader.path", "/WEB-INF/templates/velocity");
        overrideProperty("webapp.resource.loader.cache", false);

        if (properties != null) {
        	for (Map.Entry<String, String> prop : properties.entrySet()) {
            	overrideProperty(prop.getKey(), prop.getValue());
            }
        }

        try {
            velocity.init();

        } catch (final Exception ex) {
        	throw new RuntimeException(ex);
        }
	}

	public void render(
			final String templateName,
			final Map<String, Object> context,
			final Writer writer) throws RenderingException, IOException {

        try {
        	Template template = velocity.getTemplate(templateName);
        	template.merge(createContext(context), writer);
            writer.flush();

        } catch (final IOException ex) {
            throw ex;

        } catch (final Exception ex) {
            throw new RenderingException(ex);

        } finally {
        }
	}

    protected VelocityContext createContext(final Map<String, Object> contextParams) {
        final VelocityContext velocityContext = new VelocityContext();

        if (contextParams != null) {
        	for (Map.Entry<String, Object> param : contextParams.entrySet()) {
        		velocityContext.put(param.getKey(), param.getValue());
        	}
        }

        return velocityContext;
    }

	protected void overrideProperty(final String key, final Object value) {
    	if (key.equals("userdirective")) {
    		velocity.addProperty(key, value);

    	} else {
    		velocity.setProperty(key, value);
    	}
    }
}
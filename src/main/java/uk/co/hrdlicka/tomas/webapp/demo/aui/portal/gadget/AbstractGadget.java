/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.RenderingException;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.TemplateRenderer;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller.AbstractPortalController;

/**
 * Abstract Gadget
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractGadget implements Gadget {
    protected final Logger log = Logger.getLogger(getClass().getName());

    protected String key;
    protected String displayName;
    protected String description;
    protected int order;
    protected String template;

    public static final String GADGET = "gadget";

    protected Logger getLog() {
        return log;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String render(final Map<String, Object> context) {
        Map<String, Object> velocityParams = createVelocityParams(context);

        StringWriter templateContent = new StringWriter();

        TemplateRenderer templateRenderer = ComponentProvider.getInstance().getComponent(TemplateRenderer.class);

        try {
            templateRenderer.render(getTemplate(), new ImmutableMap.Builder<String, Object>()
                            .put(AbstractPortalController.CONTEXT_KEY, context)
                            .put(GADGET, this)
                            .put(AbstractPortalController.REQUEST_KEY, context.get(AbstractPortalController.REQUEST_KEY))
                            .put(AbstractPortalController.RESPONSE_KEY, context.get(AbstractPortalController.RESPONSE_KEY))
                            .put(AbstractPortalController.PORTAL_NAVIGATION, context.get(AbstractPortalController.PORTAL_NAVIGATION))
                            .put(AbstractPortalController.PORTAL_RESOURCE_MANAGER, context.get(AbstractPortalController.PORTAL_RESOURCE_MANAGER))
                            .put(AbstractPortalController.PORTAL_RESOURCES_URL, context.get(AbstractPortalController.PORTAL_RESOURCES_URL))
                            .put(AbstractPortalController.PORTAL_RESOURCES_ATTACHMENT_URL, context.get(AbstractPortalController.PORTAL_RESOURCES_ATTACHMENT_URL))
                            .putAll(velocityParams)
                            .build(),
                    templateContent);

        } catch (final RenderingException | IOException ex) {
            String msg = String.format("An error occurred during rendering velocity template file '%s'!", getTemplate());
            log.warn(msg, ex);
            templateContent.write(msg);
            templateContent.write("<br/><pre>");
            ex.printStackTrace(new PrintWriter(templateContent));
            templateContent.write("</pre>");
        }

        return templateContent.toString();
    }

    public boolean shouldDisplay(final Map<String, Object> context) {
        return true;
    }

    protected abstract Map<String, Object> createVelocityParams(final Map<String, Object> context);

}
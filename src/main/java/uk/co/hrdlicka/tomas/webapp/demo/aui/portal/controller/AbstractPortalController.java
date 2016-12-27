/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import org.springframework.web.servlet.ModelAndView;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalConfig;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalNavigation;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalNavigationSystem;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalResource;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalResourceManager;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.RenderFragment;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.RenderingException;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.TemplateRenderer;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.StringUtils;

/**
 * Abstract Portal Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractPortalController {
	protected final Logger log = Logger.getLogger(getClass().getName());

    protected static final String PORTAL_CONTEXT_PATH = "context-path";
    protected static final String PORTAL_VERSION_NUMBER = "version-number";
    protected static final String PORTAL_LOGGEDIN_USER = "loggedin-user";

	public static final String PORTAL_NAVIGATION = "portalNavigation";
	public static final String PORTAL_RESOURCE_MANAGER = "portalResourceManager";
	public static final String PORTAL_RESOURCES_URL = "portalResourcesUrl";
	public static final String PORTAL_RESOURCES_ATTACHMENT_URL = "portalResourcesAttachmentUrl";

	public static final String LAYOUT_KEY = "screen_content";
	public static final String CONTEXT_KEY = "context";
	public static final String CONTEXT_PATH = "contextPath";
	public static final String REQUEST_KEY = "request";
	public static final String RESPONSE_KEY = "response";

	protected static final String TEMPLATE_LAYOUT_FOLDER = "/layout";
	protected static final String TEMPLATE_GENERAL = "general";

	protected String displayName;
    protected boolean headerDisplayable;
    protected String headerImage;
    protected String headerActions;
    protected String headerNavigation;
	protected String templateStyleClass;
	protected boolean blankScreen;
    protected boolean navigation;
    protected String navigationLocation;
    protected boolean sidebar;
    protected String sidebarTemplate;
    protected String sidebarStyleClass;
	protected String resourcesUrl;
	protected String resourcesAttachmentUrl;

	@Resource(name="portalNavigation")
	protected PortalNavigation portalNavigation;

	@Resource(name="portalResourceManager")
	protected PortalResourceManager portalResourceManager;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isHeaderDisplayable() {
		return headerDisplayable;
	}

	public void setHeaderDisplayable(boolean headerDisplayable) {
		this.headerDisplayable = headerDisplayable;
	}

	public String getHeaderImage() {
		return headerImage;
	}

	public void setHeaderImage(String headerImage) {
		this.headerImage = headerImage;
	}
	
	public String getHeaderActions() {
		return headerActions;
	}

	public void setHeaderActions(String headerActions) {
		this.headerActions = headerActions;
	}

	public String getHeaderNavigation() {
		return headerNavigation;
	}

	public void setHeaderNavigation(String headerNavigation) {
		this.headerNavigation = headerNavigation;
	}

	public void showHeader(final String image, final String actionsLocation, final String navigationLocation) {
		setHeaderDisplayable(true);
		setHeaderImage(image);
		setHeaderActions(actionsLocation);
		setHeaderNavigation(navigationLocation);
	}

	public String getTemplateStyleClass() {
		return templateStyleClass;
	}

	public void setTemplateStyleClass(String templateStyleClass) {
		this.templateStyleClass = templateStyleClass;
	}

	public boolean isBlankScreen() {
		return blankScreen;
	}

	public void setBlankScreen(boolean blankScreen) {
		this.blankScreen = blankScreen;
	}

	public boolean isNavigation() {
		return navigation;
	}

	public void setNavigation(boolean navigation) {
		this.navigation = navigation;
	}

	public String getNavigationLocation() {
		return navigationLocation;
	}

	public void setNavigationLocation(String navigationLocation) {
		this.navigationLocation = navigationLocation;
	}

	public void showNavigation(final String location) {
		setNavigation(true);
		setNavigationLocation(location);
	}
	
	public boolean isSidebar() {
		return sidebar;
	}

	public void setSidebar(boolean sidebar) {
		this.sidebar = sidebar;
	}

	public String getSidebarTemplate() {
		return sidebarTemplate;
	}

	public void setSidebarTemplate(String sidebarTemplate) {
		this.sidebarTemplate = sidebarTemplate;
	}
	
	public String getSidebarStyleClass() {
		return sidebarStyleClass;
	}

	public void setSidebarStyleClass(String sidebarStyleClass) {
		this.sidebarStyleClass = sidebarStyleClass;
	}
	
	public void showSidebar(final String sidebarTemplate) {
		showSidebar(sidebarTemplate, null);
	}
	
	public void showSidebar(final String sidebarTemplate, final String sidebarStyleClass) {
		setSidebar(true);
		setSidebarTemplate(sidebarTemplate);
		setSidebarStyleClass(sidebarStyleClass);
	}

	public PortalNavigation getPortalNavigation() {
		return portalNavigation;
	}

	public PortalResourceManager getPortalResourceManager() {
		return portalResourceManager;
	}

	public String renderFragment(
			final String template,
			final Map<String, Object> context,
			final HttpServletRequest request,
			final HttpServletResponse response) {
				
		renderClearResources();

		StringWriter templateContent = new StringWriter();

		TemplateRenderer templateRenderer = ComponentProvider.getInstance().getComponent(TemplateRenderer.class);
		
        try {
        	Map<String, Object> velocityParams = getVelocityParams(context, request, response);			
			templateRenderer.render(template, velocityParams, templateContent);
            
        } catch (final RenderingException | IOException ex) {
        	String msg = String.format("An error occurred during rendering velocity template file '%s'!", template);
        	log.warn(msg, ex);
        	templateContent.write(msg);
        	templateContent.write("<br/><pre>");
            ex.printStackTrace(new PrintWriter(templateContent));
            templateContent.write("</pre>");
        }

		renderClear();

		return templateContent.toString();		
	}

	public ModelAndView render(
			final String template,
			final Map<String, Object> context,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		renderClearResources();

		Map<String, Object> velocityParams = null;
		String templatePath = template;

		try {
			velocityParams = getVelocityParams(context, request, response);

			TemplateRenderer templateRenderer = ComponentProvider.getInstance().getComponent(TemplateRenderer.class);

			if (templateRenderer != null) {
				StringWriter templateContent = new StringWriter();
				templateRenderer.render(templatePath, velocityParams, templateContent);
				velocityParams.put("renderFragmentContent", templateContent.toString());
				templateContent = null;

			} else {
				String msg = String.format("Unable to get TemplateRender! Skipping rendering for template %s", templatePath);
				log.warn(msg);
				velocityParams.put("renderFragmentContent", String.format("<b>%s</b>", msg));
			}

			templatePath = String.format("%s/%s", TEMPLATE_LAYOUT_FOLDER, TEMPLATE_GENERAL);

		} catch (final RenderingException | IOException ex) {
			log.error("Unable to get Velocity parameters!", ex);
		}

		ModelAndView mv = new ModelAndView(templatePath, velocityParams);

		renderClear();

		return mv;
	}

	public ModelAndView renderFragment(
			final RenderFragment fragment,
			final Map<String, Object> context,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		renderClearResources();

		Map<String, Object> velocityParams = null;

		try {
			velocityParams = getVelocityParams(context, request, response);

			if (fragment.shouldDisplay(context)) {
				String fragmentContent = fragment.render(velocityParams);
				velocityParams.put("renderFragmentContent", fragmentContent);
			}

		} catch (final RenderingException | IOException ex) {
			log.error("Unable to get Velocity parameters!", ex);
		}

		String template = String.format("%s/%s", TEMPLATE_LAYOUT_FOLDER, TEMPLATE_GENERAL);

		ModelAndView mv = new ModelAndView(template, velocityParams);

		renderClear();

		return mv;
	}

	protected void renderClear() {
		setTemplateStyleClass(null);
		
		setHeaderDisplayable(false);
		setHeaderImage(null);
		setHeaderActions(null);
		setHeaderNavigation(null);

		setBlankScreen(false);
		
		setNavigation(false);
		setNavigationLocation(null);
		
		setSidebar(false);
		setSidebarTemplate(null);
		setSidebarStyleClass(null);
	}
	
	protected void renderClearResources() {
		getPortalResourceManager().clearMetadata();
		getPortalResourceManager().clearRequiredResources();		
	}
	
	protected Map<String, Object> getVelocityParams(
			final Map<String, Object> context,
			final HttpServletRequest request,
			final HttpServletResponse response) throws RenderingException, IOException {
		
		Map<String, Object> velocityParams = new HashMap<String, Object>();

		TemplateRenderer templateRenderer = ComponentProvider.getInstance().getComponent(TemplateRenderer.class);

		context.put(REQUEST_KEY, request);
		context.put(RESPONSE_KEY, response);
		context.put(PORTAL_NAVIGATION, getPortalNavigation());
		context.put(PORTAL_RESOURCE_MANAGER, getPortalResourceManager());

		// Build layout sidebar
		if (isSidebar()) {
			StringWriter sidebarContent = new StringWriter();
			templateRenderer.render(getSidebarTemplate(), new ImmutableMap.Builder<String, Object>()
					.put(CONTEXT_KEY, context)
					.putAll(context)
					.build(),
					sidebarContent);
			
			velocityParams.put("sidebarContent", sidebarContent.toString());
		}

		velocityParams.put("isSidebar", isSidebar());
		velocityParams.put("sidebarStyleClass", getSidebarStyleClass());

		// Build AppSwitcher 
		StringWriter headerBeforeContent = new StringWriter();
		templateRenderer.render("/layout/navigation.vm", new ImmutableMap.Builder<String, Object>()
				.put(CONTEXT_KEY, context)
				.put("navigationId", "aui-nav")
				.put("location", PortalNavigationSystem.NAVIGATION_APPSWITCHER)
				.putAll(context)
				.build(),
				headerBeforeContent);
		
		velocityParams.put("headerBeforeContent", headerBeforeContent.toString());

		// Build primary navigation
		StringWriter primaryNavigation = new StringWriter();
		templateRenderer.render("/layout/navigation.vm", new ImmutableMap.Builder<String, Object>()
				.put(CONTEXT_KEY, context)
				.put("navigationId", "aui-nav")
				.put("location", PortalNavigationSystem.NAVIGATION_TOP_BAR)
				.putAll(context)
				.build(),
				primaryNavigation);
		
		velocityParams.put("primaryNavContent", primaryNavigation.toString());

		// Build secondary navigation
		StringWriter secondaryNavigation = new StringWriter();
		templateRenderer.render("/layout/navigation.vm", new ImmutableMap.Builder<String, Object>()
				.put(CONTEXT_KEY, context)
				.put("navigationId", "aui-nav")
				.put("location", PortalNavigationSystem.NAVIGATION_HEADER_LINKS)
				.putAll(context)
				.build(),
				secondaryNavigation);
		
		velocityParams.put("secondaryNavContent", secondaryNavigation.toString());

		if (isHeaderDisplayable() && getHeaderNavigation() != null) {
			// Build header navigation
			StringWriter headerNavigation = new StringWriter();
			templateRenderer.render("/layout/navigation.vm", new ImmutableMap.Builder<String, Object>()
					.put(CONTEXT_KEY, context)
					.put("navigationId", "aui-nav")
					.put("location", getHeaderNavigation())
					.putAll(context)
					.build(),
					headerNavigation);
			
			velocityParams.put("headerNavigationContent", headerNavigation.toString());			
		}

		velocityParams.putAll(context);

		PortalConfig portalConfig = ComponentProvider.getInstance().getComponent(PortalConfig.class);
		String portalCopyright = portalConfig.getPortalCopyright();

		velocityParams.put(CONTEXT_KEY, context);
		velocityParams.put("portalName", portalConfig.getPortalName());
		velocityParams.put("portalLogoText", portalConfig.getPortalLogoText());
		velocityParams.put("portalCopyright", portalCopyright);
		velocityParams.put("portalVersion", portalConfig.getPortalVersion());
		velocityParams.put("portalBuildNumber", portalConfig.getPortalBuildNumber());
		velocityParams.put("displayName", getDisplayName());
		velocityParams.put("headerDisplayable", isHeaderDisplayable());
		velocityParams.put("headerImage", getHeaderImage());
		velocityParams.put("headerActions", getHeaderActions());
		velocityParams.put("templateStyleClass", getTemplateStyleClass());
		velocityParams.put("blankScreen", isBlankScreen());
        velocityParams.put("isNavigation", isNavigation());
        velocityParams.put("navigationLocation", getNavigationLocation());
        velocityParams.put(PORTAL_RESOURCES_URL, getResourceUrl(request));
        velocityParams.put(PORTAL_RESOURCES_ATTACHMENT_URL, getResourceAttachmentUrl(request));
        
        getPortalResourceManager().addMetadata(PORTAL_CONTEXT_PATH, request.getContextPath());
        getPortalResourceManager().addMetadata(PORTAL_VERSION_NUMBER, portalConfig.getPortalVersion());

        String login = "";
        getPortalResourceManager().addMetadata(PORTAL_LOGGEDIN_USER, login);

		return velocityParams;
	}

	protected String getResourceUrl(final HttpServletRequest request) {
		return getResourceUrl(null, request);		
	}
	
	protected String getResourceUrl(final String resource, final HttpServletRequest request) {
		if (StringUtils.isNullOrEmpty(resourcesUrl)) {
	        String pluginKey = PortalResource.INTERNAL;
	        resourcesUrl = String.format("%s/download/resources/%s", request.getContextPath(), pluginKey);
		}

		String resourceUrl = resource;

		if (StringUtils.isNullOrEmpty(resourceUrl)) {
			return resourcesUrl;
		}

		if (!resourceUrl.startsWith("/")) {
			resourceUrl = String.format("/%s", resourceUrl);
		}

		return String.format("%s%s", resourcesUrl, resourceUrl);
	}

	protected String getResourceAttachmentUrl(final HttpServletRequest request) {
		return getResourceAttachmentUrl(null, request);		
	}

	protected String getResourceAttachmentUrl(final String resource, final HttpServletRequest request) {
		if (StringUtils.isNullOrEmpty(resourcesAttachmentUrl)) {
	        String pluginKey = PortalResource.INTERNAL;
	        resourcesAttachmentUrl = String.format("%s/download/attachment/%s", request.getContextPath(), pluginKey);			
		}

		String resourceUrl = resource;

		if (StringUtils.isNullOrEmpty(resourceUrl)) {
			return resourcesAttachmentUrl;
		}

		if (!resourceUrl.startsWith("/")) {
			resourceUrl = String.format("/%s", resourceUrl);
		}

		return String.format("%s%s", resourcesAttachmentUrl, resourceUrl);
	}

	protected String parserVariables(final String property, final HttpServletRequest request) {
		if (StringUtils.isNullOrEmpty(property) || request == null) {
			return property;
		}

		String prop = property;
		String contextPath = String.format("${%s}", CONTEXT_PATH);

		if (prop.contains(contextPath)) {
			prop = prop.replace(contextPath, request.getContextPath());
		}

		String portalResourcesURL = String.format("${%s}", PORTAL_RESOURCES_URL);

		if (prop.contains(portalResourcesURL)) {
			prop = prop.replace(portalResourcesURL, getResourceUrl(request));
		}

		String portalResourcesAttachmentURL = String.format("${%s}", PORTAL_RESOURCES_ATTACHMENT_URL);

		if (prop.contains(portalResourcesAttachmentURL)) {
			prop = prop.replace(portalResourcesAttachmentURL, getResourceAttachmentUrl(request));
		}

		return prop;
	}
}
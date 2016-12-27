/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalConfig;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalNavigation;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalResourceManager;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.WebPortalConfig;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.AdminGadgetPanel;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.DashboardGadgetPanelManager;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.model.Setting;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.repository.PortalSettingService;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.TemplateRenderer;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.template.velocity.VelocityTemplateRendererImpl;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.ConfigurationManager;

/**
 * Abstract Bootstrap Launcher Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractBootstrapLauncherService implements ApplicationListener<ContextRefreshedEvent> {
	protected final Logger log = Logger.getLogger(getClass().getName());

    @Autowired
    protected ServletContext servletContext;

    @Resource(name="portalNavigation")
    protected PortalNavigation portalNavigation;

    @Resource(name="portalResourceManager")
    protected PortalResourceManager portalResourceManager;

    @Resource(name="portalSettingService")
    protected PortalSettingService portalSettingService;

	public AbstractBootstrapLauncherService() {
		log.info("Starting Portal...");
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

    public PortalNavigation getPortalNavigation() {
		return portalNavigation;
	}

    public PortalResourceManager getPortalResourceManager() {
		return portalResourceManager;
	}

	public PortalSettingService getPortalSettingService() {
		return portalSettingService;
	}

	public abstract void initialize();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	log.debug("Spring Application Context has been initialized!");

    	log.info("Initializing Portal...");
    	initializePortal();
    	initialize();

    	PortalConfig config = getPortalConfig();

    	if (config != null) {
    		config.configure();
    	}
    }

    protected void initializePortal() {
		WebPortalConfig.loadConfig();

    	addComponents();
    	checkDatabaseVersion();
    }

    protected void addComponents() {
    	TemplateRenderer templateRenderer = new VelocityTemplateRendererImpl(getServletContext(), null);

    	ComponentProvider componentProvider = ComponentProvider.getInstance();
		componentProvider.addComponent(ServletContext.class, getServletContext());
		componentProvider.addComponent(TemplateRenderer.class, templateRenderer);
		componentProvider.addComponent(PortalNavigation.class, getPortalNavigation());
		componentProvider.addComponent(PortalResourceManager.class, getPortalResourceManager());
		componentProvider.addComponent(AdminGadgetPanel.class, new AdminGadgetPanel());
		componentProvider.addComponent(DashboardGadgetPanelManager.class, new DashboardGadgetPanelManager());
	}

	protected void checkDatabaseVersion() {
		boolean isUpdate = true;
		String portalVersion = ConfigurationManager.getInstance().getProperty(WebPortalConfig.VERSION);
		Setting dbVersion =  getPortalSettingService().getByKey(WebPortalConfig.SETTING_DATABASE_VERSION);

		if (dbVersion == null) {
			isUpdate = false;
			dbVersion = new Setting();
			dbVersion.setKey(WebPortalConfig.SETTING_DATABASE_VERSION);
		}

		dbVersion.setValue(portalVersion);

		if (isUpdate) {
			getPortalSettingService().update(dbVersion);

		} else {
			getPortalSettingService().add(dbVersion);
		}
	}

	protected PortalConfig getPortalConfig() {
		return ComponentProvider.getInstance().getComponent(PortalConfig.class);
	}

	protected void setPortalConfig(final PortalConfig portalConfig) {
		ComponentProvider.getInstance().addComponent(PortalConfig.class, portalConfig);
	}
}
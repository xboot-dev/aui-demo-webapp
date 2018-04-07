/* Copyright 2016, 2018 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import org.apache.log4j.Logger;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;

/**
 * Abstract Portal Configuration class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractPortalConfig implements PortalConfig {
	protected final Logger log = Logger.getLogger(getClass().getName());

	public static final String PORTAL_NAME = "XBOOT's Portal";
	public static final String PORTAL_LOGO_TEXT = "XBOOT";
	public static final String PORTAL_COPYRIGHT = "Copyright (c) 2018 Tomas Hrdlicka";

    protected PortalNavigation portalNavigation;
    protected PortalResourceManager portalResourceManager;

	protected String portalName;
	protected String portalLogoText;
	protected String portalCopyright;
	protected String portalVersion;
	protected String portalBuildNumber;

	public AbstractPortalConfig() {
		setPortalName(PORTAL_NAME);
		setPortalLogoText(PORTAL_LOGO_TEXT);
		setPortalCopyright(PORTAL_COPYRIGHT);
		setPortalVersion(WebPortalConfig.getVersion());
		setPortalBuildNumber(WebPortalConfig.getBuildNumber());
	}

	public PortalNavigation getPortalNavigation() {
		if (portalNavigation == null) {
			portalNavigation = ComponentProvider.getInstance().getComponent(PortalNavigation.class);
		}

		return portalNavigation;
	}

	public PortalResourceManager getPortalResourceManager() {
		if (portalResourceManager == null) {
			portalResourceManager = ComponentProvider.getInstance().getComponent(PortalResourceManager.class);
		}

		return portalResourceManager;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getPortalLogoText() {
		return portalLogoText;
	}

	public void setPortalLogoText(String portalLogoText) {
		this.portalLogoText = portalLogoText;
	}

	public String getPortalCopyright() {
		return portalCopyright;
	}

	public void setPortalCopyright(String portalCopyright) {
		this.portalCopyright = portalCopyright;
	}

	public String getPortalVersion() {
		return portalVersion;
	}

	public void setPortalVersion(String portalVersion) {
		this.portalVersion = portalVersion;
	}

	public String getPortalBuildNumber() {
		return portalBuildNumber;
	}

	public void setPortalBuildNumber(String portalBuildNumber) {
		this.portalBuildNumber = portalBuildNumber;
	}

	public abstract void configure();
}
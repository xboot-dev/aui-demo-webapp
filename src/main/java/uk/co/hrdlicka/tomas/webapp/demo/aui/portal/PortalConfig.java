/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

/**
 * Portal Configuration interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface PortalConfig {

	String getPortalName();
	String getPortalLogoText();
	String getPortalCopyright();
	String getPortalVersion();
	String getPortalBuildNumber();
	void configure();

}
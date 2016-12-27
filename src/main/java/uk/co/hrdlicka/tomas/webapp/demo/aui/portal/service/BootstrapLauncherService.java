/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.service;

import org.springframework.stereotype.Service;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.DefaultPortalConfig;

/**
 * Bootstrap Launcher Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Service("bootstrapLauncherService")
public class BootstrapLauncherService extends AbstractBootstrapLauncherService {

	public void initialize() {
		log.debug("Initializing Portal BootstrapLauncherService...");

		setPortalConfig(new DefaultPortalConfig());
	}
}
/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.startup;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * Abstract Bootstrap Launcher Context Listener
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractBootstrapLauncherContextListener implements ServletContextListener {
	protected final Logger log = Logger.getLogger(getClass().getName());
    protected ServletContext servletContext;

	protected Logger getLog() {
		return log;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	protected void configureInternal() {
	}

    protected abstract void configure();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		configureInternal();
		configure();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
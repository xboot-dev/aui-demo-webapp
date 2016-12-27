/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.List;
import java.util.Map;

/**
 * Portal Resource Manager interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface PortalResourceManager {

	List<PortalResource> getResources();
	List<PortalResource> getResourcesForContext(final String context);
	void addResource(final PortalResource resource);

	void clearRequiredResources();
	void requireResourcesForContext(final String context);
	List<PortalResource> getRequiredResources();
	List<String> renderRequiredResources();

	Map<String, String> getMetadata();
	void addMetadata(final String name, final String value);
	void clearMetadata();
	List<String> renderMetadata();

}
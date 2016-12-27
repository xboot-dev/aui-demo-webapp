/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.StringUtils;

/**
 * Portal Resource Manager class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Service("portalResourceManager")
public class PortalResourceManagerImpl implements PortalResourceManager {
	private final Logger log = Logger.getLogger(PortalResourceManagerImpl.class);

	public static final String METADATA_PREFIX = "portal";

	private final List<PortalResource> resources = new ArrayList<PortalResource>();
	private final List<PortalResource> requiredResources = new ArrayList<PortalResource>();
	private final Map<String, String> metadata = new HashMap<String, String>();

	public List<PortalResource> getResources() {
		return resources;
	}

	public List<PortalResource> getResourcesForContext(final String context) {
		if (resources.isEmpty()) {
			return null;
		}

		List<PortalResource> list = new ArrayList<PortalResource>();

		for (PortalResource resource : resources) {
			if (resource.getContext() != null &&
					resource.getContext().equals(context)) {
				list.add(resource);
			}
		}

		if (!list.isEmpty()) {
			return list;
		}

		return null;
	}

	public void addResource(final PortalResource resource) {
		if (resources.contains(resource)) {
			return;
		}

		resources.add(resource);
	}

	public void clearRequiredResources() {
		requiredResources.clear();
	}

	public void requireResourcesForContext(final String context) {
		List<PortalResource> list = getResourcesForContext(context);

		if (list != null) {
			for (PortalResource requiredResource : list) {
				boolean isFound = false;

				for (PortalResource resource : requiredResources) {
					if (resource.getKey().equals(requiredResource.getKey())) {
						isFound = true;
						break;
					}
				}

				if (!isFound) {
					requiredResources.add(requiredResource);
				}
			}
		}
	}

	public List<PortalResource> getRequiredResources() {
		return requiredResources;
	}

	public List<String> renderRequiredResources() {
		if (requiredResources.isEmpty()) {
			return null;
		}

		List<String> renderedResources = new ArrayList<String>();

		for (PortalResource resource : requiredResources) {
			for (PortalResourceItem item : resource.getResources()) {
				if (item.getLocation() != null) {
					if (item.getLocation().endsWith(".css")) {
						renderedResources.add(renderCss(resource.getPluginKey(), item));
					}

					else if (item.getLocation().endsWith(".js")) {
						renderedResources.add(renderJavaScript(resource.getPluginKey(), item));
					}
				}
			}
		}

		if (!renderedResources.isEmpty()) {
			return renderedResources;
		}

		return null;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void addMetadata(final String name, final String value) {
		String key = String.format("%s-%s", METADATA_PREFIX, name);

		if (metadata.containsKey(key)) {
			metadata.remove(key);
		}

		metadata.put(key, value);
	}

	public void clearMetadata() {
		metadata.clear();
	}

	public List<String> renderMetadata() {
		if (metadata.isEmpty()) {
			return null;
		}

		List<String> renderedMetadata = new ArrayList<String>();

		for (Entry<String, String> entry : metadata.entrySet()) {
			//log.debug(String.format("Portal Metadata: %s=%s", entry.getKey(), entry.getValue()));
			renderedMetadata.add(renderMetadata(entry.getKey(), entry.getValue()));
		}

		if (!renderedMetadata.isEmpty()) {
			return renderedMetadata;
		}

		return null;
	}

	private String getResourcesUrl(final String pluginKey) {
		String id = PortalResource.INTERNAL;

		if (!StringUtils.isNullOrEmpty(pluginKey)) {
			id = pluginKey;
		}

		ServletContext servletContext = ComponentProvider.getInstance().getComponent(ServletContext.class);
		String resourcesUrl = String.format("%s/download/resources/%s", servletContext.getContextPath(), id);
		return resourcesUrl;
	}

	private String renderCss(final String pluginKey, final PortalResourceItem item) {
		String link = String.format("%s%s", getResourcesUrl(pluginKey), item.getLocation());
		return String.format("<link rel=\"stylesheet\" href=\"%s\" media=\"all\">", link);
	}

	private String renderJavaScript(final String pluginKey, final PortalResourceItem item) {
		String link = String.format("%s%s", getResourcesUrl(pluginKey), item.getLocation());
		return String.format("<script src=\"%s\"></script>", link);
	}

	private String renderMetadata(final String name, final String value) {
		return String.format("<meta name=\"%s\" content=\"%s\">", name, value);
	}
}
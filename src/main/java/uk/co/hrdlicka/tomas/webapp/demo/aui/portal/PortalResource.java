/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.ArrayList;
import java.util.List;

/**
 * Portal Resource
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class PortalResource {
	private String key;
	private String name;
	private String context;
	private String pluginKey;
	private final List<PortalResourceItem> resources = new ArrayList<PortalResourceItem>();

	public static final String INTERNAL = "_";

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getPluginKey() {
		return pluginKey;
	}

	public void setPluginKey(String pluginKey) {
		this.pluginKey = pluginKey;
	}

	public List<PortalResourceItem> getResources() {
		return resources;
	}

	public void addResource(PortalResourceItem resource) {
		if (resources.contains(resource)) {
			return;
		}

		resources.add(resource);
	}
}
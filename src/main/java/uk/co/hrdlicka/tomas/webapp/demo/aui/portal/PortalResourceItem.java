/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

/**
 * Portal Resource Item
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class PortalResourceItem {
	private String type;
	private String name;
	private String location;

	public static final String TYPE_DOWNLOAD = "download";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public static PortalResourceItem create(final String name, final String location) {
		return create(TYPE_DOWNLOAD, name, location);
	}

	public static PortalResourceItem create(final String type, final String name, final String location) {
		PortalResourceItem item = new PortalResourceItem();
		item.setType(type);
		item.setName(name);
		item.setLocation(location);
		return item;
	}
}
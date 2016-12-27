/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Navigation Section
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class NavigationSection {
	private String key;
	private String location;
	private String section;
	private String label;
	private int weight;
	private String description;
	private final Map<String, String> params = new HashMap<String, String>();
	private final List<NavigationCondition> conditions = new ArrayList<NavigationCondition>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpace() {
		if (getSection() != null) {
			return String.format("%s/%s", getLocation(), getSection());
		}

		return getLocation();
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void addParam(String name, String value) {
		if (getParams().containsKey(name)) {
			return;
		}

		getParams().put(name, value);
	}

	public List<NavigationCondition> getConditions() {
		return conditions;
	}

	public void addCondition(NavigationCondition condition) {
		if (getConditions().contains(condition)) {
			return;
		}

		getConditions().add(condition);
	}

	public NavigationSection clone() {
		NavigationSection section = new NavigationSection();
		section.setKey(getKey());
		section.setLocation(getLocation());
		section.setSection(getSection());
		section.setLabel(getLabel());
		section.setWeight(getWeight());
		section.setDescription(getDescription());

		for (Entry<String, String> entry : getParams().entrySet()) {
			section.addParam(entry.getKey(), entry.getValue());
		}

		for (NavigationCondition condition : getConditions()) {
			section.addCondition(condition);
		}

		return section;
	}

	public static NavigationSection createLocation(final String location) {
		return createLocation(location, location);
	}

	public static NavigationSection createLocation(final String location, final String key) {
		return createLocation(location, key, null);
	}

	public static NavigationSection createLocation(final String location, final String key, final String label) {
		return createLocation(location, key, label, 0);
	}

	public static NavigationSection createLocation(
			final String location,
			final String key,
			final String label,
			final int weight) {

		NavigationSection section = new NavigationSection();
		section.setKey(key);
		section.setLocation(location);
		section.setSection(null);
		section.setLabel(label);
		section.setWeight(weight);
		return section;
	}

	public static NavigationSection createSection(final String space, final String key, final String label) {
		return createSection(space, key, label, 0);
	}

	public static NavigationSection createSection(
			final String space,
			final String key,
			final String label,
			final int weight) {

   		String location = null;
   		String section = null;

		String[] parts = space.split("/"); // ${location}/${section} => ${space}

		if (parts.length > 0) {
			location = parts[0];

			if (parts.length > 1) {
				section = parts[1];
			}
		}

		NavigationSection s = createLocation(location, key, label, weight);

		if (section != null) {
			s.setSection(section);
		}

		return s;
	}
}
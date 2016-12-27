/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Navigation Item
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class NavigationItem {
	private String key;
	private NavigationSection section;
	private String label;
	private String link;
	private int weight;
	private String styleClass;
	private String description;
	private final Map<String, String> params = new HashMap<String, String>();
	private final List<NavigationCondition> conditions = new ArrayList<NavigationCondition>();
	private final List<NavigationValidator> validators = new ArrayList<NavigationValidator>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public NavigationSection getSection() {
		return section;
	}

	public void setSection(NavigationSection section) {
		this.section = section;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<NavigationValidator> getValidators() {
		return validators;
	}

	public void addValidator(NavigationValidator validator) {
		if (getValidators().contains(validator)) {
			return;
		}

		getValidators().add(validator);
	}

	public NavigationItem clone() {
		NavigationItem item = new NavigationItem();
		item.setKey(getKey());
		item.setSection(getSection());
		item.setLabel(getLabel());
		item.setLink(getLink());
		item.setWeight(getWeight());
		item.setStyleClass(getStyleClass());
		item.setDescription(getDescription());

		for (Entry<String, String> entry : getParams().entrySet()) {
			item.addParam(entry.getKey(), entry.getValue());
		}

		for (NavigationCondition condition : getConditions()) {
			item.addCondition(condition);
		}

		for (NavigationValidator validator : getValidators()) {
			item.addValidator(validator);
		}

		return item;
	}
}
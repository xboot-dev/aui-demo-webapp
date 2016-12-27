/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.ArrayList;
import java.util.Collections;
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
 * Portal Navigation class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Service("portalNavigation")
public class PortalNavigationImpl implements PortalNavigation {
	private final Logger log = Logger.getLogger(PortalNavigationImpl.class);

	private final List<NavigationSection> sections = new ArrayList<NavigationSection>();
	private final Map<String, List<NavigationItem>> items = new HashMap<String, List<NavigationItem>>();

	public List<NavigationSection> getSections() {
		return sections;
	}

	public NavigationSection getSection(final String key) {
		for (NavigationSection section : sections) {
			if (section.getKey().equals(key)) {
				return section;
			}
		}

		return null;
	}

	public List<NavigationSection> getSectionsByLocation(final String location) {
		List<NavigationSection> list = new ArrayList<NavigationSection>();

		for (NavigationSection section : sections) {
			if (section.getLocation().equals(location) &&
					section.getSection() != null) {
				list.add(section);
			}
		}

		if (!list.isEmpty()) {
			Collections.sort(list, new NavigationSectionComparator());
			return list;
		}

		return null;
	}

	public List<NavigationSection> getDisplayableSectionsByLocation(final String location, final Map<String, Object> context) {
		List<NavigationSection> sectionsList = getSectionsByLocation(location);

		if (sectionsList != null) {
			List<NavigationSection> results = new ArrayList<NavigationSection>();

			for (NavigationSection section : sectionsList) {
				boolean shouldDisplay = true;

				for (NavigationCondition condition : section.getConditions()) {
					shouldDisplay = condition.shouldDisplay(context);

					if (!shouldDisplay) {
						break;
					}
				}

				if (shouldDisplay) {
					results.add(section);
				}
			}

			if (!results.isEmpty()) {
				Collections.sort(results, new NavigationSectionComparator());
				return results;
			}
		}

		return null;
	}

	public NavigationSection getSectionBySpace(final String space) {
		for (NavigationSection section : sections) {
			if (section.getSpace().equals(space)) {
				return section;
			}
		}

		return null;
	}

	public void addSection(final NavigationSection section) {
		if (section == null) {
			throw new NullPointerException();
		}

		if (existsSection(section.getKey())) {
			log.debug(String.format("Navigation section with key=%s already exists!", section.getKey()));
			return;
		}

		sections.add(section);
	}

	public boolean existsSection(final String key) {
		NavigationSection section = getSection(key);

		if (section == null) {
			return false;
		}

		return true;
	}

	public List<NavigationItem> getItemsBySection(final String space) {
		for (Entry<String, List<NavigationItem>> entry : items.entrySet()) {
			String section = entry.getKey();

			if (section.equals(space)) {
				return entry.getValue();
			}
		}

		return null;
	}

	public List<NavigationItem> getDisplayableItemsBySection(final String space, final Map<String, Object> context) {
		List<NavigationItem> itemsList = getItemsBySection(space);

		if (itemsList != null) {
			List<NavigationItem> results = new ArrayList<NavigationItem>();

			for (NavigationItem item : itemsList) {
				boolean shouldDisplay = true;

				for (NavigationCondition condition : item.getConditions()) {
					shouldDisplay = condition.shouldDisplay(context);

					if (!shouldDisplay) {
						break;
					}
				}

				if (shouldDisplay) {
					boolean isValid = true;
					NavigationItem clonedItem = item.clone();

					for (NavigationValidator validator : item.getValidators()) {
						isValid = validator.validate(clonedItem, context);

						if (!isValid) {
							break;
						}
					}

					if (isValid) {
						clonedItem.setLink(parseVariables(clonedItem.getLink(), context));
						results.add(clonedItem);
					}
				}
			}

			if (!results.isEmpty()) {
				Collections.sort(results, new NavigationItemComparator());
				return results;
			}
		}

		return null;
	}

	public NavigationItem getItem(final String space, final String key) {
		for (Entry<String, List<NavigationItem>> entry : items.entrySet()) {
			String section = entry.getKey();

			if (section.equals(space)) {
				for (NavigationItem item : entry.getValue()) {
					if (item.getKey().equals(key)) {
						return item;
					}
				}
			}
		}

		return null;
	}

	public void addItem(final String space, final NavigationItem item) {
		if (space == null || item == null) {
			throw new NullPointerException();
		}

		if (existsItem(space, item.getKey())) {
			log.debug(String.format("Navigation item with key=%s for section=%s already exists!",
					item.getKey(), space));
			return;
		}

		NavigationSection section = getSectionBySpace(space);

		if (section == null) {
			log.debug(String.format("Navigation section [%s] is missing for navigation item with key=%s!",
					space, item.getKey()));
			return;
		}

		List<NavigationItem> list = null;

		if (items.containsKey(space)) {
			list = items.get(space);

		} else {
			list = new ArrayList<NavigationItem>();
			items.put(space, list);
		}

		item.setSection(section);
		item.setLink(checkLinkPath(item.getLink()));
		list.add(item);
	}

	public void addItem(final NavigationSection space, final NavigationItem item) {
		if (space == null) {
			throw new NullPointerException();
		}

		addItem(space.getSpace(), item);
	}

	public boolean existsItem(final String space, final String key) {
		NavigationItem item = getItem(space, key);

		if (item == null) {
			return false;
		}

		return true;
	}

	protected String checkLinkPath(String link) {
		if (StringUtils.isNullOrEmpty(link) || link.startsWith("http") || link.startsWith("https")) {
			return link;
		}

		ServletContext servletContext = ComponentProvider.getInstance().getComponent(ServletContext.class);

		if (!link.startsWith("/")) {
			link = String.format("/%s", link);
		}

		String url = String.format("%s%s", servletContext.getContextPath(), link);
		return url;
	}

	protected String parseVariables(final String value, final Map<String, Object> context) {
		if (StringUtils.isNullOrEmpty(value) || context == null || context.isEmpty()) {
			return value;
		}

		String parsedValue = value;

		for (Entry<String, Object> entry : context.entrySet()) {
			parsedValue = parsedValue.replace(String.format("${%s}", entry.getKey()), entry.getValue().toString());
		}

		return parsedValue;
	}
}
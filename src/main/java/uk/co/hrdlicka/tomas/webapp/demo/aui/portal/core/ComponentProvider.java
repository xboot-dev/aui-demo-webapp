/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Component Provider
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class ComponentProvider {
	private static final ComponentProvider singleton = new ComponentProvider();
	private final Map<Class<?>, Object> components = new HashMap<Class<?>, Object>();

	private ComponentProvider() {
	}

	public static ComponentProvider getInstance() {
		return singleton;
	}

	public static ComponentProvider newInstance() {
		return new ComponentProvider();
	}

	public void addComponent(final Class<?> componentType, final Object component) {
		components.put(componentType, component);
	}

	@SuppressWarnings("unchecked")
	public <T> T getComponent(Class<T> component) {
		if (components.containsKey(component)) {
			return (T) components.get(component);
		}

		return null;
	}
}
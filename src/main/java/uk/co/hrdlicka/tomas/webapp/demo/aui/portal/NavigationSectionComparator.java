/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.Comparator;

/**
 * Navigation Section Comparator
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class NavigationSectionComparator implements Comparator<NavigationSection> {

	@Override
	public int compare(final NavigationSection section1, final NavigationSection section2) {
		Integer weight1 = section1.getWeight();
		Integer weight2 = section2.getWeight();
		return weight1.compareTo(weight2);
	}
}
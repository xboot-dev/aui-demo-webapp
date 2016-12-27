/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.Comparator;

/**
 * Navigation Item Comparator
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class NavigationItemComparator implements Comparator<NavigationItem> {

	@Override
	public int compare(final NavigationItem item1, final NavigationItem item2) {
		Integer weight1 = item1.getWeight();
		Integer weight2 = item2.getWeight();
		return weight1.compareTo(weight2);
	}
}
/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal;

import java.util.List;
import java.util.Map;

/**
 * Portal Navigation interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface PortalNavigation {

    List<NavigationSection> getSections();
    List<NavigationSection> getSectionsByLocation(final String location);
    NavigationSection getSection(final String key);
    NavigationSection getSectionBySpace(final String space);
    void addSection(final NavigationSection section);
    boolean existsSection(final String key);

    List<NavigationItem> getItemsBySection(final String space);
    List<NavigationItem> getDisplayableItemsBySection(final String space, final Map<String, Object> context);
    NavigationItem getItem(final String space, final String key);
    void addItem(final String space, final NavigationItem item);
    void addItem(final NavigationSection space, final NavigationItem item);
    boolean existsItem(final String space, final String key);

}
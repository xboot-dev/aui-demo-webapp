/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import java.util.Comparator;

/**
 * Dashboard Gadget Panel Comparator
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class DashboardGadgetPanelComparator implements Comparator<DashboardGadgetPanel> {

    @Override
    public int compare(final DashboardGadgetPanel gadgetPanel1, final DashboardGadgetPanel gadgetPanel2) {
        return (gadgetPanel1.getName().toLowerCase()).compareTo(gadgetPanel2.getName().toLowerCase());
    }
}
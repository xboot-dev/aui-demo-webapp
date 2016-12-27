/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.StringUtils;

/**
 * Dashboard Gadget Panel Manager
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class DashboardGadgetPanelManager {
    private final Map<String, DashboardGadgetPanel> dashboards = new HashMap<String, DashboardGadgetPanel>();

    public List<DashboardGadgetPanel> getDashboards() {
        List<DashboardGadgetPanel> list = new ArrayList<DashboardGadgetPanel>();

        for (Map.Entry<String, DashboardGadgetPanel> entry : dashboards.entrySet()) {
            list.add(entry.getValue());
        }

        if (!list.isEmpty()) {
            Collections.sort(list, new DashboardGadgetPanelComparator());
            return list;
        }

        return null;
    }

    public DashboardGadgetPanel getDashboard(final String viewId) {
        if (!dashboards.containsKey(viewId)) {
            return null;
        }

        return dashboards.get(viewId);
    }

    public void addDashboard(final String viewId, final DashboardGadgetPanel dashboard) {
        if (StringUtils.isNullOrEmpty(viewId) || dashboard == null || dashboards.containsKey(viewId)) {
            return;
        }

        dashboards.put(viewId, dashboard);
    }
}
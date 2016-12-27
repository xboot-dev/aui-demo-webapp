/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.DashboardGadgetPanel;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.DashboardGadgetPanelManager;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.StringUtils;

/**
 * Dashboard Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController extends AbstractPortalController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showSystemDashboard(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		return new ModelAndView("redirect:/dashboard/view/system");
	}

	@RequestMapping(value = "/view/{viewId}", method = RequestMethod.GET)
	public ModelAndView showDashboard(
			@PathVariable("viewId") final String viewId,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		DashboardGadgetPanelManager dashboardManager = ComponentProvider.getInstance().getComponent(DashboardGadgetPanelManager.class);
		DashboardGadgetPanel dashboard = dashboardManager.getDashboard(viewId);

		Map<String, Object> context = new HashMap<String, Object>();

		if (dashboard == null) {
			context.put("viewId", viewId);
			return renderDashboardNotFound(context, request, response);
		}

		String dashboardName = "Dashboard";

		if (!StringUtils.isNullOrEmpty(dashboard.getName())) {
			dashboardName = dashboard.getName();
		}

		String imageUri = parserVariables(dashboard.getImageUri(), request);

		setDisplayName(dashboardName);
		showHeader(imageUri, dashboard.getActionButtons(), null);

		return renderFragment(dashboard, context, request, response);
	}

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView showManageDashboard(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		setDisplayName("Manage Dashboards");
		showHeader(null, null, null);

		DashboardGadgetPanelManager dashboardManager = ComponentProvider.getInstance().getComponent(DashboardGadgetPanelManager.class);

		Map<String, Object> context = new HashMap<String, Object>();
		context.put("dashboards", dashboardManager.getDashboards());

		return render("/dashboard/dashboards.vm", context, request, response);
	}

	protected ModelAndView renderDashboardNotFound(
			final Map<String, Object> context,
			final HttpServletRequest request,
			final HttpServletResponse response) {

		setBlankScreen(true);
		setTemplateStyleClass("aui-page-focused aui-page-focused-medium");

		return render("/dashboard/dashboards_not_found.vm", context, request, response);
	}
}
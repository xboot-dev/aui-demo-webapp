/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Portal Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Controller
@RequestMapping("/")
public class PortalController extends AbstractPortalController {

	public static final String PORTAL_REDIRECT = "/dashboard";

	@RequestMapping(method = RequestMethod.GET)
	public String showDashboard(
			final HttpServletRequest request,
			final HttpServletResponse response) {

		return String.format("redirect:%s", PORTAL_REDIRECT);
	}
}
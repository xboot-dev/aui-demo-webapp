/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalNavigationSystem;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.RenderFragment;

/**
 * Portal Administration Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class PortalAdminController extends AbstractPortalController {

    @Override
    public ModelAndView render(
            final String template,
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        showNavigation(PortalNavigationSystem.ADMIN_NAVIGATION);
        return super.render(template, context, request, response);
    }

    @Override
    public ModelAndView renderFragment(
            final RenderFragment fragment,
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        showNavigation(PortalNavigationSystem.ADMIN_NAVIGATION);
        return super.renderFragment(fragment, context, request, response);
    }
}
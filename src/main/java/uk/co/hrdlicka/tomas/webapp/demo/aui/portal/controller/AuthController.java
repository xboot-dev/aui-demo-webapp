/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalConfig;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller.model.LoginFormData;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;

/**
 * Auth Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Controller
@RequestMapping("/auth")
@SessionAttributes
public class AuthController extends AbstractPortalController {

    @RequestMapping(method = RequestMethod.GET)
    public String redirectoToLogin(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        return "redirect:/auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        Map<String, Object> context = new HashMap<String, Object>();

        return renderLoginForm(context, request, response);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(
            @ModelAttribute final LoginFormData loginFormData,
            final HttpServletRequest request,
            final HttpServletResponse response) {

		if (loginFormData != null) {
			log.debug(
			        String.format("Login=%s, Password=%s, UseCookie=%s",
                            loginFormData.getLogin(), loginFormData.getPassword(), loginFormData.isCookie()));
		}

        return new ModelAndView("redirect:/auth/login");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView processLogout(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        Map<String, Object> context = new HashMap<String, Object>();

        return renderLogoutForm(context, request, response);
    }

    private ModelAndView renderLoginForm(
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        String loginUrl = String.format("%s/auth/login", request.getContextPath());

        PortalConfig portalConfig = ComponentProvider.getInstance().getComponent(PortalConfig.class);

        context.put("loginTitle", String.format("Login to %s", portalConfig.getPortalName()));
        context.put("loginUrl", loginUrl);

        setBlankScreen(true);

		/*
		 * AUI Page - Common markup to produce an Atlassian standard page layout and base design.
		 * https://docs.atlassian.com/aui/latest/docs/page.html
		 */
        setTemplateStyleClass("aui-page-focused aui-page-focused-small");

        return render("login.vm", context, request, response);
    }

    private ModelAndView renderLogoutForm(
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        setBlankScreen(true);
        setTemplateStyleClass("aui-page-focused aui-page-focused-small");

        return render("logout.vm", context, request, response);
    }
}
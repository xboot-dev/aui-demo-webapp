/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Demo Page Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Controller
@RequestMapping("/demo")
public class DemoPageController extends AbstractPortalController {

    private static final String PAGE_DISPLAY_NAME = "Demo Page";

    @RequestMapping(method = RequestMethod.GET)
    public String redirectoToDemoPage(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        return "redirect:/demo/page";
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView showDemoPage(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        String imageUri = String.format("%s/resources/images/nsys_logo_avatar.png", request.getContextPath());

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("msg", "Hello World! :-)");

        setDisplayName(PAGE_DISPLAY_NAME);
        showHeader(imageUri, null, null);

        return render("/page/demo.vm", context, request, response);
    }
}
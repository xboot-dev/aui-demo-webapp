/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.core.ComponentProvider;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.gadget.AdminGadgetPanel;

/**
 * Admin Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends PortalAdminController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showAdmin(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        Map<String, Object> context = new HashMap<String, Object>();

        setDisplayName("Administration");
        showHeader(null, null, null);

        AdminGadgetPanel adminGadgetPanel = ComponentProvider.getInstance().getComponent(AdminGadgetPanel.class);

        return renderFragment(adminGadgetPanel, context, request, response);
    }

    @RequestMapping(value = "/system-info", method = RequestMethod.GET)
    public ModelAndView showSystemInfo(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("props", getSystemInfo());

        setDisplayName("System Info");
        showHeader(null, null, null);

        return render("/admin/system-info.vm", context, request, response);
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.GET)
    public ModelAndView showConfiguration(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        String imageUri = String.format("%s/resources/images/nsys_logo_avatar.png", request.getContextPath());

        Map<String, Object> context = new HashMap<String, Object>();

        setDisplayName("Configuration");
        showHeader(imageUri, null, null);
        showSidebar("/admin/configuration_sidebar.vm", "projects-sidebar");

        return render("/admin/configuration.vm", context, request, response);
    }

    @RequestMapping(value = "/auditlog", method = RequestMethod.GET)
    @ResponseBody
    public String showAuditLog(
            @RequestParam("redir") final String redir,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("redir", redir);

        return renderFragment("/admin/auditlog.vm", context, request, response);
    }

    @RequestMapping(value = "/auditlog", method = RequestMethod.POST)
    public ModelAndView processAuditLog(
            @RequestParam("eventId") final String eventId,
            @RequestParam("redir") final String redir,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        log.debug(String.format("Event Id: %s", eventId));

        return new ModelAndView(String.format("redirect:%s", redir));
    }

    protected Map<String, String> getSystemInfo() {
        Map<String, String> props = new LinkedHashMap<String, String>();

        Date currentDate = new Date();

        DateFormat dfDate = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        props.put("System Date", dfDate.format(currentDate));

        DateFormat dfTime = new SimpleDateFormat("HH:mm:ss Z");
        props.put("System Time", dfTime.format(currentDate));

        props.put("Java Version", System.getProperty("java.version"));
        props.put("Java Vendor", System.getProperty("java.vendor"));
        props.put("JVM Version", System.getProperty("java.vm.specification.version"));
        props.put("JVM Vendor", System.getProperty("java.vm.specification.vendor"));
        props.put("JVM Implementation Version", System.getProperty("java.vm.version"));
        props.put("User Name", System.getProperty("user.name"));
        props.put("System Encoding", System.getProperty("file.encoding"));

        String os = String.format("%s %s", System.getProperty("os.name"), System.getProperty("os.version"));
        props.put("Operating System", os);

        props.put("OS Architecture", System.getProperty("os.arch"));

        return props;
    }
}
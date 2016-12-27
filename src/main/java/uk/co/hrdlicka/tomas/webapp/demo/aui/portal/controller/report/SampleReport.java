/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.chart.ChartConfig;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller.AbstractReportController;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.IOUtils;

/**
 * Sample Report
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Controller
@RequestMapping("/report/sample")
public class SampleReport extends AbstractReportController {

    public static final String REPORT_NAME = "Sample Report";
    public static final String REPORT_TEMPLATE = "/report/sample-report.vm";
    public static final String CHART_TITLE = "Sample Chart";
    public static final int CHART_WIDTH = 640;
    public static final int CHART_HEIGHT = 480;
    public static final String CHART_DATA_URL = "/report/sample/chart-data";

    @Override
    protected void configure() {
        setReportName(REPORT_NAME);
        setReportImageUrl("${contextPath}/resources/images/nsys_logo_avatar.png");
        setReportTemplate(REPORT_TEMPLATE);
        setChartTitle(String.format("%s # 1", CHART_TITLE));
        setChartType(ChartConfig.ChartType.BAR);
        //setChartWidth(CHART_WIDTH);
        //setChartHeight(CHART_HEIGHT);
        setChartDataUrl(CHART_DATA_URL);
        //setChartLegend(false);
        //setChartHoverDetailAxisXTitle("S");
        //setChartAxisXMode(ChartConfig.AxisXMode.CUSTOM_VALUE);
        //setChartAxisXTimeUnit(ChartConfig.TimeUnit.SECOND);

        ChartConfig chart2 = getDefaultChartConfig().clone();
        chart2.setChartTitle(String.format("%s # 2", CHART_TITLE));
        chart2.setChartType(ChartConfig.ChartType.LINE);
        addChartConfig(chart2);

        ChartConfig chart3 = getDefaultChartConfig().clone();
        chart3.setChartTitle(String.format("%s # 3", CHART_TITLE));
        chart3.setChartType(ChartConfig.ChartType.AREA);
        addChartConfig(chart3);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Override
    public ModelAndView showReport(final HttpServletRequest request, final HttpServletResponse response) {
        Map<String, Object> context = new HashMap<String, Object>();
        return render(context, request, response);
    }

    @RequestMapping(value = "/chart-data", method = RequestMethod.GET)
    @ResponseBody
    public String getChartData(final HttpServletRequest request, final HttpServletResponse response) {
        return IOUtils.toString(getServletContext().getResourceAsStream("/resources/data/sample-chart-data.json"));
    }
}
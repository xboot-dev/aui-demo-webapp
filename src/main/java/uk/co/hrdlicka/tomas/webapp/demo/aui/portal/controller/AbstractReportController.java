/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.PortalNavigationSystem;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.chart.ChartConfig;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.StringUtils;

/**
 * Abstract Report Controller
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractReportController extends AbstractPortalController {
    private String reportName;
    private String reportImageUrl;
    private String reportNavigation;
    private String reportActions;
    private String reportTemplate;
    private ChartConfig defaultChartConfig;
    private final List<ChartConfig> chartConfigs = new ArrayList<ChartConfig>();

    @Autowired
    private ServletContext servletContext;

    public static final String DEFAULT_REPORT_NAME = "Undefined Report Name";
    public static final String DEFAULT_REPORT_NAVIGATION = PortalNavigationSystem.REPORT_NAVIGATION;
    public static final String DEFAULT_REPORT_ACTIONS = "report.header.actions";
    public static final String DEFAULT_REPORT_TEMPLATE = "/report/generic-report.vm";
    public static final String DEFAULT_CHART_TEMPLATE = "/report/chart.vm";
    public static final String DEFAULT_CHART_TYPE = ChartConfig.ChartType.LINE;
    public static final int DEFAULT_CHART_WIDTH = 500;
    public static final int DEFAULT_CHART_HEIGHT = 240;

    @PostConstruct
    protected void initialize() {
        setReportName(DEFAULT_REPORT_NAME);
        setReportNavigation(DEFAULT_REPORT_NAVIGATION);
        setReportActions(DEFAULT_REPORT_ACTIONS);
        setReportTemplate(DEFAULT_REPORT_TEMPLATE);

        clearChartConfigs();

        setChartType(DEFAULT_CHART_TYPE);
        setChartWidth(DEFAULT_CHART_WIDTH);
        setChartHeight(DEFAULT_CHART_HEIGHT);
        setChartStack(true);
        setChartLegend(true);
        setChartHoverDetail(true);
        setChartHoverDetailAxisXTitle("x=");
        setChartAxisXMode(ChartConfig.AxisXMode.TIME_SERIES);

        configure();
    }

    protected abstract void configure();

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportImageUrl() {
        return reportImageUrl;
    }

    public void setReportImageUrl(String reportImageUrl) {
        this.reportImageUrl = reportImageUrl;
    }

    public String getReportNavigation() {
        return reportNavigation;
    }

    public void setReportNavigation(String reportNavigation) {
        this.reportNavigation = reportNavigation;
    }

    public String getReportActions() {
        return reportActions;
    }

    public void setReportActions(String reportActions) {
        this.reportActions = reportActions;
    }

    public String getReportTemplate() {
        return reportTemplate;
    }

    public void setReportTemplate(String reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    public String getChartTitle() {
        return defaultChartConfig.getChartTitle();
    }

    public void setChartTitle(String chartTitle) {
        defaultChartConfig.setChartTitle(chartTitle);
    }

    public String getChartType() {
        return defaultChartConfig.getChartType();
    }

    public void setChartType(String chartType) {
        defaultChartConfig.setChartType(chartType);
    }

    public int getChartWidth() {
        return defaultChartConfig.getChartWidth();
    }

    public void setChartWidth(int chartWidth) {
        defaultChartConfig.setChartWidth(chartWidth);
    }

    public int getChartHeight() {
        return defaultChartConfig.getChartHeight();
    }

    public void setChartHeight(int chartHeight) {
        defaultChartConfig.setChartHeight(chartHeight);
    }

    public boolean isChartStack() {
        return defaultChartConfig.isChartStack();
    }

    public void setChartStack(boolean chartStack) {
        defaultChartConfig.setChartStack(chartStack);
    }

    public String getChartDataUrl() {
        return defaultChartConfig.getChartDataUrl();
    }

    public void setChartDataUrl(String chartDataUrl) {
        defaultChartConfig.setChartDataUrl(chartDataUrl);
    }

    public boolean isChartLegend() {
        return defaultChartConfig.isChartLegend();
    }

    public void setChartLegend(boolean chartLegend) {
        defaultChartConfig.setChartLegend(chartLegend);
    }

    public boolean isChartHoverDetail() {
        return defaultChartConfig.isChartHoverDetail();
    }

    public void setChartHoverDetail(boolean chartHoverDetail) {
        defaultChartConfig.setChartHoverDetail(chartHoverDetail);
    }

    public String getChartHoverDetailAxisXTitle() {
        return defaultChartConfig.getChartHoverDetailAxisXTitle();
    }

    public void setChartHoverDetailAxisXTitle(String chartHoverDetailAxisXTitle) {
        defaultChartConfig.setChartHoverDetailAxisXTitle(chartHoverDetailAxisXTitle);
    }

    public String getChartAxisXMode() {
        return defaultChartConfig.getChartAxisXMode();
    }

    public void setChartAxisXMode(String chartAxisXMode) {
        defaultChartConfig.setChartAxisXMode(chartAxisXMode);
    }

    public String getChartAxisXTimeUnit() {
        return defaultChartConfig.getChartAxisXTimeUnit();
    }

    public void setChartAxisXTimeUnit(String chartAxisXTimeUnit) {
        defaultChartConfig.setChartAxisXTimeUnit(chartAxisXTimeUnit);
    }

    public ChartConfig getDefaultChartConfig() {
        return defaultChartConfig;
    }

    public List<ChartConfig> getChartConfigs() {
        return chartConfigs;
    }

    public void addChartConfig(final ChartConfig chartConfig) {
        if (chartConfigs.contains(chartConfig)) {
            return;
        }

        getChartConfigs().add(chartConfig);
    }

    protected void clearChartConfigs() {
        getChartConfigs().clear();
        defaultChartConfig = new ChartConfig();
        addChartConfig(defaultChartConfig);
    }

    public abstract ModelAndView showReport(final HttpServletRequest request, final HttpServletResponse response);

    public ModelAndView render(
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        return render(getReportTemplate(), context, request, response);
    }

    public ModelAndView render(
            final String template,
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        setupContext(context, request, response);

        return super.render(template, context, request, response);
    }

    public String renderFragment(
            final String template,
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        setupContext(context, request, response);

        return super.renderFragment(template, context, request, response);
    }

    protected void setupContext(
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        String imageUrl = parserVariables(getReportImageUrl(), request);
        String reportName = StringUtils.isNullOrEmpty(getReportName()) ? DEFAULT_REPORT_NAME : getReportName();

        List<String> charts = renderCharts(context, request, response);

        setDisplayName(reportName);
        showHeader(imageUrl, getReportActions(), null);
        showNavigation(getReportNavigation());

        context.put("reportName", reportName);
        context.put("charts", charts);

        if (!charts.isEmpty()) {
            context.put("chartContent", charts.get(0));
        }
    }

    protected List<String> renderCharts(
            final Map<String, Object> context,
            final HttpServletRequest request,
            final HttpServletResponse response) {

        List<String> charts = new ArrayList<String>();

        int chartId = 1;

        for (ChartConfig config : getChartConfigs()) {
            context.put("chartId", chartId);
            context.put("chartConfig", config);
            charts.add(super.renderFragment(DEFAULT_CHART_TEMPLATE, context, request, response));
            chartId++;
        }

        context.remove("chartId");
        context.remove("chartConfig");

        return charts;
    }
}
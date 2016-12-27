/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.chart;

/**
 * Chart Config
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class ChartConfig {
    private String chartTitle;
    private String chartType;
    private int chartWidth;
    private int chartHeight;
    private boolean chartStack;
    private String chartDataUrl;
    private boolean chartLegend;
    private boolean chartHoverDetail;
    private String chartHoverDetailAxisXTitle;
    private String chartAxisXMode;
    private String chartAxisXTimeUnit;

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public int getChartWidth() {
        return chartWidth;
    }

    public void setChartWidth(int chartWidth) {
        this.chartWidth = chartWidth;
    }

    public int getChartHeight() {
        return chartHeight;
    }

    public void setChartHeight(int chartHeight) {
        this.chartHeight = chartHeight;
    }

    public boolean isChartStack() {
        return chartStack;
    }

    public void setChartStack(boolean chartStack) {
        this.chartStack = chartStack;
    }

    public String getChartDataUrl() {
        return chartDataUrl;
    }

    public void setChartDataUrl(String chartDataUrl) {
        this.chartDataUrl = chartDataUrl;
    }

    public boolean isChartLegend() {
        return chartLegend;
    }

    public void setChartLegend(boolean chartLegend) {
        this.chartLegend = chartLegend;
    }

    public boolean isChartHoverDetail() {
        return chartHoverDetail;
    }

    public void setChartHoverDetail(boolean chartHoverDetail) {
        this.chartHoverDetail = chartHoverDetail;
    }

    public String getChartHoverDetailAxisXTitle() {
        return chartHoverDetailAxisXTitle;
    }

    public void setChartHoverDetailAxisXTitle(String chartHoverDetailAxisXTitle) {
        this.chartHoverDetailAxisXTitle = chartHoverDetailAxisXTitle;
    }

    public String getChartAxisXMode() {
        return chartAxisXMode;
    }

    public void setChartAxisXMode(String chartAxisXMode) {
        this.chartAxisXMode = chartAxisXMode;
    }

    public String getChartAxisXTimeUnit() {
        return chartAxisXTimeUnit;
    }

    public void setChartAxisXTimeUnit(String chartAxisXTimeUnit) {
        this.chartAxisXTimeUnit = chartAxisXTimeUnit;
    }

    public ChartConfig clone() {
        ChartConfig config = new ChartConfig();
        config.setChartTitle(getChartTitle());
        config.setChartType(getChartType());
        config.setChartWidth(getChartWidth());
        config.setChartHeight(getChartHeight());
        config.setChartStack(isChartStack());
        config.setChartDataUrl(getChartDataUrl());
        config.setChartLegend(isChartLegend());
        config.setChartHoverDetail(isChartHoverDetail());
        config.setChartHoverDetailAxisXTitle(getChartHoverDetailAxisXTitle());
        config.setChartAxisXMode(getChartAxisXMode());
        config.setChartAxisXTimeUnit(getChartAxisXTimeUnit());
        return config;
    }

    public static class ChartType {

        public static final String LINE = "line";
        public static final String BAR = "bar";
        public static final String AREA = "area";
    }

    public static class ChartColor {

        public static final String BLUE = "#3b7fc4";
        public static final String RED = "#d04437";
        public static final String GREEN = "#8eb021";
        public static final String VIOLET = "#654982";
    }

    public static class AxisXMode {

        public static final String TIME_SERIES = "time";
        public static final String CUSTOM_VALUE = "value";
    }

    public static class TimeUnit {

        public static final String CENTISECOND = "centisecond";
        public static final String DECISECOND = "decisecond";
        public static final String SECOND = "second";
        public static final String SECOND_15 = "15 second";
        public static final String MINUTE = "minute";
        public static final String MINUTE_15 = "15 minute";
        public static final String HOUR = "hour";
        public static final String HOUR_2 = "2 hour";
        public static final String HOUR_6 = "6 hour";
        public static final String DAY = "day";
        public static final String WEEK = "week";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
        public static final String DECADE = "decade";
    }
}
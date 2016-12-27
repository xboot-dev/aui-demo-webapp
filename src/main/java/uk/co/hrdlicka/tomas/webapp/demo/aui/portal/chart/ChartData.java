/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * Chart Data
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class ChartData {
    private final List<ChartSeries> series = new ArrayList<ChartSeries>();

    public List<ChartSeries> getSeries() {
        return series;
    }

    public void addSeries(ChartSeries series) {
        getSeries().add(series);
    }
}
/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.chart;

import java.util.ArrayList;
import java.util.List;

/**
 * Chart Series
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public class ChartSeries {
    private String key;
    private String name;
    private String color;
    private final List<ChartPoint<?, ?>> data = new ArrayList<ChartPoint<?, ?>>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<ChartPoint<?, ?>> getData() {
        return data;
    }

    public void addPoint(ChartPoint<?, ?> point) {
        data.add(point);
    }
}
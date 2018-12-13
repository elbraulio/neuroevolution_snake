package com.elbraulio.genetical.experiment;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class PrecisionChart {
    private final Number[] values;
    private final String title;
    private final Number xMax;

    public PrecisionChart(Number[] values, String title, Number xMax) {
        this.values = values;
        this.title = title;
        this.xMax = xMax;
    }


    public void show() {
        double[] x = new double[this.values.length];
        double[] y = new double[this.values.length];
        for (int i = 0; i < y.length; i++) {
            x[i] = i + 1;
            y[i] = this.values[i].doubleValue();
        }
        XYChart chart = new XYChartBuilder()
                .theme(Styler.ChartTheme.XChart)
                .width(800)
                .height(600)
                .build();
        chart.getStyler().setYAxisMin(0d);
        chart.getStyler().setYAxisMax(this.xMax.doubleValue());
        XYSeries seriesLiability = chart.addSeries(
                this.title, x, y
        );
        seriesLiability.setXYSeriesRenderStyle(
                XYSeries.XYSeriesRenderStyle.Line
        );
        new SwingWrapper<>(chart).displayChart();
    }
}


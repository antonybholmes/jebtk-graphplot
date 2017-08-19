/**
 * Copyright 2016 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jebtk.graphplot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.Mathematics;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.core.sys.SysUtils;
import org.jebtk.core.text.TextUtils;
import org.jebtk.graphplot.figure.Axes;
import org.jebtk.graphplot.figure.Axes2D;
import org.jebtk.graphplot.figure.Axis;
import org.jebtk.graphplot.figure.AxisPlotX1;
import org.jebtk.graphplot.figure.AxisPlotY2;
import org.jebtk.graphplot.figure.BarChartErrorBarsLayer;
import org.jebtk.graphplot.figure.BarChartHLayer;
import org.jebtk.graphplot.figure.BarChartStackedLayer;
import org.jebtk.graphplot.figure.BoxWhiskerBoxLayer;
import org.jebtk.graphplot.figure.BoxWhiskerLayer;
import org.jebtk.graphplot.figure.BoxWhiskerMeanLayer;
import org.jebtk.graphplot.figure.BoxWhiskerScatterLayer;
import org.jebtk.graphplot.figure.BoxWhiskerSummary;
import org.jebtk.graphplot.figure.BoxWhiskerSummaryLayer;
import org.jebtk.graphplot.figure.GridLocation;
import org.jebtk.graphplot.figure.HistogramLayer;
import org.jebtk.graphplot.figure.LabelPlotLayer;
import org.jebtk.graphplot.figure.LinePlotLayer;
import org.jebtk.graphplot.figure.MarginFillerH;
import org.jebtk.graphplot.figure.MarginFillerV;
import org.jebtk.graphplot.figure.OutlinePlotLayer;
import org.jebtk.graphplot.figure.PieChartLayer;
import org.jebtk.graphplot.figure.Plot;
import org.jebtk.graphplot.figure.PlotBoxH;
import org.jebtk.graphplot.figure.PlotBoxLayoutH;
import org.jebtk.graphplot.figure.PlotBoxLayoutV;
import org.jebtk.graphplot.figure.PlotBoxV;
import org.jebtk.graphplot.figure.PlotGrid;
import org.jebtk.graphplot.figure.PlotStyle;
import org.jebtk.graphplot.figure.ScatterBestFitPlotLayer;
import org.jebtk.graphplot.figure.ScatterPlotLayer;
import org.jebtk.graphplot.figure.SubFigure;
import org.jebtk.graphplot.figure.heatmap.ClusterHeatMapFillPlotLayer;
import org.jebtk.graphplot.figure.heatmap.ColorMapFillPlotLayer;
import org.jebtk.graphplot.figure.heatmap.ColorMapVFillPlotLayer;
import org.jebtk.graphplot.figure.heatmap.ColumnHierarchicalTreeLayer;
import org.jebtk.graphplot.figure.heatmap.GroupColorBarLayer;
import org.jebtk.graphplot.figure.heatmap.GroupHierarchicalColorBarLayer;
import org.jebtk.graphplot.figure.heatmap.HeatMapFillPlotLayer;
import org.jebtk.graphplot.figure.heatmap.HeatMapGridPlotLayer;
import org.jebtk.graphplot.figure.heatmap.RowHierarchicalTreeLayer;
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
import org.jebtk.math.Linspace;
import org.jebtk.math.cluster.Cluster;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.math.matrix.MatrixOperations;
import org.jebtk.math.matrix.MatrixUtils;
import org.jebtk.math.statistics.HistBin;
import org.jebtk.modern.graphics.colormap.ColorMap;
import org.jebtk.modern.widget.ModernWidget;

// TODO: Auto-generated Javadoc
/**
 * Generates pre-designed plots with sane defaults for
 * most users.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class PlotFactory {

	/** The Constant DEFAULT_HEATMAP_SIZE. */
	public static final int DEFAULT_HEATMAP_SIZE = 
			SettingsService.getInstance().getAsInt("graphplot.heatmap.width");

	/** The Constant BOX_WHISKER_WIDTH. */
	private static final int BOX_WHISKER_WIDTH = 
			SettingsService.getInstance().getAsInt("graphplot.boxwhiskers.width");

	/** The Constant COLOR_BAR_WIDTH. */
	private static final int COLOR_BAR_WIDTH = 30;

	/**
	 * Instantiates a new plot factory.
	 */
	private PlotFactory() {
		// Do nothing
	}

	/**
	 * Create a line plot.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createLinePlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		// First summarize each series

		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		//plot.getPlotLayerZModel().putZ(new LinePlotLayer(series.getName()));

		plot.addStyle(series.getName(), PlotStyle.JOINED);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createLinesPlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		// First summarize each series

		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		//plot.getPlotLayerZModel().putZ(new LinePlotLayer(series.getName()));

		plot.addStyle(series.getName(), PlotStyle.LINES);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createSegmentsPlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		// First summarize each series

		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		//plot.getPlotLayerZModel().putZ(new LinePlotLayer(series.getName()));

		plot.addStyle(series.getName(), PlotStyle.SEGMENTS);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createSplineLinePlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		// First summarize each series

		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		//plot.getPlotLayerZModel().putZ(new SplineLinePlotLayer(series.getName()));

		plot.addStyle(series.getName(), 
				PlotStyle.JOINED_SMOOTH, 
				PlotStyle.SCATTER);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Create a box and whisker plot.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createFilledLinePlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		//plot.getPlotLayerZModel().putZ(new FillPlotLayer(series.getName()));
		//plot.getPlotLayerZModel().putZ(new LinePlotLayer(series.getName()));

		plot.addStyle(series.getName(), 
				PlotStyle.FILLED,
				PlotStyle.JOINED, 
				PlotStyle.SCATTER);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createFilledPlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		plot.addStyle(series.getName(), PlotStyle.FILLED);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createFilledTrapezoidPlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		plot.addStyle(series.getName(), PlotStyle.FILLED_TRAPEZOID);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Creates a line plot and fills in the space between the x axis using
	 * trapezoids.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createFilledTrapezoidLinePlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		plot.addStyle(series.getName(), 
				PlotStyle.FILLED_TRAPEZOID, 
				PlotStyle.JOINED);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createSplineFilledLinePlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		//plot.getPlotLayerZModel().putZ(new FillPlotLayer(series.getName()));
		//plot.getPlotLayerZModel().putZ(new LinePlotLayer(series.getName()));

		plot.setStyle(series.getName(), 
				PlotStyle.FILLED_SMOOTH,
				PlotStyle.JOINED_SMOOTH, 
				PlotStyle.SCATTER);

		axes.setAxisLimitsAutoRound();
	}

	/**
	 * Create a scatter plot.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createScatterPlot(AnnotationMatrix m,
			Axes axes, 
			XYSeries series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);
		
		SysUtils.err().println("scatter factory", m.getRowCount(), series.getName(), plot.getName());

		plot.getAllSeries().add(series);

		plot.setStyle(series.getName(), PlotStyle.SCATTER);
		
		//plot.getPlotLayers().putZ(new ScatterPlotLayer(series.getName()));
		//plot.getPlotLayerZModel().putZ(new ScatterBestFitPlotLayer(series.getName()));


		//axes.setAxisLimitsAutoRound();

		// set some limits, the width of the plot is 2 x n + 1 since
		// we draw each box a 1,3,5 etc

		// Disable the zeroth lines
		axes.getX1Axis().setShowZerothLine(false);
		axes.getY1Axis().setShowZerothLine(false);
	}

	/**
	 * Add a best fit line to an existing plot.
	 *
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createBestFitLinePlot(Axes axes, 
			XYSeries series) {
		Plot plot = axes.getCurrentPlot();

		plot.getPlotLayers().putZ(new ScatterBestFitPlotLayer(series.getName()));
	}

	/**
	 * Create a scatter plot.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createScatterLinePlot(AnnotationMatrix m,
			Axes axes,
			XYSeries series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		plot.getPlotLayers().putZ(new LinePlotLayer(series.getName()));
		plot.getPlotLayers().putZ(new ScatterPlotLayer(series.getName()));

		axes.setAxisLimitsAutoRound();

		// set some limits, the width of the plot is 2 x n + 1 since
		// we draw each box a 1,3,5 etc

		// Disable the zeroth lines
		axes.getX1Axis().setShowZerothLine(false);
		axes.getY1Axis().setShowZerothLine(false);
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createBarPlot(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		// We add error bars if there are multiple values per column
		if (m.getRowCount() > 1) {
			plot.getPlotLayers().putZ(new BarChartErrorBarsLayer());
		}
		
		plot.addStyle(PlotStyle.BAR_PLOT);


		int xs = series.getCount();

		axes.getX1Axis().setLimits(0, xs);

		axes.setInternalPlotSize(xs * BOX_WHISKER_WIDTH, 600);

		List<String> labels = new ArrayList<String>();
		List<Double> ticks = new ArrayList<Double>();

		double x = 0;
		double midx = 0;

		for (XYSeries s : series) {
			midx = x + 0.5;

			labels.add(s.getName());
			ticks.add(midx);

			++x;
		}

		axes.getX1Axis().getTicks().setTicks(ticks);
		axes.getX1Axis().getTicks().getMajorTicks().setLabels(labels);
		axes.getX1Axis().getTicks().getMajorTicks().setRotation(-Mathematics.HALF_PI);
		axes.getX1Axis().getTitle().getFontStyle().setVisible(false); //setText("Series");
		
		// dont draw the x ticks
		axes.getX1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);


		axes.getX1Axis().getGrid().setVisible(false);
		axes.getY1Axis().getGrid().setVisible(false);


		axes.getY1Axis().setLimitsAutoRound(0, MatrixOperations.max(m));


		// The y limits are from what we got earlier

		//axes.getYAxis().autoSetLimits(yMin, yMax);
		axes.getY1Axis().getTitle().setText("Count");
		
		axes.setMargins(100);
		axes.setBottomMargin(autoSetX1LabelMargin(axes));
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createBarPlotH(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		//plot.getPlotLayers().putZ(new BarChartErrorBarsHLayer());
		plot.getPlotLayers().putZ(new BarChartHLayer());


		int xs = series.getCount();

		axes.getY1Axis().setLimits(0, xs);

		axes.setInternalPlotSize(600, xs * BOX_WHISKER_WIDTH);

		List<String> labels = new ArrayList<String>();
		List<Double> ticks = new ArrayList<Double>();

		double x = 0;
		double midx = 0;
		
		for (XYSeries s : series) {
			midx = x + 0.5;

			labels.add(s.getName());
			ticks.add(midx);

			++x;
		}


		axes.getY1Axis().getTicks().setTicks(ticks);
		axes.getY1Axis().getTicks().getMajorTicks().setLabels(labels);
		axes.getY1Axis().getTitle().getFontStyle().setVisible(false);
		
		// dont draw the y ticks
		axes.getY1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		axes.getY1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);

		axes.getX1Axis().getGrid().setVisible(false);
		axes.getY1Axis().getGrid().setVisible(false);

		// The y limits are from what we got earlier

		axes.getX1Axis().setLimitsAutoRound(0, MatrixOperations.max(m));


		// Label the x axis
		axes.getX1Axis().getTitle().setText("Count");

		axes.setMargins(100);
		axes.setLeftMargin(autoSetY1LabelMargin(axes));
	}



	/**
	 * Create a box and whisker plot.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createStackedBarPlot(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		plot.getPlotLayers().putZ(new BarChartStackedLayer());

		int n = m.getColumnCount();

		axes.getX1Axis().setLimits(0, n);

		axes.setInternalPlotSize(n * BOX_WHISKER_WIDTH, 600);
		axes.getX1Axis().getTicks().setTicks(Linspace.evenlySpaced(0.5, n - 0.5, 1));
		axes.getX1Axis().getTicks().getMajorTicks().setLabels(series);
		axes.getX1Axis().getTicks().getMajorTicks().setRotation(Mathematics.HALF_PI);

		// dont draw the x ticks
		axes.getX1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);

		axes.getY1Axis().setLimitsAutoRound(0, MatrixUtils.maxColSum(m));

		//axes.getYAxis().autoSetLimits(yMin, yMax);
		axes.getY1Axis().getTitle().setText("Count");
		axes.getX1Axis().getTitle().setText("Series");
		
		axes.setMargins(100);
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 * @param hist the hist
	 */
	/*
	public static void createHistogram(AnnotationMatrix m,
			Axes axes,
			XYSeries series,
			List<HistBin> hist) {

		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		plot.getPlotLayers().putZ(new HistogramLayer(series.getName()));

		axes.getY1Axis().setLimitsAutoRound(0, NumericalMatrix.max(m));

		axes.getX1Axis().setLimitsAutoRound(0, hist.get(hist.size() - 1).getX() + hist.get(hist.size() - 1).getWidth());
	}
	*/
	
	public static void createHistogram(AnnotationMatrix m,
			Axes axes,
			XYSeries series,
			HistBin[] hist) {

		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);

		plot.getPlotLayers().putZ(new HistogramLayer(series.getName(), hist));

		int max = 0;
		
		for (HistBin bin : hist) {
			max = Math.max(max, bin.getCount());
		}
		
		axes.getY1Axis().setLimitsAutoRound(0, max);
		axes.getX1Axis().setLimitsAutoRound(0, hist[hist.length - 1].getX() + hist[hist.length - 1].getWidth());
		
		axes.getY1Axis().getTitle().setText("Count");
		
		axes.setMargins(100);
	}


	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createPiePlot(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup series) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(series);


		plot.getPlotLayers().putZ(new PieChartLayer());

		axes.getY1Axis().setLimits(0, 1.5);
		axes.getX1Axis().setLimits(0, 1.5);
		
		axes.setInternalPlotSize(500, 500);

		// turn off most of the grid ui
		axes.getX1Axis().getLineStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMajorTicks().getFontStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMinorTicks().getFontStyle().setVisible(false);
		axes.getX1Axis().getGrid().setVisible(false);
		axes.getX1Axis().getTitle().setVisible(false);

		axes.getY1Axis().getLineStyle().setVisible(false);
		axes.getY1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		axes.getY1Axis().getTicks().getMajorTicks().getFontStyle().setVisible(false);
		axes.getY1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axes.getY1Axis().getTicks().getMinorTicks().getFontStyle().setVisible(false);
		axes.getY1Axis().getGrid().setVisible(false);
		axes.getY1Axis().getTitle().setVisible(false);
		
		axes.setMargins(100);
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param series the series
	 */
	public static void createBoxWhiskerSummaryPlot(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup series) {
		Plot plot = axes.createNewPlot();

		plot.getAllSeries().add(series);

		plot.setMatrix(m);

		plot.getPlotLayers().putZ(new BoxWhiskerSummaryLayer());

		axes.getX1Axis().setLimits(0, series.getCount());

		axes.getX1Axis().getTicks().setTicks(Linspace.evenlySpaced(0.5, series.getCount() - 0.5, 1));

		// dont draw the x ticks
		axes.getX1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTitle().setVisible(false);
		axes.getY1Axis().getTitle().setVisible(false);

		// The labels are the series names

		List<String> labels = new ArrayList<String>();

		for (XYSeries s : series) {
			labels.add(s.getName());
		}

		axes.getX1Axis().getTicks().getMajorTicks().setLabels(labels);

		axes.getY1Axis().setLimitsAutoRound(0, MatrixOperations.max(m));
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param seriesGroup the series group
	 */
	public static void createBoxWhiskerPlot(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup seriesGroup) {
		createBoxWhiskerPlot(m,
				axes,
				seriesGroup,
				true,
				false,
				true,
				false);
	}



	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param seriesGroup the series group
	 */
	public static void createBoxWhiskerScatterPlot(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup seriesGroup) {

		createBoxWhiskerPlot(m,
				axes,
				seriesGroup,
				false,
				true,
				false,
				true);
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param seriesGroup the series group
	 * @param showWhiskers the show whiskers
	 * @param showMean the show mean
	 * @param showBox the show box
	 * @param showScatter the show scatter
	 */
	public static void createBoxWhiskerPlot(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup seriesGroup,
			boolean showWhiskers,
			boolean showMean,
			boolean showBox,
			boolean showScatter) { 
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		XYSeriesGroup boxWhiskers = new XYSeriesGroup();

		double x = 0; //0.5;

		for (XYSeries series : seriesGroup) {
			BoxWhiskerSummary summary = new BoxWhiskerSummary(m, series);

			boxWhiskers.add(summary);

			plot.getAllSeries().add(summary);

			plot.getPlotLayers().putZ(new BoxWhiskerLayer(summary.getName(), x, showWhiskers));
			plot.getPlotLayers().putZ(new BoxWhiskerMeanLayer(summary.getName(), x, showMean));
			plot.getPlotLayers().putZ(new BoxWhiskerBoxLayer(summary.getName(), x, showBox));
			plot.getPlotLayers().putZ(new BoxWhiskerScatterLayer(summary.getName(), x, showScatter));

			x++;
		}

		formatBoxWhiskerAxes(m, axes, boxWhiskers);
	}

	/**
	 * Format box whisker axes.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @param boxWhiskers the box whiskers
	 */
	private static void formatBoxWhiskerAxes(AnnotationMatrix m,
			Axes axes,
			XYSeriesGroup boxWhiskers) {

		double yMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;

		for (XYSeries s : boxWhiskers) {
			BoxWhiskerSummary b = (BoxWhiskerSummary)s;

			yMin = Math.min(yMin, b.getLowerOutlier());
			yMax = Math.max(yMax, b.getUpperOutlier());
		}

		yMin = Math.min(0, yMin);
		yMax = Math.max(0, yMax);

		// set some limits, the width of the plot is 2 x n + 1 since
		// we draw each box a 1,3,5 etc

		axes.setInternalPlotSize(boxWhiskers.getCount() * BOX_WHISKER_WIDTH, SubFigure.DEFAULT_HEIGHT);

		axes.getX1Axis().setLimits(0, boxWhiskers.getCount());
		axes.getX1Axis().getTicks().setTicks(Linspace.evenlySpaced(0.5, boxWhiskers.getCount(), 1));

		// dont draw the x ticks
		axes.getX1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTicks().getMajorTicks().setRotation(-Mathematics.HALF_PI);
		axes.getX1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTitle().getFontStyle().setVisible(false);
		axes.getY1Axis().getTitle().setText("Count");



		// The labels are the series names

		List<String> labels = new ArrayList<String>();

		for (XYSeries s : boxWhiskers) {
			BoxWhiskerSummary b = (BoxWhiskerSummary)s;

			labels.add(b.getName());
		}

		axes.getX1Axis().getTicks().getMajorTicks().setLabels(labels);

		//axes.getXAxis().getTicks().getMajorTicks().setRotation(Mathematics.HALF_PI);
		axes.setMargins(100);
		axes.setBottomMargin(autoSetX1LabelMargin(axes));	

		// The y limits are from what we got earlier
		axes.getY1Axis().setLimitsAutoRound(yMin, yMax);
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param subFigure the sub figure
	 * @param axes the axes
	 */
	public static void createHeatMap(AnnotationMatrix m,
			SubFigure subFigure,
			Axes axes) {
		createHeatMap(m, subFigure, axes, XYSeriesGroup.EMPTY_GROUP);
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param seriesGroup the series group
	 */
	public static void createHeatMap(AnnotationMatrix m,
			SubFigure subFigure,
			Axes axes,
			XYSeriesGroup seriesGroup) {
		createHeatMap(m, subFigure, axes, seriesGroup, ColorMap.createBlueWhiteRedMap());
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param seriesGroup the series group
	 * @param colorMap the color map
	 */
	public static void createHeatMap(AnnotationMatrix m,
			SubFigure subFigure,
			Axes axes,
			XYSeriesGroup seriesGroup,
			ColorMap colorMap) {
		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);
		plot.setColorMap(colorMap);
		plot.getAllSeries().add(seriesGroup);

		plot.getPlotLayers().putZ(new HeatMapFillPlotLayer());
		plot.getPlotLayers().putZ(new OutlinePlotLayer());
		plot.getPlotLayers().putZ(new GroupColorBarLayer());

		createHeatMapCommon(m, subFigure, plot, axes, 1600, 1600);
	}


	/**
	 * Creates a new Plot object.
	 *
	 * @param m the m
	 * @param subFigure the sub figure
	 * @param seriesGroup the series group
	 * @param rowCluster the row cluster
	 * @param columnCluster the column cluster
	 */
	public static void createClusterHeatMap(AnnotationMatrix m,
			SubFigure subFigure,
			XYSeriesGroup seriesGroup,
			Cluster rowCluster, 
			Cluster columnCluster) {

		Axes axes = subFigure.getCurrentAxes();

		Plot plot = axes.createNewPlot();

		plot.setMatrix(m);

		plot.getAllSeries().add(seriesGroup);

		plot.getPlotLayers().putZ(new ClusterHeatMapFillPlotLayer(rowCluster, columnCluster));
		plot.getPlotLayers().putZ(new OutlinePlotLayer());
		
		createHeatMapCommon(m, subFigure, plot, axes, 1600, 1600);

		//
		// Row cluster
		//

		if (rowCluster != null) {
			/*
			plot = axes.createNewPlot(BorderLocation.W);
			plot.setMatrix(m);
			plot.setInternalPlotWidth(100);
			plot.getPlotLayerZModel().putZ(new RowHierarchicalTreeLayer(rowCluster, Color.BLACK));
			*/
			
			PlotGrid ca = new PlotGrid(new PlotBoxLayoutH());
			
			axes.addChild(ca, GridLocation.W);
			
			plot = ca.createNewPlot();
			plot.setMatrix(m);
			plot.setInternalPlotWidth(100);
			plot.getPlotLayers().putZ(new RowHierarchicalTreeLayer(rowCluster, Color.BLACK));
			
		}

		//
		// Column cluster
		//

		if (columnCluster != null) {
			PlotGrid ca = new PlotGrid(new PlotBoxLayoutV());
			
			axes.addChild(ca, GridLocation.N);
			
			
			
			plot = ca.createNewPlot();
			plot.setMatrix(m);
			plot.setInternalPlotHeight(100);
			plot.getPlotLayers().putZ(new ColumnHierarchicalTreeLayer(columnCluster, Color.BLACK));
			
			plot = ca.createNewPlot();
			plot.setMatrix(m);
			plot.setInternalPlotHeight(100);
			plot.getPlotLayers().putZ(new GroupHierarchicalColorBarLayer(columnCluster));
			
			// Color bar
			
			//axes = s2.createNewAxes();
			
			//axes.getX1Axis().setVisible(false);
			//axes.getX1Axis().setLimits(0, m.getColumnCount(), 1);
			//axes.getY1Axis().setVisible(false);
			//axes.getY1Axis().setLimits(0, 1);
			//axes.setMargins(0);
			//axes.setInternalPlotHeight(100);
			//plot = axes.createNewPlot();
			//plot.getPlotLayerZModel().putZ(new ColumnHierarchicalTreeLayer(columnCluster, Color.BLACK));
		}


		//plot.getPlotLayerZModel().putZ(new GroupHierarchicalColorBarLayer(columnCluster));


	}

	/**
	 * Shared between heatmaps and clustergrams.
	 *
	 * @param m the m
	 * @param subFigure the sub figure
	 * @param plot the plot
	 * @param axes the axes
	 * @param maxWidth the max width
	 * @param maxHeight the max height
	 */
	private static void createHeatMapCommon(AnnotationMatrix m,
			SubFigure subFigure,
			Plot plot, 
			Axes axes,
			int maxWidth,
			int maxHeight) {

		//plot.getColumnSeriesGroup().add(series);

		axes.setInternalPlotSize(Math.min(maxWidth, m.getColumnCount() * DEFAULT_HEATMAP_SIZE), 
				Math.min(maxHeight, m.getRowCount() * DEFAULT_HEATMAP_SIZE));

		boolean fullView = axes.getInternalPlotSize().getW() >= m.getColumnCount() * DEFAULT_HEATMAP_SIZE;


		axes.getX1Axis().setLimits(0, m.getColumnCount(), 1);
		axes.getX1Axis().getTicks().setTicks(Linspace.evenlySpaced(0.5, m.getColumnCount() - 0.5, 1));
		axes.getX1Axis().getTicks().getMajorTicks().setLabels(m.getColumnNames());
		axes.getX1Axis().getTicks().getMajorTicks().setRotation(-Mathematics.HALF_PI);
		axes.getX1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(true);
		axes.getX1Axis().getTicks().getMajorTicks().getFontStyle().setVisible(true);
		axes.getX1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axes.getX1Axis().getTitle().getFontStyle().setVisible(false);
		axes.getX1Axis().getLineStyle().setVisible(false);
		axes.getX1Axis().getGrid().setVisible(fullView);
		axes.getX1Axis().setVisible(fullView);

		axes.getGridLocation(GridLocation.S).putZ(new PlotBoxV());
		axes.getGridLocation(GridLocation.S).getCurrentLayer().putZ(new AxisPlotX1("Axis X1 Label Plot", axes.getX1Axis()));
		
		
		// If the height of the heatmap is less than the ideal height,
		// turn off labels and the grid.

		fullView = axes.getInternalPlotSize().getH() >= m.getRowCount() * DEFAULT_HEATMAP_SIZE;

		axes.getY1Axis().setLimits(0, m.getRowCount(), 1);
		axes.getY1Axis().setVisible(false);
		axes.getY1Axis().getGrid().setVisible(false);
		//axes.getY1Axis().getTicks().setTicks(Linspace.evenlySpaced(0.5, m.getRowCount() - 0.5, 1));
		//axes.getY1Axis().getTicks().getMajorTicks().setLabels(m.getRowNames());
		//axes.getY1Axis().getTicks().getMajorTicks().getLineStyle().setVisible(false);
		//axes.getY1Axis().getTicks().getMajorTicks().getFontStyle().setVisible(false);
		//axes.getY1Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		//axes.getY1Axis().getTitle().getFontStyle().setVisible(false);
		//autoSetY1LabelMargin(axes);
		
		///if (fullView) {
		//	autoSetY1LabelMargin(axes);
		//}


		axes.getY2Axis().setLimits(0, m.getRowCount(), 1);
		axes.getY2Axis().getTicks().setTicks(Linspace.evenlySpaced(0.5, m.getRowCount() - 0.5, 1));
		
		if (m.getRowAnnotationNames().size() > 0) {
			axes.getY2Axis().getTicks().getMajorTicks().setLabels(m.getRowNames());
		}
		
		axes.getY2Axis().getTicks().getMajorTicks().getLineStyle().setVisible(true);
		axes.getY2Axis().getTicks().getMajorTicks().getFontStyle().setVisible(true);
		axes.getY2Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axes.getY2Axis().getTitle().getFontStyle().setVisible(false);
		axes.getY2Axis().getGrid().setVisible(fullView);
		axes.getY2Axis().setVisible(fullView);
		
		// Lets make room for the v color bar
		axes.getGridLocation(GridLocation.E).putZ(new PlotBoxH());
		axes.getGridLocation(GridLocation.E).getCurrentLayer().putZ(new AxisPlotY2("Axis Y2 Label Plot", axes.getY2Axis()));
		
		//axes.getY1Axis().getLineStyle().setVisible(false);

		if (fullView) {
			plot.getPlotLayers().putZ(new HeatMapGridPlotLayer());
		}

		// Add some space around the plot
		subFigure.setMargins(100);

		
		//axes.getY2Axis().setVisible(true);
		//axes.getY2Axis().setLimits(0, m.getRowCount(), 1);
		//axes.getY2Axis().getTicks().setTicks(Linspace.evenlySpaced(0.5, m.getRowCount() - 0.5, 1));
		//axes.getY2Axis().getTicks().getMajorTicks().setLabels(m.getRowNames());
		//axes.getY2Axis().getTicks().getMinorTicks().getLineStyle().setVisible(false);
		//axes.getY2Axis().getTitle().getFontStyle().setVisible(false);

		//plot.getPlotLayerZModel().putZ(new ColorBarHLayer(axes.getColorMap()));
		//plot.getPlotLayerZModel().putZ(new ColorBarVLayer(axes.getColorMap()));


		//axes.setAxisLimitsAutoRound();
	}


	/**
	 * Creates a new Plot object.
	 *
	 * @param min the min
	 * @param max the max
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param colorMap the color map
	 */
	public static void createColorBar(double min,
			double max,
			SubFigure subFigure,
			Axes axes,
			ColorMap colorMap) {

		// Adjust the layout manager
		//subFigure.setLayout(new SubFigureLayoutColorBar());

		//SubFigure sf = subFigure.getSubFigure(2, SubFigureLocation.S);

		//Axes axes = subFigure.getAxes(2, BorderLocation.S); //sf.getAxes(1);

		Plot plot = axes.newPlot(GridLocation.S);

		plot.setColorMap(colorMap);

		plot.getPlotLayers().putZ(new ColorMapFillPlotLayer());
		//plot.getPlotLayerZModel().putZ(new OutlinePlotLayer());

		Axis axis = axes.getX1Axis();
		
		axis.setLimits(min, max);
		//axis.startEndTicksOnly();
		axis.getTicks().getMajorTicks().getLineStyle().setVisible(true);
		axis.getTicks().getMajorTicks().getFontStyle().setVisible(true);
		axis.getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axis.getTitle().getFontStyle().setVisible(false);
		axis.getLineStyle().setVisible(false);
		axis.getGrid().setVisible(false);

		axis = axes.getY1Axis();
		axis.setLimits(0, 1);
		axis.setVisible(false);

		// Default to no margin to expand fully left and right
		plot.setInternalPlotSize(200, COLOR_BAR_WIDTH);

		axes.getGridLocation(GridLocation.N).putZ(new MarginFillerV(10));
		axes.setMargins(100);
		axes.setBottomMargin(autoSetX1LabelMargin(axes));	
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param min the min
	 * @param max the max
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param colorMap the color map
	 */
	public static void createVColorBar(double min,
			double max,
			SubFigure subFigure,
			Axes axes,
			ColorMap colorMap) {

		// Adjust the layout manager
		//subFigure.setLayout(new SubFigureLayoutColorBar());

		//SubFigure sf = subFigure.getSubFigure(2, SubFigureLocation.S);

		//subFigure.clear(BorderLocation.E);

		//Axes axes = subFigure.createNewAxes(BorderLocation.E); //sf.getAxes(1);

		String name = "Vertical Color Bar Axes";
		
		subFigure.removeByName(name);
		
		Axes2D colorBarAxes = new Axes2D(name);
		
		axes.getGridLocation(GridLocation.E).getCurrentLayer().putZ(colorBarAxes);
		
		Plot plot = new Plot("Color Bar Plot");
		plot.setColorMap(colorMap);
		plot.getPlotLayers().putZ(new ColorMapVFillPlotLayer());
		plot.getPlotLayers().putZ(new OutlinePlotLayer());
		colorBarAxes.addPlot(plot);
		
		colorBarAxes.addPlot(new AxisPlotY2("Color Bar Y2 Axis Label Plot", axes.getY2Axis()), 
				GridLocation.E);
		
		
		Axis axis = colorBarAxes.getY1Axis();
		
		axis.setVisible(false);

		axis = colorBarAxes.getY2Axis();
		axis.setVisible(true);
		axis.setLimits(min, max);
		
		
		
		axis.getTicks().getMajorTicks().getLineStyle().setVisible(true);
		axis.getTicks().getMajorTicks().getFontStyle().setVisible(true);
		axis.getTicks().getMinorTicks().getLineStyle().setVisible(false);
		axis.getTitle().getFontStyle().setVisible(false);
		axis.getLineStyle().setVisible(false);
		axis.getGrid().setVisible(false);

		axis = colorBarAxes.getX1Axis();
		axis.setLimits(0, 1);
		axis.setVisible(false);

		colorBarAxes.setInternalPlotSize(COLOR_BAR_WIDTH, 200);
	
		colorBarAxes.getGridLocation(GridLocation.W).putZ(new MarginFillerH(20));
	}


	/**
	 * Auto set X 1 label margin.
	 *
	 * @param axes the axes
	 * @return the int
	 */
	public static int autoSetX1LabelMargin(Axes axes) {

		int w = 0;

		if (axes.getX1Axis().getVisible()) {
			String text = TextUtils.maxString(axes.getX1Axis().getTicks().getMajorTicks().getLabels());
			
			w = ModernWidget.getStringWidth(axes.getX1Axis().getTicks().getMajorTicks().getFontStyle().getFont(), 
					text);
			
			w += axes.getX1Axis().getTicks().getMajorTicks().getTickSpacing();
			
			if (!axes.getX1Axis().getTicks().getDrawInside()) {
				w += axes.getX1Axis().getTicks().getMajorTicks().getTickSize();
			}
			
			// Increase by 20%
			w = w * 12 / 10;
		}

		//axes.getMargins().setBottom(Math.max(w, MarginProperties.DEFAULT_MARGIN));
		
		System.err.println("x1 margin " + w + " " + MarginProperties.DEFAULT_SIZE);
		
		return Math.max(w, MarginProperties.DEFAULT_SIZE);
	}
	
	/**
	 * Auto set label margin.
	 *
	 * @param axis the axis
	 * @return the int
	 */
	public static int autoSetLabelMargin(Axis axis) {

		int w = 0;

		if (axis.getVisible()) {
			String text = TextUtils.maxString(axis.getTicks().getMajorTicks().getLabels());
			
			w = ModernWidget.getStringWidth(axis.getTicks().getMajorTicks().getFontStyle().getFont(), 
					text);
			
			w += axis.getTicks().getMajorTicks().getTickSpacing();
			
			if (!axis.getTicks().getDrawInside()) {
				w += axis.getTicks().getMajorTicks().getTickSize();
			}
			
			// Increase by 20%
			w = w * 12 / 10;
		}

		//axes.getMargins().setBottom(Math.max(w, MarginProperties.DEFAULT_MARGIN));
		
		return Math.max(w, MarginProperties.DEFAULT_SIZE);
	}

	/**
	 * Auto set Y 1 label margin.
	 *
	 * @param axes the axes
	 * @return the int
	 */
	public static int autoSetY1LabelMargin(Axes axes) {
		int w = 0;

		if (axes.getY1Axis().getVisible()) {
			String text = TextUtils.maxString(axes.getY1Axis().getTicks().getMajorTicks().getLabels());

			w = ModernWidget.getStringWidth(axes.getY1Axis().getTicks().getMajorTicks().getFontStyle().getFont(), 
					text);
		}

		return Math.max(w, MarginProperties.DEFAULT_SIZE);
	}

	/**
	 * Auto set Y 2 label margin.
	 *
	 * @param axes the axes
	 * @return the int
	 */
	public static int autoSetY2LabelMargin(Axes axes) {
		int w;

		String text = TextUtils.maxString(axes.getY2Axis().getTicks().getMajorTicks().getLabels());

		w = ModernWidget.getStringWidth(axes.getY2Axis().getTicks().getMajorTicks().getFontStyle().getFont(), 
				text);

		//axes.getMargins().setRight(Math.max(w, MarginProperties.DEFAULT_SIZE));
		return Math.max(w, MarginProperties.DEFAULT_SIZE);
	}

	/**
	 * Creates a new Plot object.
	 *
	 * @param axes the axes
	 * @param label the label
	 * @param x the x
	 * @param y the y
	 */
	public static void createLabel(Axes axes, String label, double x, double y) {
		axes.getCurrentPlot().getPlotLayers().putZ(new LabelPlotLayer(label, x, y));
	}
}

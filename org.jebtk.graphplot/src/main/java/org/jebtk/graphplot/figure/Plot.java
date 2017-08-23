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
package org.jebtk.graphplot.figure;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Set;

import org.jebtk.core.Mathematics;
import org.jebtk.core.StringId;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.graphplot.figure.heatmap.HeatMapFillPlotLayer;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
import org.jebtk.graphplot.plotbox.PlotBox;
import org.jebtk.graphplot.plotbox.PlotBoxZLayout;
import org.jebtk.graphplot.plotbox.PlotBoxZStorage;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.math.matrix.MatrixEventListener;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;
import org.jebtk.modern.graphics.colormap.ColorMap;

// TODO: Auto-generated Javadoc
/**
 * Represents a 2D Cartesian graph. This
 * class draws basic axes and titles but should
 * be subclassed to provide specific plot functionality.
 *  
 * @author Antony Holmes Holmes
 *
 */
public class Plot extends PlotBoxGraph implements MatrixEventListener, ChangeListener {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The constant NEXT_ID.
	 */
	protected static final StringId NEXT_ID = new StringId("Plot");

	/**
	 * The member column series.
	 */
	private XYSeriesGroup mColumnSeries = new XYSeriesGroup();

	/**
	 * The member axes type.
	 */
	private AxesCombType mAxesType = AxesCombType.X_Y1;

	/**
	 * The layers within a plot.
	 */
	//protected PlotLayerZModel mLayers = new PlotLayerZModel();

	/**
	 * The member m.
	 */
	private AnnotationMatrix mM;

	/**
	 * The member bar width.
	 */
	private double mBarWidth = 0.8;


	/** The m color map. */
	private ColorMap mColorMap = ColorMap.createBlueWhiteRedMap();

	/** The m cache axes. */
	private String mCacheAxes;

	/** The m buffered image. */
	private BufferedImage mBufferedImage;

	/**
	 * Instantiates a new plot.
	 */
	public Plot() {
		this(NEXT_ID.getNextId());
	}

	/**
	 * Instantiates a new plot.
	 *
	 * @param name the name
	 */
	public Plot(String name) {
		super(name, new PlotBoxZStorage(), new PlotBoxZLayout());

		//mLayers.addCanvasListener(this);

		//addCanvasMouseListener(mLayers);

		mColumnSeries.addChangeListener(this);



		/*
		getPlotLayerZModel().setZ(new LinePlotLayer(series), -6000);
		getPlotLayerZModel().setZ(new FillPlotLayer(series), -5000);
		getPlotLayerZModel().setZ(new SplineLinePlotLayer(series, false), -4000);
		getPlotLayerZModel().setZ(new SplineFillPlotLayer(series), -3000);
		getPlotLayerZModel().setZ(new JoinedBarsLayer(series), -2000);
		getPlotLayerZModel().setZ(new ScatterPlotLayer(series), -1000);
		 */
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#getType()
	 */
	@Override
	public String getType() {
		return LayerType.PLOT;
	}

	/**
	 * Sets the XY plot layers visible.
	 *
	 * @param visible the new XY plot layers visible
	 */
	public void setXYPlotLayersVisible(boolean visible) {
		for (int z : this.getZ()) {
			if (z <= ZModel.LOWER_RESERVED_Z) {
				getChild(z).setVisible(visible);
			}
		}
	}

	/**
	 * Sets the xy plot layers visible.
	 *
	 * @param style the style
	 * @param visible the visible
	 */
	public void setXYPlotLayersVisible(PlotStyle style, boolean visible) {
		setXYPlotLayersVisible(false);
	}

	/**
	 * Gets the matrix.
	 *
	 * @return the matrix
	 */
	public AnnotationMatrix getMatrix() {
		return mM;
	}

	/**
	 * Sets the matrix.
	 *
	 * @param m the new matrix
	 */
	@Override
	public void setMatrix(AnnotationMatrix m) {
		if (m != null) {
			mM = m;

			mM.addMatrixListener(this);

			fireChanged();
		}
	}

	/**
	 * Sets the color map.
	 *
	 * @param colorMap the new color map
	 */
	@Override
	public void setColorMap(ColorMap colorMap) {
		if (colorMap != null) {
			mColorMap = colorMap;

			fireChanged();
		}
	}


	/**
	 * Sets the bar width.
	 *
	 * @param barWidth the new bar width
	 */
	public void setBarWidth(double barWidth) {
		mBarWidth = Mathematics.bound(barWidth, 0, 1);

		fireChanged();
	}

	/**
	 * Gets the bar width.
	 *
	 * @return the bar width
	 */
	public double getBarWidth() {
		return mBarWidth;
	}

	/**
	 * Gets the axes type.
	 *
	 * @return the axes type
	 */
	public AxesCombType getAxesType() {
		return mAxesType;
	}

	/**
	 * Sets the z.
	 *
	 * @param layer the new z
	 */
	public void setZ(PlotLayer layer) {
		addChild(layer);
	}


	@Override
	public void plot(Graphics2D g2,
			Dimension offset,
			DrawingContext context,
			Object... params) {
		//SubFigure figure = (SubFigure)params[0];
		//Axes axes = (Axes)params[1];
		
		Figure figure = (Figure)params[0];
		SubFigure subFigure = (SubFigure)params[1];
		Axes axes = (Axes)params[2];
		
		super.plot(g2, offset, context, figure, subFigure, axes, this);
	}

	public void drawPlot(Graphics2D g2,
			DrawingContext context,
			Figure figure, 
			SubFigure subFigure, 
			Axes axes) {
		if (getVisible()) {
			for (int z : getZ()) {
				PlotLayer c = (PlotLayer)getChild(z);

				if (c.getVisible()) {
					//SysUtils.err().println("plot", c.getName(), getMatrix().getRowCount());

					c.drawPlot(g2, context, figure, subFigure, axes, this);
				}
			}
		}
	}

	/**
	 * Aa plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 */
	public void aaPlot(Graphics2D g2,
			DrawingContext context,
			Figure figure,
			SubFigure subFigure,
			Axes axes) {

		Graphics2D g2Temp = ImageUtils.createAAGraphics(g2);

		try {
			drawPlot(g2Temp, context, figure, subFigure, axes);
		} finally {
			g2Temp.dispose();
		}
	}

	/**
	 * Cache plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param figure the figure
	 * @param axes the axes
	 */
	public void cachePlot(Graphics2D g2, 
			DrawingContext context,
			Figure figure,
			SubFigure subFigure, 
			Axes axes) {
		if (context == DrawingContext.PRINT) {
			drawPlot(g2, context, figure, subFigure, axes);
		} else {
			// Create an image version of the canvas and draw that to spped
			// up operations
			if (mBufferedImage == null ||
					mCacheAxes == null || 
					!axes.hashId().equals(mCacheAxes)) {
				// The canvas need only be the size of the available display
				mBufferedImage = ImageUtils.createImage(axes.getPreferredSize());

				Graphics2D g2Temp = ImageUtils.createAAGraphics(mBufferedImage);

				try {
					drawPlot(g2Temp, context, figure, subFigure, axes);
				} finally {
					g2Temp.dispose();
				}

				mCacheAxes = axes.hashId();
			}

			g2.drawImage(mBufferedImage, 0, 0, null);
		}
	}




	/**
	 * Gets the column series group.
	 *
	 * @return the column series group
	 */
	public XYSeriesGroup getAllSeries() {
		return mColumnSeries;
	}

	/**
	 * Returns the current series. If there is no series, a new one will be
	 * automatically created. Equivalent to {@code getAllSeries().getCurrent()).
	 * 
	 * @return		The current series object.
	 */
	public XYSeries getCurrentSeries() {
		return getAllSeries().getCurrent();
	}

	/**
	 * Gets the color map.
	 *
	 * @return the color map
	 */
	public ColorMap getColorMap() {
		return mColorMap;
	}

	/**
	 * Sets the style.
	 *
	 * @param styles the new style
	 */
	@Override
	public void setStyle(Set<PlotBox> used, PlotStyle style, PlotStyle... styles) {
		setStyle(getAllSeries().getCurrent().getName(), style, styles);
	}

	/**
	 * Adds the style.
	 *
	 * @param styles the styles
	 */
	@Override
	public void addStyle(Set<PlotBox> used, PlotStyle style, PlotStyle... styles) {
		addStyle(getAllSeries().getCurrent().getName(), style, styles);
	}

	/**
	 * Sets the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void setStyle(String name, PlotStyle style, PlotStyle... styles) {
		setStyle(this, name, style, styles);
	}

	/**
	 * Adds the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void addStyle(String name, 
			PlotStyle style, 
			PlotStyle... styles) {
		addStyle(this, name, style, styles);
	}

	/**
	 * Sets the style.
	 *
	 * @param plot the plot
	 * @param series the series
	 * @param styles the styles
	 */
	public static void setStyle(Plot plot,
			String series,
			PlotStyle style,
			PlotStyle... styles) {

		//System.err.println("clearing plot " + plot.getName() + " " + plot.getId());

		plot.clear();

		addStyle(plot, series, style, styles);
	}

	/**
	 * Adds the style.
	 *
	 * @param plot the plot
	 * @param series the series
	 * @param styles the styles
	 */
	public static void addStyle(Plot plot,
			String series,
			PlotStyle style,
			PlotStyle... styles) {
		addStyle(plot, series, style);

		for (PlotStyle s : styles) {
			addStyle(plot, series, s);
		}
	}

	public static void addStyle(Plot plot,
			String series,
			PlotStyle style) {

		System.err.println("add style " + style + " " + plot.getName());

		switch(style) {
		case FILLED:
			plot.putZ(new FillPlotLayer(series));
			//plot.getPlotLayerZModel().setZ(new LinePlotLayer(series));
			break;
		case JOINED_SMOOTH_ZERO_ENDS:
			plot.putZ(new SplineLinePlotLayer(series, true));
			break;
		case FILLED_SMOOTH:
			plot.putZ(new SplineFillPlotLayer(series));
			//plot.getPlotLayerZModel().setZ(new SplineLinePlotLayer(series));
			//break;
		case JOINED_SMOOTH:
			plot.putZ(new SplineLinePlotLayer(series, false));
			break;
		case LINES:
			plot.putZ(new LinesPlotLayer(series));
			break;
		case BARS:
			plot.putZ(new BarsLayer(series));
			break;
		case JOINED_BARS:
			plot.putZ(new JoinedBarsLayer(series));
			break;
		case BAR_PLOT:
			plot.putZ(new BarChartLayer(series));
			//plot.mLayers.putZ(new JoinedBarsLayer(series));
			break;
		case SCATTER:
			plot.putZ(new ScatterPlotLayer(series));
			break;
		case FILLED_TRAPEZOID:
			plot.putZ(new FillTrapezoidPlotLayer(series));
			break;
		case SEGMENTS:
			plot.putZ(new SegmentsPlotLayer(series));
			break;
		case HEATMAP:
			plot.putZ(new HeatMapFillPlotLayer());
			plot.putZ(new OutlinePlotLayer());
			break;
		default:
			// Joined
			plot.putZ(new LinePlotLayer(series));
			break;
		}
	}

	/*
	@Override
	public void canvasChanged(ChangeEvent e) {
		fireCanvasChanged();
	}

	@Override
	public void redrawCanvas(ChangeEvent e) {
		fireChanged();
	}

	@Override
	public void canvasScrolled(ChangeEvent e) {
		fireCanvasScrolled();
	}
	*/

	@Override
	public void matrixChanged(ChangeEvent e) {
		fireChanged();
	}

	@Override
	public void changed(ChangeEvent e) {
		fireChanged();
	}

	/**
	 * Gets the x max.
	 *
	 * @param plot the plot
	 * @return the x max
	 */
	public static double getXMax(Plot plot) {
		double ret = Double.MIN_VALUE;

		for (XYSeries g : plot.mColumnSeries) {
			double m = XYSeries.getXMax(plot.getMatrix(), g);

			if (m > ret) {
				ret = m;
			}
		}

		return ret;
	}

	/**
	 * Gets the y1 max.
	 *
	 * @param plot the plot
	 * @return the y1 max
	 */
	public static double getY1Max(Plot plot) {
		double ret = Double.MIN_VALUE;

		for (XYSeries g : plot.mColumnSeries) {
			if (plot.getAxesType() == AxesCombType.X_Y1) {
				double m = XYSeries.getYMax(plot.getMatrix(), g);

				if (m > ret) {
					ret = m;
				}
			}
		}

		return ret;
	}

	/**
	 * Gets the y2 max.
	 *
	 * @param plot the plot
	 * @return the y2 max
	 */
	public static double getY2Max(Plot plot) {
		double ret = Double.MIN_VALUE;

		for (XYSeries g : plot.mColumnSeries) {
			if (plot.getAxesType() == AxesCombType.X_Y2) {
				double m = XYSeries.getYMax(plot.getMatrix(), g);

				if (m > ret) {
					ret = m;
				}
			}
		}

		return ret;
	}

	/**
	 * Gets the x min.
	 *
	 * @param plot the plot
	 * @return the x min
	 */
	public static double getXMin(Plot plot) {
		double ret = Double.MAX_VALUE;

		for (XYSeries g : plot.mColumnSeries) {
			double m = XYSeries.getXMin(plot.getMatrix(), g);

			if (m < ret) {
				ret = m;
			}
		}

		return ret;
	}

	/**
	 * Gets the y1 min.
	 *
	 * @param plot the plot
	 * @return the y1 min
	 */
	public static double getY1Min(Plot plot) {
		double ret = Double.MAX_VALUE;

		for (XYSeries s : plot.mColumnSeries) {
			if (plot.getAxesType() == AxesCombType.X_Y1) {
				double m = XYSeries.getYMin(plot.getMatrix(), s);

				if (m < ret) {
					ret = m;
				}
			}
		}

		return ret;
	}

	/**
	 * Gets the y2 min.
	 *
	 * @param plot the plot
	 * @return the y2 min
	 */
	public static double getY2Min(Plot plot) {
		double ret = Double.MAX_VALUE;

		for (XYSeries g : plot.mColumnSeries) {
			if (plot.getAxesType() == AxesCombType.X_Y2) {
				double m = XYSeries.getYMin(plot.getMatrix(), g);

				if (m < ret) {
					ret = m;
				}
			}
		}

		return ret;
	}
}

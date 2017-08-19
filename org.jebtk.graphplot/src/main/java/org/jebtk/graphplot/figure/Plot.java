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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jebtk.core.Mathematics;
import org.jebtk.core.StringId;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.graphplot.figure.heatmap.HeatMapFillPlotLayer;
import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
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
public class Plot extends MovableLayer implements MatrixEventListener, ChangeListener {

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
	protected PlotLayerZModel mLayers = new PlotLayerZModel();

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
		super(name);

		mLayers.addCanvasListener(this);

		addCanvasMouseListener(mLayers);

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
		for (int z : mLayers) {
			if (z <= ZModel.LOWER_RESERVED_Z) {
				mLayers.getChild(z).setVisible(visible);
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
	public void setMatrix(AnnotationMatrix m) {
		if (m != null) {
			mM = m;

			mM.addMatrixListener(this);

			fireCanvasRedraw();
		}
	}

	/**
	 * Sets the color map.
	 *
	 * @param colorMap the new color map
	 */
	public void setColorMap(ColorMap colorMap) {
		if (colorMap != null) {
			mColorMap = colorMap;

			fireCanvasRedraw();
		}
	}


	/**
	 * Sets the bar width.
	 *
	 * @param barWidth the new bar width
	 */
	public void setBarWidth(double barWidth) {
		mBarWidth = Mathematics.bound(barWidth, 0, 1);

		fireCanvasRedraw();
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
	 * Returns the layer model to control what is displayed on the plot
	 * and in what order.
	 *
	 * @return the plot layer z model
	 */
	public PlotLayerZModel getPlotLayers() {
		return mLayers;
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
		mLayers.putZ(layer);
	}

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param figure the figure
	 * @param axes the axes
	 */
	@Override
	public void plot(Graphics2D g2, 
			DrawingContext context,
			SubFigure figure, 
			Axes axes) {
		//cachePlot(g2, context, figure, axes);
		drawPlot(g2, context, figure, axes);
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
			SubFigure subFigure,
			Axes axes) {

		Graphics2D g2Temp = ImageUtils.createAAGraphics(g2);

		try {
			drawPlot(g2Temp, context, subFigure, axes);
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
			SubFigure figure, 
			Axes axes) {
		if (context == DrawingContext.PRINT) {
			drawPlot(g2, context, figure, axes);
		} else {
			// Create an image version of the canvas and draw that to spped
			// up operations
			if (mBufferedImage == null ||
					mCacheAxes == null || 
					!axes.hashId().equals(mCacheAxes)) {
				// The canvas need only be the size of the available display
				mBufferedImage = ImageUtils.createImage(axes.getCanvasSize());
				
				Graphics2D g2Temp = ImageUtils.createAAGraphics(mBufferedImage);
				
				try {
					drawPlot(g2Temp, context, figure, axes);
				} finally {
					g2Temp.dispose();
				}
				
				mCacheAxes = axes.hashId();
			}
			
			g2.drawImage(mBufferedImage, 0, 0, null);
		}
	}
	
	/**
	 * Draw plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param figure the figure
	 * @param axes the axes
	 */
	public void drawPlot(Graphics2D g2, 
			DrawingContext context,
			SubFigure figure, 
			Axes axes) {
		if (mVisible) {
			for (int z : mLayers) {
				PlotLayer c = mLayers.getChild(z);

				if (c.getVisible()) {
					//SysUtils.err().println("plot", c.getName(), getMatrix().getRowCount());
					
					c.plot(g2, context, figure, axes, this);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.Layer#setFont(java.awt.Font, java.awt.Color)
	 */
	@Override
	public void setFont(Font font, Color color) {
		super.setFont(font, color);
		
		for (int z : mLayers) {
			PlotLayer c = mLayers.getChild(z);

			c.setFont(font, color);
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
	 * Remove all non-reserved layers.
	 */
	public void clear() {
		mLayers.clearUnreservedLayers();
	}

	/**
	 * Sets the style.
	 *
	 * @param styles the new style
	 */
	public void setStyle(PlotStyle... styles) {
		setStyle(getAllSeries().getCurrent().getName(), styles);
	}

	/**
	 * Adds the style.
	 *
	 * @param styles the styles
	 */
	public void addStyle(PlotStyle... styles) {
		addStyle(getAllSeries().getCurrent().getName(), styles);
	}

	/**
	 * Sets the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void setStyle(String name, PlotStyle... styles) {
		setStyle(this, name, styles);
	}

	/**
	 * Adds the style.
	 *
	 * @param name the name
	 * @param styles the styles
	 */
	public void addStyle(String name, PlotStyle... styles) {
		addStyle(this, name, styles);
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
			PlotStyle... styles) {

		//System.err.println("clearing plot " + plot.getName() + " " + plot.getId());

		plot.clear();

		addStyle(plot, series, styles);
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
			PlotStyle... styles) {

		for (PlotStyle style : styles) {
			//System.err.println("add style " + style);

			switch(style) {
			case FILLED:
				plot.mLayers.putZ(new FillPlotLayer(series));
				//plot.getPlotLayerZModel().setZ(new LinePlotLayer(series));
				break;
			case JOINED_SMOOTH_ZERO_ENDS:
				plot.mLayers.putZ(new SplineLinePlotLayer(series, true));
				break;
			case FILLED_SMOOTH:
				plot.mLayers.putZ(new SplineFillPlotLayer(series));
				//plot.getPlotLayerZModel().setZ(new SplineLinePlotLayer(series));
				//break;
			case JOINED_SMOOTH:
				plot.mLayers.putZ(new SplineLinePlotLayer(series, false));
				break;
			case LINES:
				plot.mLayers.putZ(new LinesPlotLayer(series));
				break;
			case BARS:
				plot.mLayers.putZ(new BarsLayer(series));
				break;
			case JOINED_BARS:
				plot.mLayers.putZ(new JoinedBarsLayer(series));
				break;
			case BAR_PLOT:
				plot.mLayers.putZ(new BarChartLayer(series));
				//plot.mLayers.putZ(new JoinedBarsLayer(series));
				break;
			case SCATTER:
				plot.mLayers.putZ(new ScatterPlotLayer(series));
				break;
			case FILLED_TRAPEZOID:
				plot.mLayers.putZ(new FillTrapezoidPlotLayer(series));
				break;
			case SEGMENTS:
				plot.mLayers.putZ(new SegmentsPlotLayer(series));
				break;
			case HEATMAP:
				plot.mLayers.putZ(new HeatMapFillPlotLayer());
				plot.mLayers.putZ(new OutlinePlotLayer());
				break;
			default:
				// Joined
				plot.mLayers.putZ(new LinePlotLayer(series));
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.LayerCanvasListener#canvasChanged(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void canvasChanged(ChangeEvent e) {
		fireCanvasChanged();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.LayerCanvasListener#redrawCanvas(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void redrawCanvas(ChangeEvent e) {
		fireCanvasRedraw();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.LayerCanvasListener#canvasScrolled(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void canvasScrolled(ChangeEvent e) {
		fireCanvasScrolled();
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.math.matrix.MatrixEventListener#matrixChanged(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void matrixChanged(ChangeEvent e) {
		fireCanvasRedraw();
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void changed(ChangeEvent e) {
		fireCanvasRedraw();
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

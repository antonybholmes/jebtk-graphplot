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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jebtk.core.text.TextUtils;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;

// TODO: Auto-generated Javadoc
/**
 * Plot layers control how a plot is visualized within an axes object.
 * 
 * @author Antony Holmes Holmes
 *
 */
public abstract class PlotLayer extends Layer {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The m buffered image. */
	private BufferedImage mBufferedImage;
	
	/** The m cache axes. */
	private String mCacheAxes = null;

	/**
	 * Instantiates a new plot layer.
	 *
	 * @param name the name
	 */
	public PlotLayer(String name) {
		super(name);
	}

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 */
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot) {
		if (context == DrawingContext.SCREEN) {
			aaPlot(g2, context, subFigure, axes, plot);
		} else {
			drawPlot(g2, context, subFigure, axes, plot);
		}
	}
	
	/**
	 * Plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 * @param m the m
	 */
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot, 
			AnnotationMatrix m) {
		if (context == DrawingContext.SCREEN) {
			aaPlot(g2, context, subFigure, axes, plot, m);
		} else {
			drawPlot(g2, context, subFigure, axes, plot, m);
		}
	}

	/**
	 * Cache plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 */
	public void cachePlot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot) {
		if (context == DrawingContext.PRINT) {
			drawPlot(g2, context, subFigure, axes, plot);
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
					drawPlot(g2Temp, context, subFigure, axes, plot);
				} finally {
					g2Temp.dispose();
				}

				mCacheAxes = axes.hashId();
			}

			g2.drawImage(mBufferedImage, 0, 0, null);
		}
	}

	/**
	 * Set the plot for anti-aliased graphics.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 */
	public void aaPlot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot) {

		Graphics2D g2Temp = ImageUtils.createAAGraphics(g2);

		try {
			drawPlot(g2Temp, context, subFigure, axes, plot);
		} finally {
			g2Temp.dispose();
		}
	}
	
	/**
	 * Aa plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 * @param m the m
	 */
	public void aaPlot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot,
			AnnotationMatrix m) {

		Graphics2D g2Temp = ImageUtils.createAAGraphics(g2);

		try {
			drawPlot(g2Temp, context, subFigure, axes, plot, m);
		} finally {
			g2Temp.dispose();
		}
	}

	/**
	 * Draw plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 */
	public void drawPlot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot) {
		drawPlot(g2, context, subFigure, axes, plot, plot.getMatrix());
	}

	

	/**
	 * Cache plot.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 * @param m the m
	 */
	public void cachePlot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot, 
			AnnotationMatrix m) {
		if (context == DrawingContext.PRINT) {
			drawPlot(g2, context, subFigure, axes, plot, m);
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
					drawPlot(g2Temp, context, subFigure, axes, plot, m);
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
	 * @param subFigure the sub figure
	 * @param axes the axes
	 * @param plot the plot
	 * @param m the m
	 */
	public void drawPlot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes,
			Plot plot, 
			AnnotationMatrix m) {
		// Do nothing
	}


	/**
	 * Gets the id.
	 *
	 * @param m the m
	 * @param axes the axes
	 * @return the id
	 */
	protected static String getId(AnnotationMatrix m,
			Axes axes) {
		return TextUtils.join(TextUtils.COLON_DELIMITER, 
				m.hashCode(),
				axes.getMargins(),
				axes.getCanvasSize(),
				axes.getX1Axis().getMin(),
				axes.getX1Axis().getMax(),
				axes.getY1Axis().getMin(),
				axes.getY1Axis().getMax());
	}
}

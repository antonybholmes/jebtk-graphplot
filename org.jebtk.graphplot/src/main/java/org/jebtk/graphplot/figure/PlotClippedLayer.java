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
import java.awt.Graphics2D;

import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;

// TODO: Auto-generated Javadoc
/**
 * Each layer of the graph can respond to changes.
 * 
 * @author Antony Holmes Holmes
 *
 */
public abstract class PlotClippedLayer extends PlotLayer  {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new plot clipped layer.
	 *
	 * @param name the name
	 */
	public PlotClippedLayer(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.PlotLayer#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes, edu.columbia.rdf.lib.bioinformatics.plot.figure.Plot, org.abh.lib.math.matrix.AnnotationMatrix)
	 */
	@Override
	public final void drawPlot(Graphics2D g2,
			DrawingContext context,
			Figure figure,
			SubFigure subFigure,
			Axes axes,
			Plot plot) {

		Graphics2D g2Temp = ImageUtils.clone(g2);

		try {
			g2Temp.clipRect(0,
					0, 
					axes.getInternalPlotSize().getW(), 
					axes.getInternalPlotSize().getH());

			plotClipped(g2Temp, context, figure, subFigure, axes, plot);
		} finally {
			g2Temp.dispose();
		}

		//test
		//g2.setColor(Color.RED);
		//g2.drawRect(0, 0, axes.getInternalPlotSize().getW(), axes.getInternalPlotSize().getH());

	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.PlotLayer#plot(java.awt.Graphics2D, org.graphplot.DrawingContext, org.graphplot.figure.SubFigure, org.graphplot.figure.Axes, org.graphplot.figure.Plot, org.abh.common.math.matrix.AnnotationMatrix)
	 */
	@Override
	public final void drawPlot(Graphics2D g2,
			DrawingContext context,
			Figure figure,
			SubFigure subFigure,
			Axes axes,
			Plot plot,
			AnnotationMatrix m) {
		Graphics2D g2Temp = ImageUtils.clone(g2);

		try {
			g2Temp.clipRect(0,
					0, 
					axes.getInternalPlotSize().getW(), 
					axes.getInternalPlotSize().getH());

			plotClipped(g2Temp, context, figure, subFigure, axes, plot, m);
		} finally {
			g2Temp.dispose();
		}

		//test
		//g2.setColor(Color.RED);
		//g2.drawRect(0, 0, axes.getInternalPlotSize().getW(), axes.getInternalPlotSize().getH());
	}

	/**
	 * Plot clipped.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param figure the figure
	 * @param axes the axes
	 * @param plot the plot
	 */
	public void plotClipped(Graphics2D g2,
			DrawingContext context,
			Figure figure,
			SubFigure subFigure,
			Axes axes,
			Plot plot) {
		plotClipped(g2, context, figure, subFigure, axes, plot, plot.getMatrix());
	}

	/**
	 * Plot clipped.
	 *
	 * @param g2 the g2
	 * @param context the context
	 * @param figure the figure
	 * @param axes the axes
	 * @param plot the plot
	 * @param m the m
	 */
	public void plotClipped(Graphics2D g2,
			DrawingContext context,
			Figure figure,
			SubFigure subFigure,
			Axes axes,
			Plot plot,
			AnnotationMatrix m) {
		if (context == DrawingContext.SCREEN) {
			Graphics2D g2Temp = ImageUtils.createAAGraphics(g2);

			try {
				drawClipped(g2Temp, context, figure, subFigure, axes, plot, m);
			} finally {
				g2Temp.dispose();
			}
		} else {
			drawClipped(g2, context, figure, subFigure, axes, plot, m);
		}
	}

	/**
	 * Draw clipped.
	 *
	 * @param g2 the g 2
	 * @param context the context
	 * @param figure the figure
	 * @param axes the axes
	 * @param plot the plot
	 * @param m the m
	 */
	public void drawClipped(Graphics2D g2,
			DrawingContext context,
			Figure figure,
			SubFigure subFigure, 
			Axes axes, 
			Plot plot,
			AnnotationMatrix m) {
		// TODO Auto-generated method stub

	}
}

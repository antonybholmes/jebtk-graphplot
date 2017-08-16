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
import java.awt.Point;

import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Concrete implementation of Graph2dCanvas for generating
 * scatter plots.
 *
 * @author Antony Holmes Holmes
 */
public class LabelAxesLayer extends AxesLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The m label. */
	protected String mLabel;
	
	/** The m x. */
	protected double mX;
	
	/** The m y. */
	protected double mY;
	
	/** The m offset x pixels. */
	protected int mOffsetXPixels;
	
	/** The m offset y pixels. */
	protected int mOffsetYPixels;

	/**
	 * Instantiates a new scatter plot layer.
	 *
	 * @param name the name
	 * @param x the x
	 * @param y the y
	 */
	public LabelAxesLayer(String name, double x, double y) {
		this(name, x, y, 0, 0);
	}
	
	/**
	 * Instantiates a new label axes layer.
	 *
	 * @param name the name
	 * @param x the x
	 * @param y the y
	 * @param offsetXPixels the offset x pixels
	 * @param offsetYPixels the offset y pixels
	 */
	public LabelAxesLayer(String name, double x, double y, int offsetXPixels, int offsetYPixels) {
		super("Label");
		
		mLabel = name;
		mX = x;
		mY = y;
		mOffsetXPixels = offsetXPixels;
		mOffsetYPixels = offsetYPixels;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.PlotSeriesLayer#plotClipped(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes, edu.columbia.rdf.lib.bioinformatics.plot.figure.Plot, org.abh.lib.math.matrix.AnnotationMatrix, edu.columbia.rdf.lib.bioinformatics.plot.figure.series.XYSeries)
	 */
	@Override
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure figure,
			Axes axes) {
		Point p = axes.toPlotXY1(mX, mY);
		
		g2.setColor(Color.BLACK);
		g2.drawString(mLabel, p.x + mOffsetXPixels, p.y + mOffsetYPixels);
	}
}

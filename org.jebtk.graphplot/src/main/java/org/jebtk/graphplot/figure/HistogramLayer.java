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
import java.awt.Point;

import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.math.statistics.HistBin;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Draws lines between series dots.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class HistogramLayer extends PlotSeriesLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The m hist. */
	private HistBin[] mHist;

	/**
	 * Instantiates a new histogram layer.
	 *
	 * @param series the series
	 * @param hist the hist
	 */
	public HistogramLayer(String series, HistBin[] hist) {
		super("Histogram", series);
		
		mHist = hist;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.PlotSeriesLayer#plotClipped(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes, edu.columbia.rdf.lib.bioinformatics.plot.figure.Plot, org.abh.lib.math.matrix.AnnotationMatrix, edu.columbia.rdf.lib.bioinformatics.plot.figure.series.XYSeries)
	 */
	@Override
	public void plotClipped(Graphics2D g2,
			DrawingContext context,
			SubFigure figure,
			Axes axes,
			Plot plot,
			AnnotationMatrix m,
			XYSeries series) {
		int y1;
		int bwp;
		int h;

		y1 = axes.toPlotY1(0);

		// ignore last point
		for (HistBin bin : mHist) {
			int y = bin.getCount();
			
			Point p1 = axes.toPlotXY1(bin.getX(), y);

			Point p2 = axes.toPlotXY1(bin.getX() + bin.getWidth(), y);

			bwp = p2.x - p1.x;
			
			h = y1 - p1.y;

			if (series.getStyle().getFillStyle().getVisible()) {
				//System.err.println("has " + series.getTitle().getText());

				g2.setColor(series.getStyle().getFillStyle().getColor());

				g2.fillRect(p1.x, p1.y, bwp, h); //h);
			}

			if (series.getStyle().getLineStyle().getVisible()) {
				g2.setStroke(series.getStyle().getLineStyle().getStroke());
				g2.setColor(series.getStyle().getLineStyle().getColor());

				g2.drawRect(p1.x, p1.y, bwp, h);
			}
		}
	}
}

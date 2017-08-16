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

import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * Draw an outline around the plot. The color is determined by the line
 * color of the x axis.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class OutlinePlotLayer extends PlotLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new outline plot layer.
	 */
	public OutlinePlotLayer() {
		super("Outline");
	}
	
	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.PlotClippedLayer#plotClipped(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes, edu.columbia.rdf.lib.bioinformatics.plot.figure.Plot, org.abh.lib.math.matrix.AnnotationMatrix)
	 */
	@Override
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure figure,
			Axes axes,
			Plot plot,
			AnnotationMatrix m) {
		
		int x1 = 0; //axes.getMargins().getLeft();
		int y1 = 0; //axes.getMargins().getTop();

		int w = axes.getInternalPlotSize().getW();
		int h = axes.getInternalPlotSize().getH();
		
		g2.setStroke(axes.getStyle().getLineStyle().getStroke());
		g2.setColor(axes.getStyle().getLineStyle().getColor());
		
		g2.drawRect(x1, y1, w, h);
	}
}

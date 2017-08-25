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
	 * @see org.graphplot.figure.PlotLayer#plot(java.awt.Graphics2D, org.graphplot.DrawingContext, org.graphplot.figure.SubFigure, org.graphplot.figure.Axes, org.graphplot.figure.Plot, org.abh.common.math.matrix.AnnotationMatrix)
	 */
	@Override
	public final void plotContext(Graphics2D g2,
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

			super.plotContext(g2Temp, context, figure, subFigure, axes, plot, m);
		} finally {
			g2Temp.dispose();
		}
	}
}

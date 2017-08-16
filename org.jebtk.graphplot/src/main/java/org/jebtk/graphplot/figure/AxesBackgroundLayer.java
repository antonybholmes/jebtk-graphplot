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

import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Draws the grid on the plot.
 *  
 * @author Antony Holmes Holmes
 *
 */
public class AxesBackgroundLayer extends AxesLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new axes background layer.
	 */
	public AxesBackgroundLayer() {
		super("Background");
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.AxesLayer#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes)
	 */
	@Override
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure figure,
			Axes axes) {
		if (!axes.getStyle().getFillStyle().getVisible()) {
			return;
		}
		
		Color color = axes.getStyle().getFillStyle().getColor();
		
		g2.setColor(color);
		
		int x = 0;
		int y = 0;
		int w = axes.getInternalPlotSize().getW();
		int h = axes.getInternalPlotSize().getH();
		
		g2.fillRect(x, y, w, h);
	}
}

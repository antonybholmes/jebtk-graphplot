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

import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Draws the grid on the plot.
 *  
 * @author Antony Holmes Holmes
 *
 */
public class Grid2dLayer extends AxesLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The constant GRID_Z.
	 */
	public static final int GRID_Z = -300;
	
	/**
	 * Instantiates a new grid2d layer.
	 */
	public Grid2dLayer() {
		super("Grid");
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.AxesLayer#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes)
	 */
	@Override
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure figure,
			Axes axes) {
		int x;
		int y;
		int offset;

		Graphics2D g2Temp = (Graphics2D)g2.create();
		
		//
		// x axis
		//

		Axis axis = axes.getX1Axis();

		offset = axes.getMargins().getTop() + 
				axes.getInternalPlotSize().getH();


		// draw grid lines

		if (axis.getGrid().getVisible()) {
			
			g2Temp.setColor(axis.getGrid().getColor());
			g2Temp.setStroke(axis.getGrid().getStroke());

			for (int i = 0; i < axis.getTicks().getMajorTicks().getTickCount(); ++i) {
				x = axes.toPlotX1(axis.getTicks().getMajorTicks().getTick(i));

				g2Temp.drawLine(x, 
						axes.getMargins().getTop(), 
						x, 
						offset);
			}
		}

		//
		// y axis
		//

		axis = axes.getY1Axis();

		if (axis.getGrid().getVisible()) {
			g2Temp.setColor(axis.getGrid().getColor());
			g2Temp.setStroke(axis.getGrid().getStroke());

			int x2 = axes.getMargins().getLeft() + axes.getInternalPlotSize().getW();

			for (int i = 0; i < axis.getTicks().getMajorTicks().getTickCount(); ++i) {
				y = axes.toPlotY1(axis.getTicks().getMajorTicks().getTick(i));

				g2Temp.drawLine(axes.getMargins().getLeft(), 
						y, 
						x2,
						y);
			}
		}
		
		g2Temp.dispose();
	}
}

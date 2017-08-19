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
 * Draws only the center plot elements assuming you want a layered drawing.
 * 
 * @author Antony Holmes Holmes
 */
public abstract class AxesLayout extends PlotLayout {

	/* (non-Javadoc)
	 * @see org.graphplot.figure.PlotLayout#plot(java.awt.Graphics2D, org.graphplot.DrawingContext, org.graphplot.figure.SubFigure, org.graphplot.figure.Axes, org.graphplot.figure.MovableLayer)
	 */
	@Override
	public void plot(Graphics2D g2, 
			DrawingContext context,
			SubFigure subFigure, 
			Axes axes,
			MovableLayer layer) {

		MovableLayerZModel layerModel = layer.mLocations;

		ZModel<MovableLayer> m = layerModel.getChild(GridLocation.CENTER);

		for (int z : m) {
			MovableLayer l = m.getChild(z);

			Graphics2D g2Temp = (Graphics2D)g2.create();
	
			g2Temp.translate(l.getLocation().getX(), 
					l.getLocation().getY());

			if (l instanceof Axes) {
				l.plot(g2Temp, context, subFigure);
			} else {
				l.plot(g2Temp, context, subFigure, axes);
			}

			g2Temp.dispose();
		}
	}
}

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

import org.jebtk.core.geom.IntDim;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * A Figure is a collection of axes layered on top of each other. In a 
 * simple plot, there will be typically only be one set of axes.
 * 
 * @author Antony Holmes Holmes
 */
public class SubFigureVBoxLayout extends PlotLayout {

	/* (non-Javadoc)
	 * @see org.graphplot.figure.PlotLayout#refresh(org.graphplot.figure.MovableLayer)
	 */
	@Override
	public void refresh(MovableLayer layer) {
		MovableLayerZModel layerModel = layer.mLocations;
		
		
		// Size the middle of the figure
		//layer.setInternalPlotSize(maxWidth(GridLocation.CENTER, layerModel),
		//		sumHeight(GridLocation.CENTER, layerModel));
		
		//int w = layer.getInternalWidth();
		
		ZModel<MovableLayer> m = layerModel.getChild(GridLocation.CENTER);

		int y = 0;

		for (int z : m) {
			MovableLayer a = m.getChild(z);

			if (a.getVisible()) {
				a.updateLocation(0, y);
				//a.updatePlotWidth(w);

				y += a.getInternalPlotSize().getH();
			}
		}
		
		mInternalDim = 
				new IntDim(MovableLayer.maxWidth(m), MovableLayer.sumHeight(m));
		
		mDim = MovableLayer.addMargins(mInternalDim, layer.getMargins());
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.PlotLayout#plot(java.awt.Graphics2D, org.graphplot.DrawingContext, org.graphplot.figure.SubFigure)
	 */
	@Override
	public void plot(Graphics2D g2, 
			DrawingContext context, 
			SubFigure subFigure) {

		MovableLayerZModel layerModel = subFigure.mLocations;

		ZModel<MovableLayer> m = layerModel.getChild(GridLocation.CENTER);
		
		for (int z : m) {
			MovableLayer a = m.getChild(z);

			Graphics2D g2Temp = (Graphics2D)g2.create();

			g2Temp.translate(a.getLocation().getX(), a.getLocation().getY());

			if (a instanceof Axes) {
				a.plot(g2Temp, context, subFigure);
			} else {
				a.plot(g2Temp, context);
			}

			g2Temp.dispose();
		}
	}
}

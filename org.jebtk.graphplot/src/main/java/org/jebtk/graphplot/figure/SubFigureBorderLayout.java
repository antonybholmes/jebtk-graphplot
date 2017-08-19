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
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * A Figure is a collection of axes layered on top of each other. In a 
 * simple plot, there will be typically only be one set of axes.
 * 
 * @author Antony Holmes Holmes
 */
public class SubFigureBorderLayout extends PlotLayout {

	/* (non-Javadoc)
	 * @see org.graphplot.figure.PlotLayout#refresh(org.graphplot.figure.MovableLayer)
	 */
	@Override
	public void refresh(MovableLayer l) {
		MovableLayerZModel layerModel = l.mLocations;

		// Set the margins for the figure
		//setMargins(getMaxTopMargin(layerModel),
		//		getMaxLeftMargin(layerModel),
		//		getMaxBottomMargin(layerModel),
		//		getMaxRightMargin(layerModel));


		MarginProperties margins = l.getMargins();


		// Size the middle of the figure
		//l.setInternalPlotSize(getMaxCenter(layerModel));

		IntDim size = MovableLayer.getMaxCenter(layerModel); //l.getInternalPlotSize();

		int x = margins.getLeft();
		int y = margins.getTop();

		layout(GridLocation.NW,
				x,
				y,
				layerModel);

		x += MovableLayer.getMaxLeftMargin(layerModel);

		layout(GridLocation.N,
				x,
				y,
				layerModel);

		x += size.getW();

		layout(GridLocation.NE,
				x,
				y,
				layerModel);

		y += MovableLayer.getMaxTopMargin(layerModel);

		layout(GridLocation.E,
				x,
				y,
				layerModel);

		y += size.getH();

		layout(GridLocation.SE,
				x,
				y,
				layerModel);

		x -= size.getW();

		layout(GridLocation.S,
				x,
				y,
				layerModel);

		x -= MovableLayer.getMaxLeftMargin(layerModel);

		layout(GridLocation.SW,
				x,
				y,
				layerModel);

		y -= size.getH();

		layout(GridLocation.W,
				x,
				y,
				layerModel);

		layout(GridLocation.CENTER,
				x,
				y,
				layerModel);

		mInternalDim = new IntDim(MovableLayer.getMaxLeftMargin(layerModel) +
				MovableLayer.getMaxRightMargin(layerModel) +
				size.getW(),
				MovableLayer.getMaxTopMargin(layerModel) +
				MovableLayer.getMaxBottomMargin(layerModel) +
				size.getH());
		
		
		mDim = MovableLayer.addMargins(mInternalDim, margins);
	}


	/* (non-Javadoc)
	 * @see org.graphplot.figure.PlotLayout#plot(java.awt.Graphics2D, org.graphplot.DrawingContext, org.graphplot.figure.SubFigure)
	 */
	@Override
	public void plot(Graphics2D g2, 
			DrawingContext context,
			SubFigure subFigure) {

		MovableLayerZModel layerModel = subFigure.mLocations;

		for (GridLocation l : GridLocation.LOCATIONS) {
			plot(g2, subFigure, l, layerModel, context);
		}
	}

	/**
	 * Plot.
	 *
	 * @param g2 the g2
	 * @param subFigure the sub figure
	 * @param l the l
	 * @param layerModel the layer model
	 * @param context the context
	 */
	private void plot(Graphics2D g2,
			SubFigure subFigure,
			GridLocation l,
			MovableLayerZModel layerModel,
			DrawingContext context) {
		ZModel<MovableLayer> m = layerModel.getChild(l);

		if (m.size() > 0) {
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

	/**
	 * Layout.
	 *
	 * @param l the l
	 * @param x the x
	 * @param y the y
	 * @param layerModel the layer model
	 */
	private static void layout(GridLocation l,
			int x,
			int y, 
			MovableLayerZModel layerModel) {

		ZModel<MovableLayer> m = layerModel.getChild(l);

		for (int z : m) {
			MovableLayer a = m.getChild(z);

			a.updateLocation(x, y);
			//a.updatePlotSize(w, h);
		}
	}
}

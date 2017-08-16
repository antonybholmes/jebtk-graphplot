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

// TODO: Auto-generated Javadoc
/**
 * A Figure is a collection of axes layered on top of each other. In a 
 * simple plot, there will be typically only be one set of axes.
 * 
 * @author Antony Holmes Holmes
 */
public class PlotBoxLayoutV extends AxesLayout {

	/* (non-Javadoc)
	 * @see org.graphplot.figure.PlotLayout#refresh(org.graphplot.figure.MovableLayer)
	 */
	@Override
	public void refresh(MovableLayer l) {
		MovableLayerZModel layerModel = l.mLocations;

		//IntDim size = axes.getInternalPlotSize();

		///int w = size.getW();

		//int maxH = sumHeight(GridLocation.CENTER, layerModel);

		//axes.setInternalPlotHeight(maxH);

		ZModel<MovableLayer> m = layerModel.get(GridLocation.CENTER);

		int y = l.getMargins().getTop();

		for (int z : m) {
			MovableLayer a = m.getAtZ(z);

			if (a.getVisible()) {
				a.updateLocation(0, y);
				//a.updatePlotWidth(w);

				y += a.getInternalPlotSize().getH();
			}
		}
	}
}

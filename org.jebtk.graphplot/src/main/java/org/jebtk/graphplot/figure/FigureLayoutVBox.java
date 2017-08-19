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

import org.jebtk.core.geom.IntDim;
import org.jebtk.graphplot.figure.properties.MarginProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class FigureLayoutGrid arranges sub figures in a grid on the plot.
 */
public class FigureLayoutVBox extends FigureLayout {
	
	/** The m internal dim. */
	private IntDim mInternalDim;
	
	/** The m dim. */
	private IntDim mDim;

	/* (non-Javadoc)
	 * @see org.graphplot.figure.FigureLayout#refresh(org.graphplot.figure.Figure)
	 */
	@Override
	public void refresh(Figure figure) {
		SubFigureZModel layerModel = figure.mSubFigureZModel;
		
		MarginProperties margins = figure.getMargins();
		
		int x = margins.getLeft();
		int y = margins.getTop();

		for (int z : layerModel) {
			MovableLayer a = layerModel.getChild(z);

			if (a.getVisible()) {
				a.updateLocation(x, y);
				
				//a.updatePlotWidth(w);

				y += a.getCanvasSize().getH();
			}
		}
		
		mInternalDim = new IntDim(maxWidth(layerModel), sumHeight(layerModel));
		
		mDim = MovableLayer.addMargins(mInternalDim, figure.getMargins());
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.FigureLayout#getInternalCanvasSize(org.graphplot.figure.Figure)
	 */
	@Override
	public IntDim getInternalCanvasSize(Figure figure) {
		return mInternalDim;
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.FigureLayout#getCanvasSize(org.graphplot.figure.Figure)
	 */
	@Override
	public IntDim getCanvasSize(Figure figure) {
		return mDim;
	}
}

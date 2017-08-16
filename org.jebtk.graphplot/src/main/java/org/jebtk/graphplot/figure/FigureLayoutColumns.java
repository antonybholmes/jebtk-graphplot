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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jebtk.core.geom.GeomUtils;
import org.jebtk.core.geom.IntDim;
import org.jebtk.graphplot.figure.properties.MarginProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class FigureLayoutGrid arranges sub figures in columns with an
 * unlimited number of rows.
 * 
 */
public class FigureLayoutColumns extends FigureLayout {
	
	/** The m columns. */
	private int mColumns;
	
	/** The m padding x. */
	private int mPaddingX;
	
	/** The m padding y. */
	private int mPaddingY;

	/** The m dim. */
	private IntDim mDim = GeomUtils.DIM_ZERO;

	/** The m internal dim. */
	private IntDim mInternalDim;


	/**
	 * Instantiates a new figure layout grid.
	 *
	 * @param columns the columns
	 */
	public FigureLayoutColumns( int columns) {
		this(columns, 0, 0);
	}
	
	/**
	 * Instantiates a new figure layout grid.
	 *
	 * @param columns the columns
	 * @param paddingX the padding x
	 * @param paddingY the padding y
	 */
	public FigureLayoutColumns(int columns, 
			int paddingX, 
			int paddingY) {
		mColumns = columns;
		mPaddingX = paddingX;
		mPaddingY = paddingY;
	}



	/* (non-Javadoc)
	 * @see org.graphplot.figure.FigureLayout#refresh(org.graphplot.figure.Figure)
	 */
	@Override
	public void refresh(Figure figure) {
		//
		// Determine the maximum space the grid will occupy
		//

		int w = 0;
		int h = 0;
		int y = 0;
		int x;
		int p = 0;

		figure.mOffsetMap.clear();
		
		MarginProperties margins = figure.getMargins();
		
		SubFigureZModel layerModel = figure.mSubFigureZModel;
		
		List<Integer> zLayers = layerModel.getZLayers();
		
		//System.err.println("refresh " + zLayers);
		
		// determine offsets
		
		Map<Integer, Integer> cOffsets = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rOffsets = new HashMap<Integer, Integer>();
		
		int rows = 0;
		
		while (p < zLayers.size()) {
			for (int c = 0; c < mColumns; ++c) {
				if (p == zLayers.size()) {
					break;
				}

				//w = mPlots.get(p).getGraphProperties().getPlotLayout().getSize().width;
				//h = mPlots.get(p).getGraphProperties().getPlotLayout().getSize().height;

				SubFigure subFigure = layerModel.getAtZ(zLayers.get(p));
				
				w = subFigure.getCanvasSize().getW();
				h = subFigure.getCanvasSize().getH();
				
				if (rOffsets.containsKey(rows)) {
					rOffsets.put(rows, Math.max(rOffsets.get(rows), h));
				} else {
					rOffsets.put(rows, h);
				}
				
				if (cOffsets.containsKey(c)) {
					cOffsets.put(c, Math.max(cOffsets.get(c), w));
				} else {
					cOffsets.put(c, w);
				}

				++p;
			}
			
			++rows;
		}

		p = 0;
		x = margins.getLeft();
		y = margins.getTop();
		
		int maxW = 0;
		
		for (int r = 0; r < rows; ++r) {
			if (p == zLayers.size()) {
				break;
			}
			
			x = 0;

			for (int c = 0; c < mColumns; ++c) {
				if (p == zLayers.size()) {
					break;
				}

				//w = mPlots.get(p).getGraphProperties().getPlotLayout().getSize().width;
				//h = mPlots.get(p).getGraphProperties().getPlotLayout().getSize().height;

				SubFigure subFigure = layerModel.getAtZ(zLayers.get(p));
				
				//System.err.println("ah " + figure.getName() + " " + figure.getPlotSize());

				w = subFigure.getCanvasSize().getW();
				h = subFigure.getCanvasSize().getH();

				int fx = x;
				
				int fy = y;
				
				if (subFigure.getVertAlignment() == FigureVertAlignment.BOTTOM) {
					fy += rOffsets.get(r) - h;
				}
				
				// keep track of where the figure is on the canvas is
				//figure.mOffsetMap.put(subFigure, new IntRect(fx, fy, w, h));
				
				subFigure.updateLocation(fx, fy);

				// add any padding

				x += cOffsets.get(c);
				
				//System.err.println("coffset " + c + " " + cOffsets.get(c));
				
				if (c < mColumns - 1) {
					x += mPaddingX;
				}

				++p;
			}

			if (x > maxW) {
				maxW = x;
			}
			
			y += rOffsets.get(r);

			if (r < rows - 1) {
				y += mPaddingY;
			}

			//mSubPlotLayout.getSize().height; // + mGrid.getPadding();
		}

		w = maxW + figure.getInsets().left + figure.getInsets().right;

		y += figure.getInsets().top + figure.getInsets().bottom;

		mInternalDim = new IntDim(w, y);

		mDim = MovableLayer.addMargins(mInternalDim, margins);
		
		//getCurrentAxes().setInternalPlotSize(w, y);
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

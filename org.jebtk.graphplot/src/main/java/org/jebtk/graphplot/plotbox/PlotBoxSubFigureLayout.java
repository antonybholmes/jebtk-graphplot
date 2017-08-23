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
package org.jebtk.graphplot.plotbox;

import java.awt.Dimension;
import java.awt.Graphics2D;

import org.jebtk.graphplot.figure.SubFigure;
import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxSubFigureLayout extends PlotBoxLayout {

	/**
	 * The member renderer.
	 */
	private SubFigure mSubFigure = null;

	/**
	 * Instantiates a new plot box.
	 *
	 * @param renderer the renderer
	 */
	public PlotBoxSubFigureLayout(SubFigure renderer) {
		mSubFigure = renderer;
	}


	/**
	 * Gets the plot size recursive.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	@Override
	public void plotSize(PlotBox plotBox, Dimension dim) {
		// Call getRenderer() so that we do not recursively call
		// getPlotSizeRecursive through getCanvasSize() in an infinite loop.
		dim.width += mSubFigure.getPreferredSize().width;
		dim.height += mSubFigure.getPreferredSize().height;
	}

	/**
	 * Draw recursive.
	 *
	 * @param g2 the g2
	 * @param plotBox the plot box
	 * @param offset the offset
	 * @param context the context
	 */
	@Override
	public void plot(Graphics2D g2,
			PlotBox plotBox,
			Dimension offset,
			DrawingContext context,
			Object... params) {
		mSubFigure.plot(g2, context);
		
		offset.width += mSubFigure.getPreferredSize().width;
		offset.height += mSubFigure.getPreferredSize().height;
	}


	public SubFigure getSubFigure() {
		return mSubFigure;
	}
}

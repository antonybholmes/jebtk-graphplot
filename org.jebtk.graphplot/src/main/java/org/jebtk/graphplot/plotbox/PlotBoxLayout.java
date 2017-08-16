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
import java.awt.Point;

import org.jebtk.core.geom.IntDim;
import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public abstract class PlotBoxLayout {
	public IntDim getCanvasSize(PlotBox plotBox) {
		Dimension dim = new Dimension(0, 0);

		getPlotSizeRecursive(plotBox, dim);

		return new IntDim(dim);
	}
	
	/**
	 * Gets the plot size recursive.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	public abstract void getPlotSizeRecursive(PlotBox plotBox, Dimension dim);


	public void plot(Graphics2D g2, 
			PlotBox plotBox,
			DrawingContext context) {
		plotRecursive(g2, plotBox, new Point(0, 0), context);
	}

	/**
	 * Draw recursive.
	 *
	 * @param g2 the g2
	 * @param plotBox the plot box
	 * @param offset the offset
	 * @param context the context
	 */
	public abstract void plotRecursive(Graphics2D g2,
			PlotBox plotBox,
			Point offset,
			DrawingContext context);

}

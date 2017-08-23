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

import org.jebtk.core.geom.IntDim;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ModernCanvas;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxCanvasLayout extends PlotBoxLayout {

	private ModernCanvas mCanvas;

	public PlotBoxCanvasLayout(ModernCanvas canvas) {
		mCanvas = canvas;
	}
	
	/**
	 * Gets the plot size recursive.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	@Override
	public void plotSize(PlotBox plot, Dimension dim) {
		IntDim s = mCanvas.getCanvasSize();
		
		// Call getRenderer() so that we do not recursively call
		// getPlotSizeRecursive through getCanvasSize() in an infinite loop.
		dim.width += s.getW();
		dim.height += s.getH();
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
			PlotBox plot,
			Dimension offset,
			DrawingContext context,
			Object... params) {
		
		IntDim s = mCanvas.getCanvasSize();
		
		mCanvas.drawCanvasForeground(g2, context);
		
		offset.width += s.getW();
		offset.height += s.getH();
	}
	
	public ModernCanvas getCanvas() {
		return mCanvas;
	}
}

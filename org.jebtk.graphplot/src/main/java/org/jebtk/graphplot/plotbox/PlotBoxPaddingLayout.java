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

import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxPaddingLayout extends PlotBoxLayout {

	private int mT;
	private int mL;
	private int mB;
	private int mR;


	public PlotBoxPaddingLayout(int t,
			int l,
			int b,
			int r) {
		mT = t;
		mL = l;
		mB = b;
		mR = r;
	}
	
	/**
	 * Gets the plot size recursive.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	@Override
	public void getPlotSizeRecursive(PlotBox plot, Dimension dim) {
		Dimension d = new Dimension(0, 0);

		plot.iterator().next().getPlotSizeRecursive(dim);

		dim.width += d.width + mL + mR;
		dim.height += d.height + mT + mB;
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
	public void plotRecursive(Graphics2D g2,
			PlotBox plot,
			Point offset,
			DrawingContext context) {
		Point tempOffset = new Point(0, 0);

		Graphics2D g2Temp = ImageUtils.clone(g2);
		
		try {
			g2Temp.translate(mL, mT);
			
			plot.iterator().next().plotRecursive(g2Temp, tempOffset, context);
		} finally {
			g2Temp.dispose();
		}
		
		offset.x += tempOffset.x + mL + mR;
		offset.y += tempOffset.y + mT + mB;
	}
}

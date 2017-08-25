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

import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxColumnLayout extends PlotBoxLayout {

	/**
	 * Gets the plot size recursive.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	@Override
	public void plotSize(PlotBox plotBox,
			Dimension dim) {
		int width = 0;
		int height = 0;

		Dimension tmpDim = new Dimension(0, 0);
		
		for (PlotBox child : plotBox) {
			tmpDim.width = 0;
			tmpDim.height = 0;
			
			child.plotSize(tmpDim);

			width += tmpDim.width;
			height = Math.max(height, tmpDim.height);
		}

		dim.width += width;
		dim.height += height;
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
		Graphics2D g2Temp = ImageUtils.clone(g2);

		//subg2.translate(offset.width, offset.height);

		int width = 0;
		int height = 0;

		Dimension tempOffset = new Dimension(0, 0);

		try {
			for (PlotBox child : plotBox) {
				tempOffset.width = 0;
				tempOffset.height = 0;

				child.plot(g2Temp, tempOffset, context, params);

				g2Temp.translate(tempOffset.width, 0);

				width += tempOffset.width;
				height = Math.max(height, tempOffset.height);
			}
		} finally {
			g2Temp.dispose();
		}
		
		offset.width += width;
		offset.height += height;
	}
}

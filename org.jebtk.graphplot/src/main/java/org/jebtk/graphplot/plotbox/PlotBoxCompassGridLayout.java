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

import org.jebtk.graphplot.figure.GridLocation;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxCompassGridLayout extends PlotBoxLayout {

	private int[] mWidths = new int[3];
	private int[] mHeights = new int[3];

	/**
	 * Gets the plot size recursive.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	@Override
	public void getPlotSizeRecursive(PlotBox plot, Dimension dim) {

		PlotBoxCompassGrid gp = (PlotBoxCompassGrid)plot;

		for (int i = 0; i < 3; ++i) {
			mHeights[i] = 0;

			for (int j = 0; j < 3; ++j) {
				GridLocation l = PlotBoxCompassGrid.ROWS[i][j];

				PlotBox child = gp.getChild(l);

				if (child != null) {
					Dimension d = new Dimension(0, 0);

					child.getPlotSizeRecursive(d);

					mHeights[i] = Math.max(mHeights[i], d.height);
				}
			}
		}

		for (int i = 0; i < 3; ++i) {
			mWidths[i] = 0;

			for (int j = 0; j < 3; ++j) {
				GridLocation l = PlotBoxCompassGrid.ROWS[j][i];

				PlotBox child = gp.getChild(l);

				if (child != null) {
					Dimension d = new Dimension(0, 0);

					child.getPlotSizeRecursive(d);

					mWidths[i] = Math.max(mWidths[i], d.width);
				}
			}
		}

		for (int i = 0; i < 3; ++i) {
			dim.width += mWidths[i];
			dim.height += mHeights[i];
		}
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
		//
		// Plot
		//

		PlotBoxCompassGrid gp = (PlotBoxCompassGrid)plot;

		Graphics2D g2Temp = ImageUtils.clone(g2);

		try {
			for (int i = 0; i < 3; ++i) {
				Graphics2D g2Temp2 = ImageUtils.clone(g2Temp);

				try {
					for (int j = 0; j < 3; ++j) {
						GridLocation l = PlotBoxCompassGrid.ROWS[i][j];

						PlotBox child = gp.getChild(l);

						if (child != null) {
							child.plotRecursive(g2Temp2, new Point(0, 0), context);
						}

						g2Temp2.translate(mWidths[j], 0);
					}
				} finally {
					g2Temp2.dispose();
				}

				g2Temp.translate(0, mHeights[i]);
			}
		} finally {
			g2Temp.dispose();
		}


		for (int i = 0; i < 3; ++i) {
			offset.x += mWidths[i];
			offset.y += mHeights[i];
		}
	}
}

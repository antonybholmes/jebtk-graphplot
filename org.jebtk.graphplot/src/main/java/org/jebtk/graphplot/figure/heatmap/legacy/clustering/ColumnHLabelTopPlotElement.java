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
package org.jebtk.graphplot.figure.heatmap.legacy.clustering;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jebtk.core.geom.IntDim;
import org.jebtk.math.cluster.Cluster;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.widget.ModernWidget;


// TODO: Auto-generated Javadoc
/**
 * Sort the labels using the hierarchical tree.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class ColumnHLabelTopPlotElement extends ColumnHLabelBottomPlotElement {
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new column H label top plot element.
	 *
	 * @param matrix the matrix
	 * @param aspectRatio the aspect ratio
	 * @param rootCluster the root cluster
	 * @param color the color
	 * @param charWidth the char width
	 * @param maxRowChars the max row chars
	 */
	public ColumnHLabelTopPlotElement(AnnotationMatrix matrix, 
			IntDim aspectRatio, 
			Cluster rootCluster,
			Color color, 
			int charWidth, 
			int maxRowChars) {
		super(matrix, aspectRatio, rootCluster, color, charWidth, maxRowChars);
	}

	/**
	 * Draw labels.
	 *
	 * @param g2 the g2
	 */
	@Override
	protected void drawLabels(Graphics2D g2) {
		g2.setColor(mColor);

		int xd = (mBlockSize.getW() - ModernWidget.getStringHeight(g2)) / 2;

		int x = BLOCK_SIZE + xd;

		for (String id : mIds) {

			Graphics2D g2Temp = (Graphics2D)g2.create();

			try {
				g2Temp.translate(x, getCanvasSize().getH());
				g2Temp.rotate(-Math.PI / 2.0);
				//g2Temp.translate(0, -xd);

				g2Temp.drawString(id, 0, 0);
			} finally {
				g2Temp.dispose();
			}
			x += mBlockSize.getW();
		}
	}
}

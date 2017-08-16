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
package org.jebtk.graphplot.figure.heatmap.legacy;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jebtk.core.geom.IntDim;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Writes the number of samples that are up and down regulated
 * on the plot.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class CountPlotElement extends RowMatrixPlotElement {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The color.
	 */
	private Color mColor;

	/** The m count groups. */
	private CountGroups mCountGroups;

	/**
	 * Instantiates a new count plot element.
	 *
	 * @param matrix the matrix
	 * @param countGroups the count groups
	 * @param width the width
	 * @param aspectRatio the aspect ratio
	 * @param color the color
	 */
	public CountPlotElement(AnnotationMatrix matrix,
			CountGroups countGroups,
			int width,
			IntDim aspectRatio,
			Color color) {
		super(matrix, width, aspectRatio);

		mCountGroups = countGroups;
		mColor = color;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		g2.setColor(mColor);

		int y1 = 0;
		int y2;

		int offset = (g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;

		for (CountGroup countGroup : mCountGroups) {
			if (countGroup.size() > 0) {
				y1 = countGroup.getStart() * mBlockSize.getH();
				y2 = (countGroup.getEnd() + 1) * mBlockSize.getH();

				g2.drawString(countGroup.toString(), 0, (y1 + y2) / 2 + offset);
			}
		}

		/*
		int y;

		if (count > 0) {
			y = (count * mBlockSize.getH() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;

			String t = Integer.toString(count); // + " features";

			g2.drawString(t, 0, y);
		}
		 */
	}
}

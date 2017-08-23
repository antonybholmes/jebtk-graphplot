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
import java.awt.Dimension;
import java.awt.Graphics2D;

import org.jebtk.core.geom.IntDim;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Displays brackets to indicate how many samples are up and down
 * regulated based on their z-score.
 *
 * @author Antony Holmes Holmes
 */
public class CountBracketLeftPlotElement extends RowMatrixPlotElement {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The count.
	 */
	private int count = 0;

	/** The m color. */
	private Color mColor;

	/**
	 * Instantiates a new count bracket left plot element.
	 *
	 * @param matrix the matrix
	 * @param width the width
	 * @param aspectRatio the aspect ratio
	 * @param color the color
	 */
	public CountBracketLeftPlotElement(AnnotationMatrix matrix,
			int width, 
			IntDim aspectRatio,
			Color color) {
		super(matrix, width, aspectRatio);
		mColor = color;
		count = mMatrix.getRowCount();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, Dimension offset, DrawingContext context, Object... params) {
		g2.setColor(mColor);
		
		int y = 0;
		int w = getPreferredSize().width;
		
		g2.drawLine(0, y, w, y);
		
		y = count * mBlockSize.getH();
		
		g2.drawLine(0, y, w, y);
		
		y = mMatrix.getRowCount() * mBlockSize.getH();
		
		g2.drawLine(0, y, w, y);
		
		g2.drawLine(0, 0, 0, y);
		
		super.plot(g2, offset, context, params);
	}
}

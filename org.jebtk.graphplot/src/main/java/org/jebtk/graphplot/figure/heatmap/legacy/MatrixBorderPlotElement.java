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
 * The class MatrixBorderPlotElement.
 */
public class MatrixBorderPlotElement extends MatrixPlotElement {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new matrix border plot element.
	 *
	 * @param matrix the matrix
	 * @param aspectRatio the aspect ratio
	 */
	public MatrixBorderPlotElement(AnnotationMatrix matrix, 
			IntDim aspectRatio) {
		super(matrix, aspectRatio);
	}
	
	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		drawBorder(g2);
	}
	
	/**
	 * Draw border.
	 *
	 * @param g2 the g2
	 */
	protected void drawBorder(Graphics2D g2) {
		g2.setColor(Color.DARK_GRAY);
		
		
		int w = getCanvasSize().getW();
		int h = getCanvasSize().getH();
		
		g2.drawRect(0, 0, w, h);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvas#getCanvasSize()
	 */
	@Override
	public IntDim getCanvasSize() {
		return new IntDim(mMatrix.getColumnCount() * mBlockSize.getW(), 
				mMatrix.getRowCount() * mBlockSize.getH());
	}
}

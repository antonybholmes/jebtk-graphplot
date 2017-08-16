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

import org.jebtk.core.geom.IntDim;
import org.jebtk.math.matrix.AnnotationMatrix;


// TODO: Auto-generated Javadoc
/**
 * The class RowMatrixPlotElement.
 */
public abstract class RowMatrixPlotElement extends MatrixPlotElement {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new row matrix plot element.
	 *
	 * @param matrix the matrix
	 * @param width the width
	 * @param aspectRatio the aspect ratio
	 */
	public RowMatrixPlotElement(AnnotationMatrix matrix, 
			int width, 
			IntDim aspectRatio) {
		super(matrix, aspectRatio);
		
		setWidth(width);
	}
	
	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		setCanvasSize(width, -1);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvas#getCanvasSize()
	 */
	@Override
	public IntDim getCanvasSize() {
		return new IntDim(super.getCanvasSize().getW(), mMatrix.getRowCount() * mBlockSize.getH());
	}
}

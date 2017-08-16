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

import java.awt.Dimension;

import org.jebtk.core.geom.IntDim;
import org.jebtk.math.matrix.AnnotationMatrix;


// TODO: Auto-generated Javadoc
/**
 * Plot element that handles data from a matrix.
 * 
 * @author Antony Holmes Holmes
 *
 */
public abstract class ColumnMatrixPlotElement extends MatrixPlotElement {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new column matrix plot element.
	 *
	 * @param matrix the matrix
	 * @param aspectRatio the aspect ratio
	 * @param height the height
	 */
	public ColumnMatrixPlotElement(AnnotationMatrix matrix, 
			IntDim aspectRatio,
			int height) {
		super(matrix, aspectRatio);
		
		setHeight(height);
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) {
		setCanvasSize(new Dimension(-1, height));
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvas#getCanvasSize()
	 */
	@Override
	public IntDim getCanvasSize() {
		return new IntDim(mMatrix.getColumnCount() * mBlockSize.getW(), super.getCanvasSize().getH());
	}
}

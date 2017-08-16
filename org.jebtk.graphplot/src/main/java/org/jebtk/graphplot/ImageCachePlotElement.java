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
package org.jebtk.graphplot;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jebtk.core.geom.IntDim;
import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * Cache a drawing as an image to reduce drawing time.
 *
 * @author Antony Holmes Holmes
 */
public class ImageCachePlotElement extends PlotElement {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The member element.
	 */
	private PlotElement mElement;

	/**
	 * The bi.
	 */
	private BufferedImage bi;

	/**
	 * Instantiates a new image cache plot element.
	 *
	 * @param element the element
	 */
	public ImageCachePlotElement(PlotElement element) {
		mElement = element;
		
		cache();
	}
	
	/**
	 * Cache.
	 */
	private void cache() {
		bi = new BufferedImage(mElement.getCanvasSize().getW(), 
				mElement.getCanvasSize().getH(), BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2 = (Graphics2D)bi.getGraphics();
		
		mElement.plot(g2, DrawingContext.SCREEN);
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		g2.drawImage(bi, 0, 0, null);
	}
	
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvas#getCanvasSize()
	 */
	@Override
	public IntDim getCanvasSize() {
		return mElement.getCanvasSize();
	}
}

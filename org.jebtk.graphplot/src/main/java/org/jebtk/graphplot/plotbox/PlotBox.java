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
import java.util.Iterator;

import org.jebtk.graphplot.ModernPlotCanvas;
import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBox extends ModernPlotCanvas implements Iterable<PlotBox> {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	private PlotBoxLayout mLayout = new PlotBoxColumnLayout();
	
	
	public PlotBox(PlotBoxLayout layout) {
		setLayout(layout);
	}
	

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvas#getCanvasSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		Dimension dim = new Dimension(0, 0);

		getPlotSizeRecursive(dim);
		
		zoom2(dim);

		return dim; //new IntDim(dim);
	}
	
	/**
	 * Gets the plot size recursive.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	public void getPlotSizeRecursive(Dimension dim) {
		mLayout.getPlotSizeRecursive(this, dim);
	}


	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		plotRecursive(g2, new Point(0, 0), context);
	}

	/**
	 * Draw recursive.
	 *
	 * @param g2 the g2
	 * @param plotBox the plot box
	 * @param offset the offset
	 * @param context the context
	 */
	public void plotRecursive(Graphics2D g2, 
			Point offset,
			DrawingContext context) {
		mLayout.plotRecursive(g2, this, offset, context);
	}
	
	public void setLayout(PlotBoxLayout layout) {
		setPlotBoxLayout(layout);
	}
	
	public void setPlotBoxLayout(PlotBoxLayout layout) {
		mLayout = layout;
	}
	
	public PlotBoxLayout getPlotBoxLayout() {
		return mLayout;
	}
	
	public void addChild(PlotBox plot) {
		// Do nothing
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return null;
	}

	
}

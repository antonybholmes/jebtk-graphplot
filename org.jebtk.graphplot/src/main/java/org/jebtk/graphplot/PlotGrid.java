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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;

import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * Display a grid of components.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class PlotGrid extends ModernPlotCanvas {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The rows.
	 */
	protected int mRows = 1;
	
	/**
	 * The columns.
	 */
	protected int mColumns = 1;
	
	/**
	 * The id.
	 */
	public int id;

	/**
	 * The renderers.
	 */
	protected List<ModernPlotCanvas> mRenderers;
	
	/**
	 * The plot size.
	 */
	protected Dimension plotSize = new Dimension(0, 0);

	/**
	 * Instantiates a new plot grid.
	 */
	public PlotGrid() {
		// do nothing
	}
	
	/**
	 * Instantiates a new plot grid.
	 *
	 * @param rows the rows
	 * @param columns the columns
	 */
	public PlotGrid(int rows, int columns) {
		setGridLayout(rows, columns);
	}
	
	/**
	 * Sets the grid layout.
	 *
	 * @param rows the rows
	 * @param columns the columns
	 */
	public void setGridLayout(int rows, int columns) {
		mRows = rows;
		mColumns = columns;
		
		//this.plotBoxSize = new Dimension(plotSize.width * columns, plotSize.height * rows);
		
		repaint();
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		if (mRenderers == null) {
			return;
		}
		
		int x = 0;
		int y = 0;

		int c = 0;
		
		boolean stop = false;
		
		for (int i = 0; i < mRows; ++i) {
			
			x = 0;
			
			for (int j = 0; j < mColumns; ++j) {
				if (c == mRenderers.size()) {
					stop = true;
					break;
				}
				
				Graphics2D subg2 = (Graphics2D)g2.create();
				
				subg2.translate(x, y);
				
				mRenderers.get(c).drawCanvasForeground(subg2, context);
				
				subg2.dispose();
				
				x += plotSize.width;
				
				++c;
			}
			
			if (stop) {
				break;
			}
			
			y += plotSize.height;
		}
	}


	/**
	 * Sets the renderers.
	 *
	 * @param renderers the new renderers
	 */
	public void setRenderers(List<ModernPlotCanvas> renderers) {
		mRenderers = renderers;
		
		repaint();
	}
}

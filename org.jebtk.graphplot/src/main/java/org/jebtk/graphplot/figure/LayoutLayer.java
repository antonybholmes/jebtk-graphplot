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
package org.jebtk.graphplot.figure;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.geom.IntDim;


// TODO: Auto-generated Javadoc
/**
 * Some layers can have a layout manager in charge of determining how
 * sub components are layed out.
 * 
 * @author Antony Holmes Holmes
 */
public abstract class LayoutLayer extends MovableLayer {


	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The m layout. */
	protected PlotLayout mLayout;

	/**
	 * Instantiates a new layout layer.
	 *
	 * @param name the name
	 * @param layout the layout
	 */
	public LayoutLayer(String name, PlotLayout layout) {
		super(name);
		
		setLayout(layout);
	}


	/**
	 * Set the layout manager governing how axes are layed out.
	 *
	 * @param layout the new layout
	 */
	public void setLayout(PlotLayout layout) {
		mLayout = layout;
		
		//addCanvasListener(mLayout);
		
		//refresh();
	}
	
	/**
	 * Gets the layout.
	 *
	 * @return the layout
	 */
	public PlotLayout getLayout() {
		return mLayout;
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#getInternalPlotSize()
	 */
	@Override
	public IntDim getInternalPlotSize() {
		return mLayout.getInternalPlotSize(this);
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#getPlotSize()
	 */
	@Override
	public IntDim getCanvasSize() {
		return mLayout.getPlotSize(this);
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#canvasChanged(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void canvasChanged(ChangeEvent e) {
		refresh();
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.MovableLayer#canvasResized(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void canvasResized(ChangeEvent e) {
		refresh();
	}
	
	/**
	 * Refresh.
	 */
	public void refresh() {
		//mLayout.invalidate();
		
		// Resize the canvas if necessary
		
		//mInternalSize = MovableLayer.getMaxCenter(mLocations);
		
		mLayout.refresh(this);
		
		fireCanvasResized();
	}
}

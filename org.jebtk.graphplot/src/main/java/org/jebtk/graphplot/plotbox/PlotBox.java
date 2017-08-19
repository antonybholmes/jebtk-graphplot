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
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jebtk.core.geom.IntPos2D;
import org.jebtk.core.text.Join;
import org.jebtk.graphplot.ModernPlotCanvas;
import org.jebtk.graphplot.figure.GridLocation;
import org.jebtk.graphplot.figure.PlotHashProperty;
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.ImageUtils;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBox extends ModernPlotCanvas implements Iterable<PlotBox>, PlotHashProperty {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static final Iterable<Integer> SINGLE_Z_LAYER = 
			Collections.emptyList();

	private static final Iterable<IntPos2D> EMPTY_POSITIONS = 
			Collections.emptyList();
	
	//private IntPos2D mPos = GeomUtils.INT_POINT_ZERO;

	private PlotBoxLayout mLayout = null;
	
	private Map<String, PlotBox> mNameMap = new HashMap<String, PlotBox>(10);

	private MarginProperties mMargins = MarginProperties.DEFAULT_MARGIN;
	
	
	public PlotBox(String name) {
		this(name, new PlotBoxColumnLayout());
	}
	
	public PlotBox(String name, PlotBoxLayout layout) {
		setName(name);
		
		setLayout(layout);
	}
	
	public MarginProperties getMargins() {
		return mMargins;
	}
	
	/**
	 * Sets the left margin.
	 *
	 * @param margin the new left margin
	 */
	public void setLeftMargin(int margin) {
		setMargins(mMargins.getTop(), 
				margin, 
				mMargins.getBottom(), 
				mMargins.getRight());
	}
	
	/**
	 * Sets the bottom margin.
	 *
	 * @param margin the new bottom margin
	 */
	public void setBottomMargin(int margin) {
		setMargins(mMargins.getTop(), 
				mMargins.getLeft(), 
				margin, 
				mMargins.getRight());
	}
	
	/**
	 * Sets the margins.
	 *
	 * @param s the new margins
	 */
	public void setMargins(int s) {
		setMargins(s, s, s, s);
	}
	
	/**
	 * Sets the margins.
	 *
	 * @param t 	the top margin.
	 * @param l 	the left margin.
	 * @param b 	the bottom margin.
	 * @param r 	the right margin.
	 */
	public void setMargins(int t, int l, int b, int r) {
		setMargins(new MarginProperties(t, l, b, r));
	}
	
	/**
	 * Sets the margins.
	 *
	 * @param margins the new margins
	 */
	public void setMargins(MarginProperties margins) {
		updateMargins(margins);
		
		fireCanvasResized();
	}
	
	/**
	 * Update margins.
	 *
	 * @param margins the margins
	 */
	public void updateMargins(MarginProperties margins) {
		mMargins = margins;
	}
	

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvas#getCanvasSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		Dimension dim = new Dimension(0, 0);

		plotSize(dim);
		
		zoom2(dim);
		
		// Account for padding
		//dim.width += getInsets().left + getInsets().right;
		//dim.height += getInsets().top + getInsets().bottom;
		
		dim.width += mMargins.getLineMargin();
		dim.height += mMargins.getPageMargin();

		return dim; //new IntDim(dim);
	}
	
	/**
	 * Update parameter dim to the current plot size. This is to allow for
	 * recursive updating of the size without generating dimension objects.
	 *
	 * @param plotBox the plot box
	 * @param dim the dim
	 * @return the plot size recursive
	 */
	public void plotSize(Dimension dim) {
		mLayout.plotSize(this, dim);
	}


	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		plot(g2, new Point(0, 0), context);
	}

	/**
	 * Draw recursive.
	 *
	 * @param g2 the g2
	 * @param plotBox the plot box
	 * @param offset the offset
	 * @param context the context
	 */
	public void plot(Graphics2D g2, 
			Point offset,
			DrawingContext context) {
		
		Graphics2D g2Temp = ImageUtils.clone(g2);
		
		try {
			// Translate to account for padding.
			g2Temp.translate(mMargins.getLeft(), mMargins.getTop());
			
			mLayout.plot(g2Temp, this, offset, context);	
		} finally {
			g2Temp.dispose();
		}
		
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
	
	public void addChildByName(PlotBox plot) {
		mNameMap.put(plot.getName(), plot);
		
		fireCanvasResized();
	}
	
	public void addChild(PlotBox plot) {
		addChildByName(plot);
	}
	
	public void addChild(PlotBox plot, int i) {
		addChild(plot);
	}
	
	public void addChild(PlotBox plot, int i, int j) {
		addChild(plot);
	}
	
	public void addChild(PlotBox plot, GridLocation l) {
		addChild(plot);
	}
	
	public void addChild(PlotBox plot, IntPos2D p) {
		addChild(plot);
	}
	
	public PlotBox getChild(int i, int j) {
		return getChild(i);
	}
	
	public PlotBox getChild(int i) {
		return null;
	}
	
	public PlotBox getChild(GridLocation l) {
		return getChild(0);
	}


	public PlotBox getChild(IntPos2D p) {
		return getChild(0);
	}
	
	public PlotBox getChild(String name) {
		return getChild(0);
	}
	
	/*
	public void setPosition(IntPos2D p) {
		mPos = p;
		
		fireCanvasChanged();
	}
	
	public IntPos2D getPosition() {
		return mPos;
	}
	*/
	
	public Iterable<IntPos2D> getPositions() {
		return EMPTY_POSITIONS;
	}
	
	public Iterable<GridLocation> getLocations() {
		return GridLocation.LOCATIONS_LIST;
	}
	
	public String getType() {
		return "plot-box";
	}
	
	public int getChildCount() {
		return 0;
	}
	
	/**
	 * Returns the next available z layer.
	 * 
	 * @return
	 */
	public int getUnusedZ() {
		return Integer.MIN_VALUE;
	}
	
	public Iterable<Integer> getZ() {
		return SINGLE_Z_LAYER;
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return null;
	}
	
	/**
	 * Create a unique hash string for this plot box.
	 * @return
	 */
	@Override
	public String hashId() {
		return Join.onColon().values(getId(),
				getMargins(),
				getCanvasSize())
				.toString();
	}
	
	/**
	 * Find a plotBox in the graph by name.
	 * 
	 * @param name
	 * @return
	 */
	public PlotBox findByName(String name) {
		String ls = name.toLowerCase();
		
		PlotBox ret = null;
		
		Deque<PlotBox> stack = new ArrayDeque<PlotBox>(100);
		
		stack.push(this);
		
		while (!stack.isEmpty()) {
			PlotBox p = stack.pop();
			
			if (p.getName().toLowerCase().contains(ls)) {
				ret = p;
				break;
			}
			
			for (PlotBox c : p) {
				stack.push(c);
			}
		}
		
		return ret;
	}
	
	public PlotBox findByType(String type) {
		String ls = type.toLowerCase();
		
		PlotBox ret = null;
		
		Deque<PlotBox> stack = new ArrayDeque<PlotBox>(100);
		
		stack.push(this);
		
		while (!stack.isEmpty()) {
			PlotBox p = stack.pop();
			
			if (p.getType().toLowerCase().contains(ls)) {
				ret = p;
				break;
			}
			
			for (PlotBox c : p) {
				stack.push(c);
			}
		}
		
		return ret;
	}
}

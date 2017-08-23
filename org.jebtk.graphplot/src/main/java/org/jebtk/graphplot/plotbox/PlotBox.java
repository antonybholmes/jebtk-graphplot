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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jebtk.core.NameProperty;
import org.jebtk.core.event.ChangeListeners;
import org.jebtk.core.geom.IntPos2D;
import org.jebtk.core.sys.SysUtils;
import org.jebtk.graphplot.figure.GridLocation;
import org.jebtk.graphplot.figure.PlotHashProperty;
import org.jebtk.graphplot.figure.PlotStyle;
import org.jebtk.graphplot.figure.properties.MarginProperties;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.graphics.colormap.ColorMap;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public abstract class PlotBox extends ChangeListeners implements Iterable<PlotBox>, NameProperty, PlotHashProperty {

	private static final long serialVersionUID = 1L;

	public static final Iterable<PlotBox> NO_CHILDREN = 
			Collections.emptyList();
	
	private String mName;

	public PlotBox(String name) {
		mName = name;
	}

	@Override
	public String getName() {
		return mName;
	}
	
	public void setStorage(PlotBoxStorage s) {
		
	}
	
	public PlotBoxStorage getStorage() {
		return null;
	}
	
	public void setLayout(PlotBoxLayout layout) {
		
	}
	
	public PlotBoxLayout getPlotBoxLayout() {
		return null;
	}
	
	public MarginProperties getMargins() {
		return MarginProperties.DEFAULT_MARGIN;
	}
	
	/**
	 * Sets the left margin.
	 *
	 * @param margin the new left margin
	 */
	public void setLeftMargin(int margin) {
		
	}
	
	/**
	 * Sets the bottom margin.
	 *
	 * @param margin the new bottom margin
	 */
	public void setBottomMargin(int margin) {
		
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
		
		fireChanged();
	}
	
	/**
	 * Update margins.
	 *
	 * @param margins the margins
	 * @return 
	 */
	public boolean updateMargins(MarginProperties margins) {
		return false;
	}

	public final void plot(Graphics2D g2, 
			DrawingContext context, 
			Object... params) {
		if (getVisible()) {
			plot(g2, new Dimension(0, 0), context, params);
		}
	}
	
	public void plot(Graphics2D g2, 
			Dimension offset,
			DrawingContext context, 
			Object... params) {

		plotSize(offset);
	}
	
	public PlotBox addChildByName(PlotBox plot) {
		return this;
	}
	
	public PlotBox addChild(PlotBox plot) {
		return this;
	}
	
	public PlotBox addChild(PlotBox plot, int i) {
		return this;
	}
	
	public PlotBox addReserved(PlotBox plot, int i) {
		return addChild(plot, i);
	}
	
	public PlotBox addChild(PlotBox plot, int i, int j) {
		return this;
	}
	
	public PlotBox addChild(PlotBox plot, GridLocation l) {
		return this;
	}
	
	public PlotBox addChild(PlotBox plot, IntPos2D p) {
		return this;
	}
	
	public PlotBox putZ(PlotBox plot) {
		return this;
	}
	
	public PlotBox putZ(PlotBox plot, GridLocation l) {
		return this;
	}
	
	public PlotBox getChild(int i, int j) {
		return null;
	}
	
	public PlotBox getChild(int i) {
		return null;
	}
	
	public PlotBox getChild(GridLocation l) {
		return null;
	}

	public PlotBox getChild(IntPos2D p) {
		return null;
	}
	
	public PlotBox getChild(String name) {
		return null;
	}
	
	public Iterable<IntPos2D> getPositions() {
		return null;
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
		return -1;
	}
	
	public Iterable<Integer> getZ() {
		return null;
	}
	
	public void clear() {
		
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return NO_CHILDREN.iterator();
	}
	
	/**
	 * Create a unique hash string for this plot box.
	 * @return
	 */
	@Override
	public abstract String hashId();
	

	public final void setFont(Font font, Color color) {
		Set<PlotBox> used = new HashSet<PlotBox>();
		
		setFont(used, font, color);
	}
	
	public void setFont(Set<PlotBox> used, Font font, Color color) {
		
	}
	

	public void setStyle(String name, PlotStyle style, PlotStyle... styles) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Sets the style.
	 *
	 * @param style the new style
	 */
	public final void setStyle(PlotStyle style, PlotStyle... styles) {
		System.err.println("style " + this.getName());
		
		Set<PlotBox> used = new HashSet<PlotBox>();
		
		setStyle(used, style, styles);
	}
	
	public void setStyle(Set<PlotBox> used, PlotStyle style, PlotStyle... styles) {
		
	}
	
	public void addStyle(String name, PlotStyle style, PlotStyle... styles) {
		// TODO Auto-generated method stub
	}

	public final void addStyle(PlotStyle style, PlotStyle... styles) {
		Set<PlotBox> used = new HashSet<PlotBox>();
		
		addStyle(used, style, styles);
	}
	
	public void addStyle(Set<PlotBox> used, PlotStyle style, PlotStyle... styles) {
		
	}

	public void setMatrix(AnnotationMatrix m) {
		// TODO Auto-generated method stub
	}

	public void setColorMap(ColorMap colorMap) {
		// TODO Auto-generated method stub
	}
	
	public void setVisible(boolean visible) {
		
	}
	
	public boolean getVisible() {
		return true;
	}
	
	/**
	 * Find a plotBox in the graph by name.
	 * 
	 * @param name
	 * @return
	 */
	public PlotBox findByName(String name) {
		String ls = name.toLowerCase();
		
		Deque<PlotBox> stack = new ArrayDeque<PlotBox>(100);
		
		stack.push(this);
		
		while (!stack.isEmpty()) {
			PlotBox p = stack.pop();
			
			System.err.println("fbn " + p.getName());
			
			if (p.getName().toLowerCase().contains(ls)) {
				return p;
			}
			
			for (PlotBox c : p) {
				stack.push(c);
			}
		}
		
		return null;
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
	
	public List<PlotBox> getByType(String type) {
		String ls = type.toLowerCase();
		
		List<PlotBox> ret = new ArrayList<PlotBox>(100);
		
		Deque<PlotBox> stack = new ArrayDeque<PlotBox>(100);
		
		stack.push(this);
		
		while (!stack.isEmpty()) {
			PlotBox p = stack.pop();
			
			if (p.getType().toLowerCase().contains(ls)) {
				ret.add(p);
				break;
			}
			
			for (PlotBox c : p) {
				stack.push(c);
			}
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvas#getCanvasSize()
	 */
	public Dimension getPreferredSize() {
		Dimension dim = new Dimension(0, 0);

		plotSize(dim);

		return dim;
	}
	
	public void plotSize(Dimension d) {
		// Do nothing
	}
	
	public void removeByName(String name) {
		
	}
	
	/*
	@Override
	public int compareTo(PlotBox plot) {
		return mName.compareTo(plot.mName);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof PlotBox) {
			return compareTo((PlotBox)o) == 0;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return mName;
	}
	*/
	

	/**
	 * Produce a stack trace for the plot box.
	 * 
	 * @param plot
	 */
	public static void toStack(PlotBox plot) {
		System.err.println("--------");
		System.err.println(plot.getName());
		System.err.println("--------");
		
		Deque<PlotBox> stack = new ArrayDeque<PlotBox>(100);
		Deque<Integer> levelStack = new ArrayDeque<Integer>(100);
		
		for (PlotBox c : plot) {
			stack.push(c);
			levelStack.push(1);
		}
		
		while (!stack.isEmpty()) {
			PlotBox p = stack.pop();
			int level = levelStack.pop();
			
			SysUtils.err().println(level, p.getName());
			
			int nlevel = level + 1;
			
			for (PlotBox c : p) {
				SysUtils.err().println(c + " my child");
				stack.push(c);
				levelStack.push(nlevel);
			}
		}
		
		System.err.println("========");
	}
	
	public static void childStack(PlotBox plot) {
		System.err.println("--------");
		System.err.println(plot.getName() + plot  + " " + plot.getStorage());
		System.err.println("--------");
		
		for (PlotBox c : plot) {
			SysUtils.err().println(c.getName() + " " + c);
		}
		
		System.err.println("========");
	}
}

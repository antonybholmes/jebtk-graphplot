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

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;
import org.jebtk.core.geom.IntPos2D;
import org.jebtk.graphplot.figure.GridLocation;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public abstract class PlotBoxStorage extends ChangeListeners implements Iterable<PlotBox>, ChangeListener {

	private static final long serialVersionUID = 1L;

	private static final Iterable<Integer> SINGLE_Z_LAYER = 
			Collections.emptyList();

	private static final Iterable<IntPos2D> EMPTY_POSITIONS = 
			Collections.emptyList();
	
	public void addChild(PlotBox plot) {
		plot.addChangeListener(this);
	}
	
	public void addChild(PlotBox plot, int i) {
		addChild(plot);
	}
	
	/**
	 * Add a child to a reserved location.
	 * @param plot
	 * @param i
	 */
	public void addReserved(PlotBox plot, int i) {
		addChild(plot, i);
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
	
	public void putZ(PlotBox plot, GridLocation l) {
		addChild(plot, l);
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
	
	public Iterable<IntPos2D> getPositions() {
		return EMPTY_POSITIONS;
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return PlotBox.NO_CHILDREN.iterator();
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
		
		for (PlotBox c : this) {
			stack.push(c);
		}
		
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
		
		for (PlotBox c : this) {
			stack.push(c);
		}
		
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

	public void clear() {
		fireChanged();
	}

	public void remove(PlotBox plot) {
		// TODO Auto-generated method stub
		
	}

	public <T extends PlotBox> void setChildren(Collection<T> plots) {
		clear();
		
		for (PlotBox c : plots) {
			addChild(c);
		}
	}

	@Override
	public void changed(ChangeEvent e) {
		fireChanged();
	}

	

	
}

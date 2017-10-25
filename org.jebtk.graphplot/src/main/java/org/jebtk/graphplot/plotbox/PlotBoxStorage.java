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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;
import org.jebtk.core.geom.IntPos2D;


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
	
	private Map<Integer, PlotBox> mIdMap = new HashMap<Integer, PlotBox>();
	private Map<String, PlotBox> mNameMap = new TreeMap<String, PlotBox>();

	public void addChildByName(PlotBox plot) {
		plot.addChangeListener(this);
		
		mIdMap.put(plot.getId(), plot);
		mNameMap.put(plot.getName(), plot);
		
		fireChanged();
	}
	
	public void addChild(PlotBox plot, Object... params) {
		addReserved(plot, params);
	}
	
	/**
	 * Add a child to a reserved location.
	 * @param plot
	 * @param i
	 */
	public void addReserved(PlotBox plot, Object... params) {
		addChildByName(plot);
	}
	
	public abstract PlotBox getChild(Object param, Object... params);
	
	public boolean remove(PlotBox plot) {
		return false;
	}
	
	public boolean remove(Object param, Object... params) {
		return false;
	}
	
	public PlotBox getChildByName(String name) {
		return mNameMap.get(name);
	}
	
	public PlotBox getChildById(int id) {
		return mIdMap.get(id);
	}
	
	public int getChildCount() {
		return 0;
	}
	
	public Iterable<String> getNames() {
		return mNameMap.keySet();
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
		mNameMap.clear();
		
		fireChanged();
	}
	
	public void removeByName(String name) {
		remove(mNameMap.get(name));
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

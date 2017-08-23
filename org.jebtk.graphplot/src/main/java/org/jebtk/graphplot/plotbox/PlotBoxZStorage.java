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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxZStorage extends PlotBoxStorage {

	private static final long serialVersionUID = 1L;
	
	private Map<Integer, PlotBox> mMap =
			new TreeMap<Integer, PlotBox>();
	
	private Map<PlotBox, Integer> mKeyMap =
			new HashMap<PlotBox, Integer>();
	
	private int mUnused = 0;
	
	@Override
	public void addChild(PlotBox plot) {
		addChild(plot, getUnusedZ());
	}
	
	@Override
	public void addChild(PlotBox plot, int z) {
		addReserved(plot, z);
		
		// keep track of where we can likely search from to find the next
		// unused z
		mUnused = z + 1;
	}
	
	public void addReserved(PlotBox plot, int z) {
		mMap.put(z, plot);
		mKeyMap.put(plot, z);
	}
	
	@Override
	public int getUnusedZ() {
		for (int i = mUnused; i < Integer.MAX_VALUE; ++i) {
			if (!mMap.containsKey(i)) {
				
				return i;
			}
		}
		
		return 0;
	}
	
	@Override
	public Iterable<Integer> getZ() {
		return mMap.keySet();
	}
	
	@Override
	public PlotBox getChild(int z) {
		return mMap.get(z);
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return mMap.values().iterator();
	}
	
	@Override
	public void clear() {
		mMap.clear();
		
		super.clear();
	}
	
	@Override
	public void remove(PlotBox plot) {
		if (mKeyMap.containsKey(plot)) {
			mMap.remove(mKeyMap.get(plot));
			mKeyMap.remove(plot);
		}
	}
}

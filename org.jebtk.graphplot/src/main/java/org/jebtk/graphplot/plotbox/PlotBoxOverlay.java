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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxOverlay extends PlotBox {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static final Map<Integer, PlotBox> mMap =
			new TreeMap<Integer, PlotBox>();

	public PlotBoxOverlay() {
		super(new PlotBoxOverlayLayout());
	}
	
	public void add(PlotBox plot) {
		add(plot, getUnusedZ());
	}
	
	public void add(PlotBox plot, int z) {
		putZ(plot, z);
	}
	
	public void putZ(PlotBox plot) {
		putZ(plot, getUnusedZ());
	}
	
	public void putZ(PlotBox plot, int z) {
		mMap.put(z, plot);
	}
	

	public int getUnusedZ() {
		for (int i = 1; i < Integer.MAX_VALUE; ++i) {
			if (!mMap.containsKey(i)) {
				return i;
			}
		}
		
		return 1;
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return mMap.values().iterator();
	}
}

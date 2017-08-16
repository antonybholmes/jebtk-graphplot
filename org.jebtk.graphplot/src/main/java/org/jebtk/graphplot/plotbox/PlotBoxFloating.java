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

import org.jebtk.core.geom.IntPos2D;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxFloating extends PlotBox {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static final Map<IntPos2D, PlotBox> mMap =
			new HashMap<IntPos2D, PlotBox>();

	public PlotBoxFloating() {
		super(new PlotBoxFloatingLayout());
	}
	
	public void add(PlotBox plot, IntPos2D l) {
		mMap.put(l, plot);
	}

	@Override
	public Iterator<PlotBox> iterator() {
		return mMap.values().iterator();
	}

	public Iterable<IntPos2D> getLocations() {
		return mMap.keySet();
	}

	public PlotBox getChild(IntPos2D p) {
		return mMap.get(p);
	}
}

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

import org.jebtk.graphplot.figure.GridLocation;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxCompassGridStorage extends PlotBoxStorage {

	private static final long serialVersionUID = 1L;


	public static final GridLocation[][] ROWS =
		{{GridLocation.NW, GridLocation.N, GridLocation.NE}, 
				{GridLocation.W, GridLocation.CENTER, GridLocation.E}, 
				{GridLocation.SW, GridLocation.S, GridLocation.SE}};
	
	
	private Map<GridLocation, PlotBox> mMap =
			new TreeMap<GridLocation, PlotBox>();

	@Override
	public void addChild(PlotBox plot, GridLocation l) {
		mMap.put(l, plot);
		plot.addChangeListener(this);
		
		fireChanged();
	}
	
	@Override
	public void addChild(PlotBox plot) {
		addChild(plot, GridLocation.CENTER);
	}
	
	@Override
	public PlotBox getChild(int i) {
		return getChild(GridLocation.CENTER);
	}
	
	@Override
	public PlotBox getChild(int i, int j) {
		return getChild(ROWS[i][j]);
	}
	
	@Override
	public PlotBox getChild(GridLocation l) {
		return mMap.get(l);
	}
	
	@Override
	public Iterator<PlotBox> iterator() {
		return mMap.values().iterator();
	}

	@Override
	public int getChildCount() {
		return mMap.size();
	}
}

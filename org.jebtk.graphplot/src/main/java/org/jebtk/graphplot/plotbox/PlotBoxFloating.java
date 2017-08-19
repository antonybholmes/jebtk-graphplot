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

import org.jebtk.core.StringId;
import org.jebtk.core.geom.GeomUtils;
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

	private static final StringId NEXT_ID = new StringId("Plot Box Floating");
	
	public PlotBoxFloating() {
		super(NEXT_ID.getNextId(), new PlotBoxFloatingLayout());
	}
	
	@Override
	public void addChild(PlotBox plot) {
		addChild(plot, GeomUtils.INT_POINT_ZERO);
	}
	
	@Override
	public void addChild(PlotBox plot, IntPos2D p) {
		mMap.put(p, plot);
		
		addChildByName(plot);
	}

	@Override
	public Iterator<PlotBox> iterator() {
		return mMap.values().iterator();
	}

	@Override
	public Iterable<IntPos2D> getPositions() {
		return mMap.keySet();
	}

	@Override
	public PlotBox getChild(IntPos2D p) {
		return mMap.get(p);
	}
	
	@Override
	public int getChildCount() {
		return mMap.size();
	}
}

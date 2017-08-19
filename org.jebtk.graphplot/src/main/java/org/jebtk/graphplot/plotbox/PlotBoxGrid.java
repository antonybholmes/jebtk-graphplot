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

import org.jebtk.core.StringId;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxGrid extends PlotBox {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private static final StringId NEXT_ID = new StringId("Plot Box Grid");
	
	private PlotBox[][] mLocations;

	
	
	public PlotBoxGrid(int rows, int columns) {
		super(NEXT_ID.getNextId(), new PlotBoxGridLayout(rows, columns));
		
		mLocations = new PlotBox[rows][columns];
	}

	@Override
	public void addChild(PlotBox plot, int row, int col) {
		mLocations[row][col] = plot;
		
		addChildByName(plot);
	}
	
	public void addChild(PlotBox plot) {
		addChild(plot, 0, 0);
	}
	
	@Override
	public PlotBox getChild(int i, int j) {
		return mLocations[i][j];
	}
	
	@Override
	public PlotBox getChild(int i) {
		return getChild(i, 0);
	}

	@Override
	public Iterator<PlotBox> iterator() {
		return new PlotBoxGridIterator<PlotBox>(mLocations);
	}

	@Override
	public int getChildCount() {
		return mLocations.length * mLocations[0].length;
	}
	
}

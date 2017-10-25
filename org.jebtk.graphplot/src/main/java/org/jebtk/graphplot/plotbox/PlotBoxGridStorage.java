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

import org.jebtk.core.geom.GeomUtils;
import org.jebtk.core.geom.IntPos2D;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public class PlotBoxGridStorage extends PlotBoxStorage {

	private static final long serialVersionUID = 1L;

	private PlotBox[][] mLocations;


	public PlotBoxGridStorage(int rows, int columns) {
		mLocations = new PlotBox[rows][columns];
	}

	@Override
	public void addReserved(PlotBox plot, Object... params) {
		int row = 0;
		int col = 0;

		if (params.length > 1) {
			if (params[0] instanceof Integer) {
				row = (int)params[0];
			}

			if (params[1] instanceof Integer) {
				col = (int)params[1];
			}
		}

		addReserved(plot, row, col);
	}

	public void addReserved(PlotBox plot, int row, int col) {
		mLocations[row][col] = plot;

		addChildByName(plot);
	}

	public void addChild(PlotBox plot) {
		addChild(plot, 0, 0);
	}

	@Override
	public PlotBox getChild(Object param, Object... params) {

		int row = 0;
		int col = 0;

		if (param instanceof Integer) {
			row = (int)param;
		}

		if (params.length > 0) {
			if (params[0] instanceof Integer) {
				col = (int)params[0];
			}
		}

		return getChild(row, col);
	}

	public PlotBox getChild(int row, int col) {
		return mLocations[row][col];
	}

	@Override
	public Iterator<PlotBox> iterator() {
		return new PlotBoxGridIterator<PlotBox>(mLocations);
	}

	@Override
	public int getChildCount() {
		return mLocations.length * mLocations[0].length;
	}

	@Override
	public boolean remove(PlotBox plot) {
		IntPos2D rl = GeomUtils.INT_POINT_ZERO;

		boolean found = false;

		for (int i = 0; i < mLocations.length; ++i) {
			for (int j = 0; j < mLocations[0].length; ++j) {
				if (mLocations[i][j].equals(plot)) {
					remove(i, j);
					found = true;
					break;
				}
			}

			if (found) {
				break;
			}
		}

		if (found) {
			remove(rl);
		}

		return true;
	}

	@Override
	public boolean remove(Object param, Object... params) {
		int row = 0;
		int col = 0;

		if (param instanceof Integer) {
			row = (int)param;
		}

		if (params.length > 0) {
			if (params[0] instanceof Integer) {
				col = (int)params[0];
			}
		}

		remove(row, col);

		return true;
	}

	public void remove(int i, int j) {
		mLocations[i][j] = null;
	}
}

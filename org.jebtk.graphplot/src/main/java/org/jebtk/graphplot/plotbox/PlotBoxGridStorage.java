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

<<<<<<< HEAD
=======
import org.jebtk.core.Props;
import org.jebtk.core.geom.GeomUtils;
import org.jebtk.core.geom.IntCell;
import org.jebtk.core.geom.IntDim;
import org.jebtk.core.geom.IntPos2D;

>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6
/**
 * Store plots on a grid
 */
public class PlotBoxGridStorage extends PlotBoxStorage {

<<<<<<< HEAD
	private static final long serialVersionUID = 1L;

	private final PlotBox[][] mLocations;

	public PlotBoxGridStorage(int rows, int columns) {
		mLocations = new PlotBox[rows][columns];
	}

//  @Override
//  public void add(PlotBox plot, Props params) {
//    int row = 0;
//    int col = 0;
//
//    if (params.length > 1) {
//      if (params[0] instanceof Integer) {
//        row = (int) params[0];
//      }
//
//      if (params[1] instanceof Integer) {
//        col = (int) params[1];
//      }
//    }
//
//    add(plot, row, col);
//  }

	@Override
	public void add(PlotBox plot, int row, int col) {
		mLocations[row][col] = plot;

		super.add(plot);
	}

	@Override
	public void add(PlotBox plot) {
		add(plot, 0, 0);
	}

	@Override
	public PlotBox get(int row, int col) {
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
			remove(0, 0);
		}

		return true;
	}

	@Override
	public boolean remove(int i, int j) {
		mLocations[i][j] = null;
		
		return true;
	}
=======
  private static final long serialVersionUID = 1L;

  private final PlotBox[][] mLocations;

  public PlotBoxGridStorage(int rows, int columns) {
    mLocations = new PlotBox[rows][columns];
  }

  @Override
  public void add(PlotBox plot, Object p) {
    IntCell c = IntCell.ZERO;

    if (p != null) {
      c = (IntCell)p;
    }

    add(plot, c);
  }
  
  public void add(PlotBox plot, IntCell c) {
    add(plot, c.row, c.col);
  }

  public void add(PlotBox plot, int row, int col) {
    mLocations[row][col] = plot;

    super.add(plot);
  }

  public void addChild(PlotBox plot) {
    add(plot, 0, 0);
  }

  @Override
  public PlotBox get(Object p) {
    IntCell c = IntCell.ZERO;

    if (p != null) {
      c = (IntCell)p;
    }

    return getChild(c);
  }
  
  public PlotBox getChild(IntCell c) {
    return getChild(c.row, c.col);
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
      remove(0, 0);
    }

    return true;
  }

  @Override
  public boolean remove(Object p) {
    IntCell c = IntCell.ZERO;

    if (p != null) {
      c = (IntCell)p;
    }

    remove(c);

    return true;
  }
  
  public void remove(IntCell c) {
    remove(c.row, c.col);
  }

  public void remove(int i, int j) {
    mLocations[i][j] = null;
  }
>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6
}

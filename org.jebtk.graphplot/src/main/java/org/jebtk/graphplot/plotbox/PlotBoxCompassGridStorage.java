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

import org.jebtk.core.geom.IntCell;
import org.jebtk.graphplot.figure.GridLocation;

/**
 * The class PlotBox.
 */
public class PlotBoxCompassGridStorage extends PlotBoxStorage {

  private static final long serialVersionUID = 1L;

  public static final GridLocation[][] ROWS = {
      { GridLocation.NW, GridLocation.N, GridLocation.NE },
      { GridLocation.W, GridLocation.CENTER, GridLocation.E },
      { GridLocation.SW, GridLocation.S, GridLocation.SE } };

  private Map<GridLocation, PlotBox> mMap = new TreeMap<GridLocation, PlotBox>();

  @Override
  public void add(PlotBox plot, Object p) {
    GridLocation l = GridLocation.CENTER;

    if (p != null) {
      if (p instanceof GridLocation) {
        l = (GridLocation) p;
      } else if (p instanceof IntCell) {
        IntCell c = (IntCell)p;
        l = ROWS[c.row][c.col];
      } else {
        // do nothing
      }
    }

    add(plot, l);
  }

  public void add(PlotBox plot, GridLocation l) {
    mMap.put(l, plot);

    super.add(plot, l); //new Props().set("location", l));
  }

  @Override
  public PlotBox get(Object p) {
    return getChild(parseLocation(p));
  }

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

  @Override
  public boolean remove(PlotBox plot) {
    GridLocation rl = GridLocation.CENTER;

    boolean found = false;

    for (GridLocation l : mMap.keySet()) {
      if (mMap.get(l).equals(plot)) {
        rl = l;
        found = true;
        break;
      }
    }

    if (found) {
      remove(rl);
    }

    return true;
  }

  @Override
  public boolean remove(Object p) {
    remove(parseLocation(p));

    return true;
  }

  public void remove(GridLocation l) {
    mMap.remove(l);
  }

  private static GridLocation parseLocation(Object p) {
    GridLocation l = GridLocation.CENTER;

    if (p != null) {
      if (p instanceof GridLocation) {
        l = (GridLocation) p;
      } else if (p instanceof IntCell) {
        IntCell c = (IntCell) p;
        l = ROWS[c.row][c.col];
      } else {
        // Do nothing
      }
    }

    return l;
  }
}

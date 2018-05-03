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
  public void addReserved(PlotBox plot, Object... params) {
    GridLocation l = GridLocation.CENTER;

    if (params.length > 0) {
      if (params[0] instanceof GridLocation) {
        l = (GridLocation) params[0];
      } else {
        if (params.length > 1) {
          if (params[0] instanceof Integer && params[1] instanceof Integer) {
            l = ROWS[(int) params[0]][(int) params[1]];
          }
        }
      }
    }

    addReserved(plot, l);
  }

  public void addReserved(PlotBox plot, GridLocation l) {
    mMap.put(l, plot);

    addChildByName(plot);
  }

  @Override
  public PlotBox getChild(Object param, Object... params) {
    return getChild(parseLocation(param, params));
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
  public boolean remove(Object param, Object... params) {
    remove(parseLocation(param, params));

    return true;
  }

  public void remove(GridLocation l) {
    mMap.remove(l);
  }

  private static GridLocation parseLocation(Object param, Object... params) {
    GridLocation l = GridLocation.CENTER;

    if (param instanceof GridLocation) {
      l = (GridLocation) param;
    } else {
      if (params.length > 0) {
        if (param instanceof Integer && params[0] instanceof Integer) {
          l = ROWS[(int) param][(int) params[0]];
        }
      }
    }

    return l;
  }
}

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

import org.jebtk.core.geom.GeomUtils;
import org.jebtk.core.geom.IntPos2D;
import org.jebtk.graphplot.figure.GridLocation;

/**
 * The class PlotBox.
 */
public class PlotBoxFloatingStorage extends PlotBoxStorage {

  private static final long serialVersionUID = 1L;

  private Map<IntPos2D, PlotBox> mMap = new HashMap<IntPos2D, PlotBox>();

  @Override
  public void addReserved(PlotBox plot, Object... params) {
    IntPos2D l = GeomUtils.INT_POINT_ZERO;

    if (params.length > 0) {
      if (params[0] instanceof GridLocation) {
        l = (IntPos2D) params[0];
      } else {
        if (params.length > 0) {
          if (params[0] instanceof Integer && params[1] instanceof Integer) {
            l = new IntPos2D((int) params[0], (int) params[1]);
          }
        }
      }
    }

    addReserved(plot, l);
  }

  public void addReserved(PlotBox plot, IntPos2D p) {
    mMap.put(p, plot);

    addChildByName(plot);
  }

  @Override
  public PlotBox getChild(Object param, Object... params) {
    return getChild(parseLocation(param, params));
  }

  public PlotBox getChild(IntPos2D p) {
    return mMap.get(p);
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
  public int getChildCount() {
    return mMap.size();
  }

  @Override
  public void clear() {
    mMap.clear();

    super.clear();
  }

  @Override
  public boolean remove(PlotBox plot) {
    IntPos2D rl = GeomUtils.INT_POINT_ZERO;

    boolean found = false;

    for (IntPos2D l : mMap.keySet()) {
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

  public void remove(IntPos2D l) {
    mMap.remove(l);
  }

  private static IntPos2D parseLocation(Object param, Object... params) {
    IntPos2D l = GeomUtils.INT_POINT_ZERO;

    if (param instanceof IntPos2D) {
      l = (IntPos2D) param;
    } else {
      if (params.length > 0) {
        if (param instanceof Integer && params[0] instanceof Integer) {
          l = new IntPos2D((int) param, (int) params[0]);
        }
      }
    }

    return l;
  }
}

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

/**
 * The class PlotBox.
 */
public class PlotBoxZStorage extends PlotBoxStorage {

  private static final long serialVersionUID = 1L;

  private Map<Integer, PlotBox> mMap = new TreeMap<Integer, PlotBox>();

  private int mUnused = 0;

  @Override
  public void add(PlotBox plot, Object... params) {
    int z = 0;

    if (params.length > 0 && params[0] instanceof Integer) {
      z = (int) params[0];
    } else {
      z = getUnusedZ();
    }

    add(plot, z);
  }

  public void add(PlotBox plot, int z) {
    mMap.put(z, plot);

    super.add(plot, z);

    // keep track of where we can likely search from to find the next
    // unused z
    mUnused = z + 1;
  }

  @Override
  public PlotBox get(Object param, Object... params) {
    return mMap.get(param);
  }

  @Override
  public int getUnusedZ() {
    for (int i = mUnused; i < Integer.MAX_VALUE; ++i) {
      if (!mMap.containsKey(i)) {
        return i;
      }
    }

    return 0;
  }

  @Override
  public Iterable<Integer> getZ() {
    return mMap.keySet();
  }

  @Override
  public Iterator<PlotBox> iterator() {
    return mMap.values().iterator();
  }

  @Override
  public void clear() {
    mMap.clear();

    super.clear();
  }

  @Override
  public boolean remove(PlotBox plot) {
    int z = 0;

    boolean found = false;

    for (int l : mMap.keySet()) {
      if (mMap.get(l).equals(plot)) {
        z = l;
        found = true;
        break;
      }
    }

    if (found) {
      remove(z);
    }

    return true;
  }

  @Override
  public boolean remove(Object param, Object... params) {
    remove(parseZ(param, params));

    return true;
  }

  public void remove(int i) {
    mMap.remove(i);
  }

  private static int parseZ(Object param, Object... params) {
    int z = 0;

    if (param instanceof Integer) {
      z = (int) param;
    }

    return z;
  }
}

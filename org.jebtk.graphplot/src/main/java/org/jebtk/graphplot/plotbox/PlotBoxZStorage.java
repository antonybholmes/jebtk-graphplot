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

import org.jebtk.core.Props;

/**
 * The class PlotBox.
 */
public class PlotBoxZStorage extends PlotBoxStorage {

	private static final long serialVersionUID = 1L;

<<<<<<< HEAD
	private Map<Integer, PlotBox> mMap = new TreeMap<Integer, PlotBox>();

	private int mUnused = 0;
=======
  @Override
  public void add(PlotBox plot, Object p) {
    int z = 0;

    if (p != null) {
      z = (int) p;
    } else {
      z = getUnusedZ();
    }
>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6

	@Override
	public void add(PlotBox plot) {
		add(plot, -1);
	}

	@Override
	public void add(PlotBox plot, int z) {
		if (z == -1) {
			z = getUnusedZ();
		}

		mMap.put(z, plot);

		// keep track of where we can likely search from to find the next
		// unused z
		mUnused = z + 1;

<<<<<<< HEAD
		super.add(plot);
	}
=======
  @Override
  public PlotBox get(Object p) {
    return mMap.get((int)p);
  }
>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6

	@Override
	public PlotBox get(int z) {
		return mMap.get(z);
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

<<<<<<< HEAD
		return true;
	}
=======
  @Override
  public boolean remove(Object p) {
    remove((int)p);
>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6

	@Override
	public boolean remove(int z) {
		mMap.remove(z);

<<<<<<< HEAD
		return true;
	}

	private static int parseZ(Object param, Props params) {
		int z = 0;

		if (param instanceof Integer) {
			z = (int) param;
		}

		return z;
	}
=======
  public void remove(int i) {
    mMap.remove(i);
  }
>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6
}

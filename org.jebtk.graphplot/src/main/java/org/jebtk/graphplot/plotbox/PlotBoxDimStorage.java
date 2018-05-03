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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jebtk.core.collections.CollectionUtils;

/**
 * The class PlotBox.
 */
public class PlotBoxDimStorage extends PlotBoxStorage {
  private static final long serialVersionUID = 1L;

  /**
   * The member children.
   */
  private List<PlotBox> mChildren = new ArrayList<PlotBox>(100);

  public void addChild(Collection<PlotBox> plotBoxes) {
    addChildren(plotBoxes);
  }

  public void addChildren(Collection<PlotBox> plotBoxes) {
    for (PlotBox b : plotBoxes) {
      addChild(b);
    }
  }

  /**
   * Adds the child.
   *
   * @param plotBox the plot box
   */
  @Override
  public void addReserved(PlotBox plot, Object... params) {
    mChildren.add(plot);

    super.addReserved(plot, params);
  }

  /**
   * Gets the child.
   *
   * @param index the index
   * @return the child
   */
  @Override
  public PlotBox getChild(Object param, Object... params) {
    int i = 0;

    if (param instanceof Integer) {
      i = (int) param;
    } else {
      i = 0;
    }

    return getChild(i);
  }

  public PlotBox getChild(int index) {
    if (CollectionUtils.inBounds(index, mChildren)) {
      return mChildren.get(index);
    } else {
      return null;
    }
  }

  @Override
  public boolean remove(PlotBox plot) {
    mChildren.remove(plot);

    return true;
  }

  @Override
  public boolean remove(Object param, Object... params) {
    if (param instanceof Integer) {
      mChildren.remove((int) param);
    }

    return true;
  }

  /**
   * Remove all plot children.
   */
  @Override
  public void clear() {
    mChildren.clear();

    super.clear();
  }

  @Override
  public Iterator<PlotBox> iterator() {
    return mChildren.iterator();
  }

  @Override
  public int getChildCount() {
    return mChildren.size();
  }

}

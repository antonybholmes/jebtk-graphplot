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

import java.awt.Dimension;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jebtk.core.StringId;
import org.jebtk.core.geom.IntDim;



// TODO: Auto-generated Javadoc
/**
 * The class EmptyPlotBox.
 */
public class PlotBoxEmpty extends PlotBox {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	private static final StringId NEXT_ID = new StringId("Plot Box Empty");
	
	private static final List<PlotBox> NO_PLOTS =
			Collections.emptyList();

	/**
	 * Instantiates a new empty plot box.
	 *
	 * @param dimension the dimension
	 */
	public PlotBoxEmpty(IntDim dim) {
		super(NEXT_ID.getNextId(), new PlotBoxEmptyLayout(dim));
	}
	
	public PlotBoxEmpty(Dimension dim) {
		this(IntDim.create(dim));
	}

	/**
	 * Instantiates a new empty plot box.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public PlotBoxEmpty(int width, int height) {
		this(new IntDim(width, height));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<PlotBox> iterator() {
		return NO_PLOTS.iterator();
	}
}

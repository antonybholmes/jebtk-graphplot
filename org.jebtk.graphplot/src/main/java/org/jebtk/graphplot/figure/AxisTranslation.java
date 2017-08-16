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
package org.jebtk.graphplot.figure;

import java.util.HashMap;
import java.util.Map;

import org.jebtk.core.Mathematics;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;

// TODO: Auto-generated Javadoc
/**
 * Translate between graph space and pixel space.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class AxisTranslation implements ChangeListener {
	
	/** The m x map. */
	protected Map<Double, Integer> mXMap = new HashMap<Double, Integer>();
	
	/** The m x norm map. */
	protected Map<Double, Double> mXNormMap = new HashMap<Double, Double>();

	/** The m x min. */
	protected double mXMin;

	/** The m x diff. */
	protected double mXDiff;

	/** The m max x. */
	protected int mMaxX;

	/** The m offset. */
	protected int mOffset = 0;

	/** The m axis. */
	protected Axis mAxis;
	
	/**
	 * Instantiates a new axis translation.
	 *
	 * @param axis the axis
	 */
	public AxisTranslation(Axis axis) {
		mAxis = axis;
		
		axis.addChangeListener(this);
	}
	
	/**
	 * Update.
	 *
	 * @param offset the offset
	 * @param width the width
	 */
	protected void update(int offset, int width) {
		mOffset = offset;
		
		// The max coordinate is the width - 1, since we are using 0 based
		// coordinates.
		mMaxX = width; // - 1;

		mXMap.clear();
	}
	
	/**
	 * Redraw.
	 */
	public void redraw() {	
		mXMin = mAxis.getMin();
		mXDiff = mAxis.getMax() - mXMin;
		
		mXNormMap.clear();
	}
	
	/**
	 * To plot.
	 *
	 * @param x the x
	 * @return the int
	 */
	public int toPlot(double x) {
		if (!mXMap.containsKey(x)) {
			mXMap.put(x, mOffset + (int)Math.round(plotNormalize(x) * mMaxX));
		}

		return mXMap.get(x);
	}
	
	/**
	 * Plot normalize.
	 *
	 * @param x the x
	 * @return the double
	 */
	public double plotNormalize(double x) {
		if (!mXNormMap.containsKey(x)) {
			mXNormMap.put(x, Mathematics.bound((x - mXMin) / mXDiff, 0, 1));
		}
		
		return mXNormMap.get(x);
	}
	
	/**
	 * Clear caches.
	 */
	public void clearCaches() {
		mXMap.clear();
		
		mXNormMap.clear();
	}

	/* (non-Javadoc)
	 * @see org.abh.common.event.ChangeListener#changed(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void changed(ChangeEvent e) {
		redraw();
	}
}

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

// TODO: Auto-generated Javadoc
/**
 * Translate between graph space and pixel space in the Y axis. Since Java
 * uses inverted coordinates for y (0 is top right) we must invert and
 * correct for this since on a 2D cartesian graph y, y increases from
 * the bottom up.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class AxisTranslationY extends AxisTranslation {
	
	/**
	 * Instantiates a new axis translation y.
	 *
	 * @param axis the axis
	 */
	public AxisTranslationY(Axis axis) {
		super(axis);
	}
	
	/* (non-Javadoc)
	 * @see org.graphplot.figure.AxisTranslation#toPlot(double)
	 */
	@Override
	public int toPlot(double x) {
		if (!mXMap.containsKey(x)) {
			mXMap.put(x, mOffset - (int)Math.round(plotNormalize(x) * mMaxX));
		}

		return mXMap.get(x);
	}
}

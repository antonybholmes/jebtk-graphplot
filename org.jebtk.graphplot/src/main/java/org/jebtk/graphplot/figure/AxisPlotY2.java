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
 * Plot for drawing an axis. Responds to axis visibility properties.
 *  
 * @author Antony Holmes Holmes
 *
 */
public class AxisPlotY2 extends AxisPlot {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new axis plot y2.
	 *
	 * @param axis the axis
	 */
	public AxisPlotY2(Axis axis) {
		this("Axis Y2 Label Plot", axis);
	}

	/**
	 * Instantiates a new plot.
	 *
	 * @param name the name
	 * @param axis the axis
	 */
	public AxisPlotY2(String name, Axis axis) {
		super(name, axis);
		
		setZ(new AxisLabelLayerY2());
		setInternalPlotWidth(autoSetLabelMargin(axis));
		
		System.err.println("Y2 width " + getInternalWidth());
	}

	
}

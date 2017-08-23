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

import org.jebtk.core.StringId;

// TODO: Auto-generated Javadoc
/**
 * Represents a 2D Cartesian graph. This
 * class draws basic axes and titles but should
 * be subclassed to provide specific plot functionality.
 *  
 * @author Antony Holmes Holmes
 *
 */
public class Axes2D extends Axes {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant NEXT_ID. */
	private static final StringId NEXT_ID = new StringId("Axes2D");

	/**
	 * Instantiates a new axes2 d.
	 */
	public Axes2D() {
		this(NEXT_ID.getNextId());
	}

	/**
	 * Instantiates a new axes2 d.
	 *
	 * @param name the name
	 */
	public Axes2D(String name) {
		super(name);
		
		addReserved(new AxisLayerX1(), 1000);
		addReserved(new AxisLayerY1(), 2000);
		addReserved(new AxisLayerY2(), 3000);

		// DO NOT NEED
		//mAxesLayers.setZ(new LabelPlotLayer(), 4000);

		addReserved(new LegendLayer(), 5000);
		addReserved(new AxesTitleLayer(), 6000);
	}
}

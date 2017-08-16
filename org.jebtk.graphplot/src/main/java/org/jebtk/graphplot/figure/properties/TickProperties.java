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
package org.jebtk.graphplot.figure.properties;

import java.util.List;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;
import org.jebtk.math.Linspace;

// TODO: Auto-generated Javadoc
/**
 * Describes the tick marks on an axis.
 * 
 * @author Antony Holmes Holmes
 */
public class TickProperties extends ChangeListeners implements ChangeListener {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The member major ticks.
	 */
	private TickMarkProperties mMajorTicks = new MajorTickMarkProperties();
	
	//private TickLabelProperties mMajorTickLabels = new TickLabelProperties();
	
	/**
	 * The member minor ticks.
	 */
	private TickMarkProperties mMinorTicks = new MinorTickMarkProperties();

	/**
	 * The member draw inside.
	 */
	private boolean mDrawInside = true;
	
	//private TickLabelProperties mMinorTickLabels = new TickLabelProperties();
	
	/**
	 * Instantiates a new tick properties.
	 */
	public TickProperties() {
		mMajorTicks.addChangeListener(this);
		mMinorTicks.addChangeListener(this);
	}
	
	/**
	 * Returns true if the ticks should be draw facing inwards.
	 *
	 * @return the draw inside
	 */
	public boolean getDrawInside() {
		return mDrawInside;
	}
	
	/**
	 * Sets the draw inside.
	 *
	 * @param drawInside the new draw inside
	 */
	public void setDrawInside(boolean drawInside) {
		mDrawInside = drawInside;
		
		fireChanged();
	}
	
	
	/**
	 * Sets the ticks.
	 *
	 * @param ticks the new ticks
	 */
	public void setTicks(List<Double> ticks) {
		mMajorTicks.setTicks(ticks);
		
		// Default to subdividing major ticks into 10 for the minor ticks.
		mMinorTicks.setTicks(Linspace.subDivide(ticks, 11));
	}
	
	/**
	 * Gets the major ticks.
	 *
	 * @return the major ticks
	 */
	public TickMarkProperties getMajorTicks() {
		return mMajorTicks;
	}

	/**
	 * Gets the minor ticks.
	 *
	 * @return the minor ticks
	 */
	public TickMarkProperties getMinorTicks() {
		return mMinorTicks;
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void changed(ChangeEvent e) {
		fireChanged();
	}
}

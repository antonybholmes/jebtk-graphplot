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

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;

// TODO: Auto-generated Javadoc
/**
 * Describes how the legend should appear on a plot.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class LegendProperties extends VisibleProperties implements ChangeListener {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The member position.
	 */
	private LegendPosition mPosition = LegendPosition.TOP_RIGHT;
	
	/**
	 * The member style.
	 */
	private StyleProperties mStyle = new StyleProperties();
	
	/**
	 * The member font.
	 */
	private FontProperties mFont = new FontProperties();
	
	private boolean mInside = true;
	
	/**
	 * Instantiates a new legend properties.
	 */
	public LegendProperties() {
		mStyle.addChangeListener(this);
		mFont.addChangeListener(this);
		
		setVisible(false);
		
		// Default to a black outline around the legend
		mStyle.getLineStyle().setVisible(false); //setColor(Color.BLACK);
		mStyle.getFillStyle().setVisible(false);
	}
	
	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(LegendPosition position) {
		mPosition = position;
		
		fireChanged();
	}
	
	/**
	 * Set whether to plot the legend inside the boundaries of the axes or
	 * not.
	 * 
	 * @param inside
	 */
	public void setInside(boolean inside) {
		mInside = inside;
		
		fireChanged();
	}
	
	/**
	 * Returns true if the legend should be plotted inside the boundaries of
	 * the axes.
	 * 
	 * @return
	 */
	public boolean inside() {
		return mInside;
	}
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public LegendPosition getPosition() {
		return mPosition;
	}
	
	/**
	 * Gets the style.
	 *
	 * @return the style
	 */
	public StyleProperties getStyle() {
		return mStyle;
	}
	
	/**
	 * Gets the font properties.
	 *
	 * @return the font properties
	 */
	public FontProperties getFontProperties() {
		return mFont;
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void changed(ChangeEvent e) {
		fireChanged();
	}
}

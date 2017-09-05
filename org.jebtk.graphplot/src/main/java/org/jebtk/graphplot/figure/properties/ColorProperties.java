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

import java.awt.Color;

// TODO: Auto-generated Javadoc
/**
 * Describes the font for a plot object.
 * 
 * @author Antony Holmes Holmes
 *
 */
public abstract class ColorProperties extends VisibleProperties {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The member color.
	 */
	private Color mColor = Color.BLACK;

	/**
	 * Instantiates a new color properties.
	 */
	public ColorProperties() {

	}

	/**
	 * Instantiates a new color properties.
	 *
	 * @param color the color
	 */
	public ColorProperties(Color color) {
		setColor(color);
	}

	/**
	 * Copy.
	 *
	 * @param properties the properties
	 */
	public void copy(final ColorProperties properties) {
		setColor(properties.mColor);

		super.copy(properties);
	}

	/**
	 * Set the color of an item. Setting the color automatically triggers
	 * the object to become visible.
	 * 
	 * @param color		Font color.
	 */
	public void setColor(Color color) {
		System.err.println("color properties " + color + " " + this);
		
		updateColor(color);
		
		if (color == null) {
			// If a null color is specified, default to making element
			// invisible
			setVisible(false);
		} else {
			// If a color is specified, default to making visible
			setVisible(true);
		}
	}
	
	/**
	 * Update color.
	 *
	 * @param color the color
	 */
	public void updateColor(Color color) {
		if (color != null && !color.equals(mColor)) {
			mColor = color;
		}
	}

	/**
	 * Returns the color.
	 * 
	 * @return		The color.
	 */
	public Color getColor() {
		return mColor;
	}
}

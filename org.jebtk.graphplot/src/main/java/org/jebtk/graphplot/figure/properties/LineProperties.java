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
import java.awt.Stroke;

// TODO: Auto-generated Javadoc
/**
 * Set the color and stroke of a line on a plot element.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class LineProperties extends ColorProperties {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The m style. */
	private StrokeStyle mStyle = null;
	
	/**
	 * The member stroke.
	 */
	private Stroke mStroke;

	/** The m width. */
	private int mWidth;

	/**
	 * Instantiates a new line properties.
	 */
	public LineProperties() {
		// The default color, to override the fill color from which it
		// derived.
		setColor(Color.BLACK);
		
		setStroke(StrokeStyle.SINGLE, 1);
	}
	
	/**
	 * Copy.
	 *
	 * @param p the p
	 */
	public void copy(final LineProperties p) {
		mStroke = p.mStroke;
		mStyle = p.mStyle;
		mWidth = p.mWidth;
		
		super.copy(p);
	}
	
	/**
	 * Sets the stroke.
	 *
	 * @param w the new stroke
	 */
	public void setStroke(int w) {
		setStroke(StrokeStyle.SINGLE, w);
	}
	
	/**
	 * Sets the stroke.
	 *
	 * @param style the style
	 * @param w the w
	 */
	public void setStroke(StrokeStyle style, int w) {
		mStyle = style;
		mWidth = w;
		
		setStroke(StrokeStyle.getStroke(style, w));
	}
	
	/**
	 * Set the line stroke style.
	 * 
	 * @param stroke	The line stroke.
	 */
	private void setStroke(Stroke stroke) {
		mStroke = stroke;
		
		fireChanged();
	}
	
	/**
	 * Returns the line stroke.
	 * 
	 * @return		The line stroke.
	 */
	public Stroke getStroke() {
		return mStroke;
	}

	/**
	 * Gets the stroke style.
	 *
	 * @return the stroke style
	 */
	public StrokeStyle getStrokeStyle() {
		return mStyle;
	}
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return mWidth;
	}
	
}

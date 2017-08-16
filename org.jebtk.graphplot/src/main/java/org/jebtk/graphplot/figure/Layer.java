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

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: Auto-generated Javadoc
/**
 * A graph is built from layers which may or may not have a visual
 * component associated with them or may be just a sub collection of 
 * elements to make up a plot.
 *
 * @author Antony Holmes Holmes
 */
public abstract class Layer extends LayerCanvasListener implements ZLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The Constant ID. */
	private static final AtomicInteger ID = new AtomicInteger(0);
	
	/**
	 * The member name.
	 */
	private String mName;
	
	/**
	 * The member visible.
	 */
	protected boolean mVisible = true;

	/** The m id. */
	private int mId;

	/**
	 * Instantiates a new plot layer.
	 *
	 * @param name the name
	 */
	public Layer(String name) {
		mName = name;
		
		mId = ID.getAndIncrement();
	}
	
	/* (non-Javadoc)
	 * @see org.abh.lib.NameProperty#getName()
	 */
	@Override
	public String getName() {
		return mName;
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.IdProperty#getId()
	 */
	@Override
	public int getId() {
		return mId;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public LayerType getType() {
		return LayerType.LAYER;
	}
	
	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean getVisible() {
		return mVisible;
	}
	
	/**
	 * Sets the visible.
	 *
	 * @param visible the new visible
	 */
	public void setVisible(boolean visible) {
		mVisible = visible;
		
		fireCanvasRedraw();
	}
	
	/**
	 * Sets the font.
	 *
	 * @param font the font
	 * @param color the color
	 */
	public void setFont(Font font, Color color) {
		fireCanvasRedraw();
	}
}

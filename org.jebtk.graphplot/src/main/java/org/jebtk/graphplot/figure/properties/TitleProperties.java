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
import org.jebtk.core.event.ChangeListeners;
import org.jebtk.core.text.TextUtils;

// TODO: Auto-generated Javadoc
/**
 * Represents a title.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class TitleProperties extends ChangeListeners implements ChangeListener {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The member font.
	 */
	private FontProperties mFont = new FontProperties();
	
	/**
	 * The member name.
	 */
	private String mName = TextUtils.EMPTY_STRING;
	
	/**
	 * Instantiates a new title properties.
	 */
	public TitleProperties() {
		mFont.setVisible(false);
		mFont.addChangeListener(this);
	}
	
	/**
	 * Copy.
	 *
	 * @param title the title
	 */
	public void copy(TitleProperties title) {
		
		mFont.copy(title.mFont);
		
		setText(title.mName);
	}

	/**
	 * Sets the text.
	 *
	 * @param name the new text
	 * @return the title properties
	 */
	public TitleProperties setText(String name) {
		if (name != null) {
			mName = name;
		
			// If the name is set, make the title visible
			mFont.setVisible(true);
		}
		
		return this;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return mName;
	}
	
	/**
	 * Gets the font style.
	 *
	 * @return the font style
	 */
	public FontProperties getFontStyle() {
		return mFont;
	}

	/* (non-Javadoc)
	 * @see org.abh.lib.event.ChangeListener#changed(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void changed(ChangeEvent e) {
		fireChanged();
	}

	/**
	 * Sets whether the title is visible or not. Equivalent to
	 * getFontStyle().setVisible().
	 *
	 * @param visible the new visible
	 */
	public void setVisible(boolean visible) {
		mFont.setVisible(visible);
	}
	
	public boolean getVisible() {
		return mFont.getVisible();
	}
}

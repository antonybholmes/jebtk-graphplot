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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.jebtk.core.StringId;


// TODO: Auto-generated Javadoc
/**
 * Management of figures.
 *
 * @author Antony Holmes Holmes
 *
 */
public class SubFigures implements Iterable<String> {
	
	/** The Constant NEXT_ID. */
	private static final StringId NEXT_ID = new StringId("Figure");
	
	/**
	 * The member graph map.
	 */
	private Map<String, SubFigure> mGraphMap = 
			new TreeMap<String, SubFigure>();

	/**
	 * The member current figure.
	 */
	private SubFigure mCurrentFigure = null;

	/**
	 * Creates the new figure.
	 *
	 * @return the figure
	 */
	public SubFigure createNewFigure() {
		return createNewFigure(NEXT_ID.getNextId());
	}
	
	/**
	 * Creates the new figure.
	 *
	 * @param prefix the prefix
	 * @param id the id
	 * @return the sub figure
	 */
	public SubFigure createNewFigure(String prefix, int id) {
		return createNewFigure(prefix + "_" + id);
	}
	
	/**
	 * Creates the new figure.
	 *
	 * @param name the name
	 * @return the sub figure
	 */
	private SubFigure createNewFigure(String name) {
		SubFigure figure = new SubFigure(name);
		
		mGraphMap.put(figure.getName(), figure);
		
		mCurrentFigure = figure;
		
		return figure;
	}
	
	/**
	 * Returns plugins of a particular type.
	 *
	 * @param name the name
	 * @return the figure
	 */
	public SubFigure getFigure(String name) {
		return mGraphMap.get(name);
	}
	
	/**
	 * Gets the current figure.
	 *
	 * @return the current figure
	 */
	public SubFigure getCurrentFigure() {
		if (mCurrentFigure == null) {
			createNewFigure();
		}
		
		return mCurrentFigure;
	}

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<String> iterator() {
		return mGraphMap.keySet().iterator();
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return mGraphMap.size();
	}
}
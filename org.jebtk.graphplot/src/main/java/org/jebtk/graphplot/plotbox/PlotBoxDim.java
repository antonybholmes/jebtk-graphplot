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
package org.jebtk.graphplot.plotbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jebtk.graphplot.figure.SubFigure;
import org.jebtk.modern.graphics.ModernCanvas;


// TODO: Auto-generated Javadoc
/**
 * The class PlotBox.
 */
public abstract class PlotBoxDim extends PlotBox {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The member children.
	 */
	private List<PlotBox> mChildren = new ArrayList<PlotBox>();


	public PlotBoxDim(String name, PlotBoxLayout layout) {
		super(name, layout);
	}

	public void addChild(Collection<PlotBox> plotBoxes) {
		addChildren(plotBoxes);
	}
	
	public <T extends ModernCanvas> void addCanvases(Collection<T> canvases) {
		for (T canvas : canvases) {
			addChild(new PlotBoxCanvas(canvas));
		}
	}
	
	public <T extends SubFigure> void setSubFigures(Collection<T> subFigures) {
		clear();
		
		addSubFigures(subFigures);
	}
	
	
	public <T extends SubFigure> void addSubFigures(Collection<T> subFigures) {
		for (T f : subFigures) {
			addChild(new PlotBoxSubFigure(f));
		}
	}
	
	public void addChildren(Collection<PlotBox> plotBoxes) {
		for (PlotBox b : plotBoxes) {
			addChild(b);
		}
	}

	/**
	 * Adds the child.
	 *
	 * @param plotBox the plot box
	 */
	@Override
	public void addChild(PlotBox plot) {
		mChildren.add(plot);
		
		addChildByName(plot);
	}
	
	@Override
	public void addChild(PlotBox plot, int i) {
		addChild(plot);
	}

	/**
	 * Gets the child.
	 *
	 * @param index the index
	 * @return the child
	 */
	@Override
	public PlotBox getChild(int index) {
		return mChildren.get(index);
	}
	
	/**
	 * Remove all plot children.
	 */
	public void clear() {
		mChildren.clear();
		
		fireCanvasResized();
	}

	@Override
	public Iterator<PlotBox> iterator() {
		return mChildren.iterator();
	}
	
	@Override
	public int getChildCount() {
		return mChildren.size();
	}
}

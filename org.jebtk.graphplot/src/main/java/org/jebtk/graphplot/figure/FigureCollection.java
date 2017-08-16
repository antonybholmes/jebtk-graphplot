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

import java.util.Collection;

import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.model.ListModel;
import org.jebtk.modern.graphics.ModernCanvasEventProducer;
import org.jebtk.modern.graphics.ModernCanvasListener;
import org.jebtk.modern.graphics.ModernCanvasListeners;

// TODO: Auto-generated Javadoc
/**
 * A collection of canvases.
 *
 * @author Antony Holmes Holmes
 */
public class FigureCollection extends ListModel<SubFigure> implements ModernCanvasListener, ModernCanvasEventProducer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The member listeners.
	 */
	private ModernCanvasListeners mListeners = new ModernCanvasListeners();
	
	/**
	 * Instantiates a new figure collection.
	 */
	public FigureCollection() {
		// Do nothing
	}
	
	/**
	 * Instantiates a new figure collection.
	 *
	 * @param figure the figure
	 */
	public FigureCollection(SubFigure figure) {
		add(figure);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.lib.model.ListModel#add(java.lang.Object)
	 */
	@Override
	public boolean add(SubFigure item) {
		item.addCanvasListener(this);
		
		return super.add(item);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.lib.model.ListModel#add(java.util.Collection)
	 */
	@Override
	public void add(Collection<SubFigure> items) {
		for (SubFigure item : items) {
			item.addCanvasListener(this);
		}

		super.add(items);
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasListener#canvasChanged(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void canvasChanged(ChangeEvent e) {
		fireChanged();
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasListener#redrawCanvas(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void redrawCanvas(ChangeEvent e) {
		fireCanvasRedraw(e);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.graphics.ModernCanvasListener#canvasResized(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void canvasResized(ChangeEvent e) {
		fireCanvasResized(e);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasListener#canvasScrolled(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void canvasScrolled(ChangeEvent e) {
		fireCanvasScrolled(e);
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasEventProducer#addCanvasListener(org.abh.common.ui.ui.graphics.ModernCanvasListener)
	 */
	@Override
	public void addCanvasListener(ModernCanvasListener l) {
		mListeners.addCanvasListener(l);
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasEventProducer#removeCanvasListener(org.abh.common.ui.ui.graphics.ModernCanvasListener)
	 */
	@Override
	public void removeCanvasListener(ModernCanvasListener l) {
		mListeners.removeCanvasListener(l);
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasEventProducer#fireCanvasChanged(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void fireCanvasChanged(ChangeEvent e) {
		mListeners.fireCanvasChanged(e);
	}

	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasEventProducer#fireCanvasRedraw(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void fireCanvasRedraw(ChangeEvent e) {
		mListeners.fireCanvasRedraw(e);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.ui.graphics.ModernCanvasEventProducer#fireCanvasScrolled(org.abh.lib.event.ChangeEvent)
	 */
	@Override
	public void fireCanvasScrolled(ChangeEvent e) {
		mListeners.fireCanvasScrolled(e);
	}
	
	/* (non-Javadoc)
	 * @see org.abh.common.ui.graphics.ModernCanvasEventProducer#fireCanvasResized(org.abh.common.event.ChangeEvent)
	 */
	@Override
	public void fireCanvasResized(ChangeEvent e) {
		mListeners.fireCanvasResized(e);
	}
}

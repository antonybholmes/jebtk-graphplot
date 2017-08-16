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
package org.jebtk.graphplot;

import java.awt.Graphics2D;

import org.jebtk.modern.graphics.DrawingContext;


// TODO: Auto-generated Javadoc
/**
 * The class PanelLabel.
 */
public class PanelLabel extends ModernPlotCanvas {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The constant SIZE.
	 */
	private static final int SIZE = 40;
	
	/**
	 * The y offset.
	 */
	private static int Y_OFFSET = 20;

	/**
	 * The label.
	 */
	private String mLabel;
	
	/**
	 * Instantiates a new panel label.
	 *
	 * @param label the label
	 */
	public PanelLabel(String label) {
		mLabel = label;
		
		setCanvasSize(SIZE, SIZE);
	}
	
	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, DrawingContext context) {
		int x = (SIZE - g2.getFontMetrics().stringWidth(mLabel)) / 2;
		
		g2.drawString(mLabel, x, Y_OFFSET);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString() {
		return "Panel " + mLabel;
	}
}

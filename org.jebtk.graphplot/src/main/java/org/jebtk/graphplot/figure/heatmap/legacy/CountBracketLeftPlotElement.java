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
package org.jebtk.graphplot.figure.heatmap.legacy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import org.jebtk.core.Props;
import org.jebtk.core.geom.DoubleDim;
import org.jebtk.math.matrix.DataFrame;
import org.jebtk.modern.graphics.DrawingContext;

/**
 * Displays brackets to indicate how many samples are up and down regulated
 * based on their z-score.
 *
 * @author Antony Holmes
 */
public class CountBracketLeftPlotElement extends RowMatrixPlotElement {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The m color. */
	private Color mColor;

	private CountGroups mCountGroups;

	/**
	 * Instantiates a new count bracket left plot element.
	 *
	 * @param matrix      the matrix
	 * @param width       the width
	 * @param aspectRatio the aspect ratio
	 * @param color       the color
	 */
	public CountBracketLeftPlotElement(DataFrame matrix, CountGroups countGroups, int width, DoubleDim aspectRatio,
			Color color) {
		super(matrix, aspectRatio, width);
		mColor = color;
		mCountGroups = countGroups;
	}

<<<<<<< HEAD
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.
	 * Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
	 */
	@Override
	public void plot(Graphics2D g2, Dimension offset, DrawingContext context, Props params) {
		g2.setColor(mColor);
=======
  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.columbia.rdf.lib.bioinformatics.plot.ModernPlotCanvas#plot(java.awt.
   * Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext)
   */
  @Override
  public void plot(Graphics2D g2,
      Dimension offset,
      DrawingContext context,
      Props props) {
    g2.setColor(mColor);
>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6

		int y1;
		int y2;

		int w = getPreferredSize().width;
		double r = mAspectRatio.getH();

		for (CountGroup countGroup : mCountGroups) {
			y1 = (int) (countGroup.getStart() * r);
			y2 = (int) ((countGroup.getEnd() + 1) * r);

			g2.drawLine(0, y1, w, y1);
			g2.drawLine(0, y1, 0, y2);
			g2.drawLine(0, y2, w, y2);
		}

<<<<<<< HEAD
		super.plot(g2, offset, context, params);
	}
=======
    super.plot(g2, offset, context, props);
  }
>>>>>>> edc2de9085a0b61281652320f8186d7d1777b2d6
}

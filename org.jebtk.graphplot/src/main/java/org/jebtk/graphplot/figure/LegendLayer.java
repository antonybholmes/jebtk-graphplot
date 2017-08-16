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

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import java.util.TreeMap;

import org.jebtk.graphplot.figure.series.XYSeries;
import org.jebtk.modern.graphics.DrawingContext;
import org.jebtk.modern.widget.ModernWidget;

// TODO: Auto-generated Javadoc
/**
 * The class LegendLayer.
 */
public class LegendLayer extends AxesLayer {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The Constant GAP. */
	private static final int GAP = 5;
	
	/** The Constant TEXT_OFFSET. */
	private static final int TEXT_OFFSET = 48;
	
	/** The Constant LINE_WIDTH. */
	private static final int LINE_WIDTH = 32;

	/**
	 * Instantiates a new legend layer.
	 */
	public LegendLayer() {
		super("Legend");
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.AxesLayer#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes)
	 */
	@Override
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure figure,
			Axes axes) {
		// determine the size of the legend

		if (!axes.getLegend().getVisible()) {
			return;
		}

		// In case there are repetitive series within groups (e.g. grouped
		// bars), try to extract only the unique names.

		Map<String, XYSeries> uniqueSeries = new TreeMap<String, XYSeries>();

		//axes.setInternalPlotHeight(100);

		for (GridLocation l : axes.mLocations) {
			for (int z : axes.mLocations.get(l)) {
				MovableLayer layer = axes.mLocations.get(l).getAtZ(z);

				if (layer instanceof Plot) {
					Plot plot = (Plot)layer;

					for (XYSeries series : plot.getAllSeries()) {
						uniqueSeries.put(series.getName(), series);

						//System.err.println("series " + series.getName()  + " " + series.getMarker() + " " + series.getMarkerStyle().getFillStyle().getColor());
					}
				}
			}
		}


		int lineHeight = g2.getFontMetrics().getAscent() + 
				g2.getFontMetrics().getDescent();

		// The height of the bounding box needs to be slightly bigger than
		// the number of rows * line height to account for the font descent.
		int height = (lineHeight + GAP) * uniqueSeries.size();//+ ModernWidget.DOUBLE_PADDING;

		String longest = null;

		for (String s : uniqueSeries.keySet()) {
			if (longest == null || s.length() > longest.length()) {
				longest = s;
			}
		}

		if (longest == null) {
			// There are no series to form a legend.
			return;
		}

		int width = (int)(g2.getFontMetrics().stringWidth(longest)) + 64;

		// determine the location

		int x;
		int y;

		switch(axes.getLegend().getPosition()) {
		case TOP_LEFT:
			x = 0;
			y = 0;
			break;
		case TOP_MIDDLE:
			x = (axes.getInternalPlotSize().getW() - width) / 2;
			y = 0;
			break;
		case CENTER_RIGHT:
			x = axes.getInternalPlotSize().getW() - width;
			y = (axes.getInternalPlotSize().getH() - height) / 2;
			break;
		case BOTTOM_RIGHT:
			x = axes.getInternalPlotSize().getW() - width;
			y = axes.getInternalPlotSize().getH() - height;
			break;
		case BOTTOM_MIDDLE:
			x = (axes.getInternalPlotSize().getW() - width) / 2;
			y = axes.getInternalPlotSize().getH() - height;
			break;
		case BOTTOM_LEFT:
			x = 0;
			y = axes.getInternalPlotSize().getH() - height;
			break;
		case CENTER_LEFT:
			x = 0;
			y = (axes.getInternalPlotSize().getH() - height) / 2;
			break;
		case CENTER:
			x = (axes.getInternalPlotSize().getW() - width) / 2;
			y = (axes.getInternalPlotSize().getH() - height) / 2;
			break;
		default:
			//top right
			x = axes.getInternalPlotSize().getW() - width;
			y = 0;
			break;
		}

		if (axes.getLegend().getStyle().getFillStyle().getVisible()) {
			g2.setColor(axes.getLegend().getStyle().getFillStyle().getColor());
			g2.fillRect(x, y, width, height);
		}

		if (axes.getLegend().getStyle().getLineStyle().getVisible()) {
			g2.setColor(axes.getLegend().getStyle().getLineStyle().getColor());
			g2.drawRect(x, y, width, height);
		}

		// draw the labels


		x += ModernWidget.PADDING;
		y += ModernWidget.PADDING;



		int textX;
		int textY;

		// draw the lines

		textX = x;
		textY = y;

		for (String name : uniqueSeries.keySet()) {
			XYSeries s = uniqueSeries.get(name);

			if (s.getStyle().getLineStyle().getVisible()) {
				Graphics2D g2Temp = (Graphics2D)g2.create();

				g2Temp.translate(textX, textY + lineHeight / 2);
				g2Temp.setColor(s.getStyle().getLineStyle().getColor());
				g2Temp.setStroke(s.getStyle().getLineStyle().getStroke());

				g2Temp.drawLine(0, 0, LINE_WIDTH, 0);

				g2Temp.dispose();
			}

			textY += lineHeight + GAP;
		}

		// draw the markers

		textX = x + ModernWidget.PADDING;
		textY = y;

		for (String name : uniqueSeries.keySet()) {
			XYSeries s = uniqueSeries.get(name);

			if (s.getMarker().getVisible()) {
				s.getMarker().plot(g2, 
						s.getMarkerStyle(), 
						new Point(textX + (LINE_WIDTH - s.getMarker().getSize()) / 2, textY + lineHeight / 2));
			}

			textY += lineHeight + GAP;
		}

		// draw the labels

		textX = x + TEXT_OFFSET;
		textY = y + lineHeight - g2.getFontMetrics().getDescent();

		for (String name : uniqueSeries.keySet()) {
			XYSeries s = uniqueSeries.get(name);

			g2.setColor(s.getFontStyle().getColor());
			g2.setFont(s.getFontStyle().getFont());

			g2.drawString(name, textX, textY);

			textY += lineHeight + GAP;
		}


	}
}
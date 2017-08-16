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

import org.jebtk.core.collections.UniqueArrayList;
import org.jebtk.modern.graphics.DrawingContext;

// TODO: Auto-generated Javadoc
/**
 * Draws an x axis on a plot
 *  
 * @author Antony Holmes Holmes
 *
 */
public class AxisLayerX1 extends AxisLayerX {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new axis x layer.
	 */
	public AxisLayerX1() {
		super("X Axis 1");
	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.AxesLayer#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes)
	 */
	@Override
	public void plot(Graphics2D g2,
			DrawingContext context,
			SubFigure subFigure,
			Axes axes) {
		Axis axis = axes.getX1Axis();

		plot(g2,
				context,
				subFigure,
				axes,
				axis,
				axes.getInternalPlotSize().getH(),
				axis.getTicks().getDrawInside());

		/*
		//
		// x axis
		//

		Axis axis = axes.getX1Axis();

		if (!axis.getVisible()) {
			return;
		}



		// The x axis line

		if (axis.getLineStyle().getVisible()) {
			g2.setStroke(axis.getLineStyle().getStroke());

			g2.setColor(axis.getLineStyle().getColor());

			y = axes.getMargins().getTop() + axes.getInternalPlotSize().getH();

			g2.drawLine(axes.getMargins().getLeft(), 
					y,
					axes.getMargins().getLeft() + axes.getInternalPlotSize().getW(),
					y);

			// If xmin is less than zero and xmax greater than zero,
			// draw a line at x=0

			if (axis.getShowZerothLine() && axis.getMin() < 0 && axis.getMax() > 0) {
				x = axes.toPlotX1(0);
				y = axes.getMargins().getTop();

				g2.drawLine(x, 
						y,
						x,
						y + axes.getInternalPlotSize().getH());
			}
		}

		System.err.println("hashes " + mHashId + " " + axes.plotHashId());

		System.err.println("hashes w " + axes.getInternalPlotSize().getW());

		if (mHashId == null || !mHashId.equals(axes.plotHashId())) {
			mMinorTicks = new UniqueArrayList<Integer>(axis.getTicks().getMinorTicks().getTickCount());

			for (double t : axis.getTicks().getMinorTicks()) {
				if (t != 0) {
					mMinorTicks.add(axes.toPlotX1(t));
				}
			}

			mMajorTicks = new UniqueArrayList<Integer>(axis.getTicks().getMajorTicks().getTickCount());

			mMajorTickLabels = 
					new UniqueArrayList<String>(axis.getTicks().getMajorTicks().getTickCount());

			for (int i = 0; i < axis.getTicks().getMajorTicks().getTickCount(); ++i) {
				//for (double y : axis.getTicks().getMajorTicks()) {
				double t = axis.getTicks().getMajorTicks().getTick(i);

				if (t != 0) {
					mMajorTicks.add(axes.toPlotX1(t));
					mMajorTickLabels.add(axis.getTicks().getMajorTicks().getLabel(i));
				}
			}

			System.err.println("huh w " + mMajorTicks.toString());


			mHashId = axes.plotHashId();
		}

		y = axes.getMargins().getTop() + axes.getInternalPlotSize().getH();



		drawTicks(g2, 
				axes,
				axis.getTicks().getMinorTicks(),
				axis.getTicks().getDrawInside(),
				mMinorTicks,
				y);

		TickMarkProperties ticks = axis.getTicks().getMajorTicks();

		drawTicks(g2, 
				axes,
				ticks,
				axis.getTicks().getDrawInside(),
				mMajorTicks,
				y);

		// axis tick marks



		if (ticks.getFontStyle().getVisible()) {
			g2.setFont(ticks.getFontStyle().getFont());
			g2.setColor(ticks.getFontStyle().getColor());

			int fontHeight = Ui.getFontHeight(g2);

			int offset = ticks.getTickSpacing();

			if (!axis.getTicks().getDrawInside()) {
				offset += ticks.getTickSize();
			}

			y = axes.getMargins().getTop() + 
					axes.getInternalPlotSize().getH() + 
					offset;

			double rotation = ticks.getRotation();

			if (rotation == Mathematics.HALF_PI) {

				for (int i = 0; i < mMajorTicks.size(); ++i) {
					x = mMajorTicks.get(i);

					String mark = ModernWidget.getTruncatedText(g2, 
							mMajorTickLabels.get(i), 
							axes.getMargins().getBottom() - offset);

					Graphics2D g2Temp = (Graphics2D)g2.create();

					g2Temp.translate(x, y);
					g2Temp.rotate(rotation);

					g2Temp.drawString(mark, 
							0, 
							(g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2);

					g2Temp.dispose();
				}
			} else if (rotation == -Mathematics.HALF_PI) {

				for (int i = 0; i < mMajorTicks.size(); ++i) {
					x = mMajorTicks.get(i);

					String mark = ModernWidget.getTruncatedText(g2, 
							mMajorTickLabels.get(i), 
							axes.getMargins().getBottom() - offset);

					Graphics2D g2Temp = (Graphics2D)g2.create();

					int w = ModernWidget.getStringWidth(g2Temp, mark);

					g2Temp.translate(x, y + w);
					g2Temp.rotate(rotation);

					g2Temp.drawString(mark, 
							0, 
							(g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2);

					g2Temp.dispose();
				}
			} else {
				y += fontHeight;

				for (int i = 0; i < mMajorTicks.size(); ++i) {
					x = mMajorTicks.get(i);

					String mark = mMajorTickLabels.get(i);

					x -= g2.getFontMetrics().stringWidth(mark) / 2;

					g2.drawString(mark, x, y);
				}
			}
		}

		// axis label

		if (axis.getTitle().getFontStyle().getVisible()) {
			g2.setFont(axis.getTitle().getFontStyle().getFont());
			g2.setColor(axis.getTitle().getFontStyle().getColor());

			x = axes.getMargins().getLeft() + 
					(axes.getInternalPlotSize().getW() - g2.getFontMetrics().stringWidth(axes.getX1Axis().getTitle().getText())) / 2;

			y = axes.getPlotSize().getH() - g2.getFontMetrics().getDescent();

			g2.drawString(axis.getTitle().getText(), x, y);
		}
		 */
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.AxisLayerX#drawAxisLine(java.awt.Graphics2D, org.graphplot.figure.Axes, org.graphplot.figure.Axis, int)
	 */
	@Override
	public void drawAxisLine(Graphics2D g2,
			Axes axes,
			Axis axis,
			int y) {

		if (axis.getLineStyle().getVisible()) {
			g2.setStroke(axis.getLineStyle().getStroke());

			g2.setColor(axis.getLineStyle().getColor());

			g2.drawLine(0, 
					y,
					axes.getInternalPlotSize().getW(),
					y);

			// If xmin is less than zero and xmax greater than zero,
			// draw a line at x=0

			if (axis.getShowZerothLine() && axis.getMin() < 0 && axis.getMax() > 0) {
				int x = axes.toPlotX1(0);
				y = 0;

				g2.drawLine(x, 
						y,
						x,
						axes.getInternalPlotSize().getH());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.graphplot.figure.AxisLayerX#cache(org.graphplot.figure.Axes, org.graphplot.figure.Axis)
	 */
	@Override
	public void cache(Axes axes, Axis axis) {
		if (mHashId == null || !mHashId.equals(axes.hashId())) {
			//int offset = axes.getMargins().getLeft();

			mMinorTicks = new UniqueArrayList<Integer>(axis.getTicks().getMinorTicks().getTickCount());

			for (double x : axis.getTicks().getMinorTicks()) {
				//if (x != 0) {
				mMinorTicks.add(axes.toPlotX1(x));
				//}
			}

			mMajorTicks = 
					new UniqueArrayList<Integer>(axis.getTicks().getMajorTicks().getTickCount());

			mMajorTickLabels = 
					new UniqueArrayList<String>(axis.getTicks().getMajorTicks().getTickCount());

			for (int i = 0; i < axis.getTicks().getMajorTicks().getTickCount(); ++i) {
				//for (double y : axis.getTicks().getMajorTicks()) {
				double x = axis.getTicks().getMajorTicks().getTick(i);

				//if (t != 0) {
				mMajorTicks.add(axes.toPlotX1(x));
				mMajorTickLabels.add(axis.getTicks().getMajorTicks().getLabel(i));
				//}
			}


			mHashId = axes.hashId();
		}
	}
}

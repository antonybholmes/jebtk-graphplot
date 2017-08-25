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
package org.jebtk.graphplot.figure.heatmap;

import java.awt.Graphics2D;
import java.util.Map;

import org.jebtk.core.settings.SettingsService;
import org.jebtk.graphplot.figure.Axes;
import org.jebtk.graphplot.figure.Figure;
import org.jebtk.graphplot.figure.Plot;
import org.jebtk.graphplot.figure.PlotLayer;
import org.jebtk.graphplot.figure.SubFigure;
import org.jebtk.graphplot.figure.series.XYSeriesGroup;
import org.jebtk.math.matrix.AnnotationMatrix;
import org.jebtk.modern.graphics.DrawingContext;



// TODO: Auto-generated Javadoc
/**
 * The class GroupColorBarLayer.
 */
public class GroupColorBarLayer extends PlotLayer {
	
	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant HEIGHT. */
	protected static final int HEIGHT = 
			SettingsService.getInstance().getAsInt("graphplot.heatmap.colorbar.height");
	
	/** The Constant OFFSET. */
	protected static final int OFFSET = 
			SettingsService.getInstance().getAsInt("graphplot.heatmap.colorbar.offset");

	/**
	 * The max rows.
	 */
	protected int mMaxRows = -1;

	/**
	 * The member group map.
	 */
	protected Map<Integer, XYSeriesGroup> mGroupMap = null;
	

	/**
	 * Instantiates a new group color bar layer.
	 */
	public GroupColorBarLayer() {
		super("Group Bar");

		mMaxRows = 0;

	}

	/* (non-Javadoc)
	 * @see edu.columbia.rdf.lib.bioinformatics.plot.figure.PlotLayer#plot(java.awt.Graphics2D, org.abh.common.ui.ui.graphics.DrawingContext, edu.columbia.rdf.lib.bioinformatics.plot.figure.Figure, edu.columbia.rdf.lib.bioinformatics.plot.figure.Axes, edu.columbia.rdf.lib.bioinformatics.plot.figure.Plot, org.abh.lib.math.matrix.AnnotationMatrix)
	 */
	@Override
	public void plotLayer(Graphics2D g2,
			DrawingContext context,
			Figure figure,
SubFigure subFigure,
			Axes axes,
			Plot plot,
			AnnotationMatrix m) {

		if (mGroupMap == null) {
			mGroupMap = XYSeriesGroup.arrangeGroupsByIndex(m, plot.getAllSeries());

			for (int key : mGroupMap.keySet()) {
				mMaxRows = Math.max(mGroupMap.get(key).getCount(), mMaxRows);
			}
		}

		int x = axes.getMargins().getLeft();
		int y = 0;
		
		int w = Math.max(1, axes.toPlotX1(1) - axes.toPlotX1(0));
		
		//int h = w / 2; //axes.toPlotY1(0) - axes.toPlotY1(1);
		
		for (int column = 0; column < m.getColumnCount(); ++column) {
			y = axes.getMargins().getTop() - HEIGHT * mMaxRows - OFFSET;

			for (int r = 0; r < mMaxRows; ++r) {
				if (!mGroupMap.containsKey(column)) {
					break;
				}

				if (r == mGroupMap.get(column).getCount()) {
					break;
				}

				g2.setColor(mGroupMap.get(column).get(r).getColor());

				g2.fillRect(x, y, w, HEIGHT);

				//if (mProperties.showGrid) {
				//	g2.setColor(mProperties.gridColor);
				//	g2.drawRect(x, y, w, h2);
				//}

				y += HEIGHT;
			}

			x += w;
		}
	}
}
